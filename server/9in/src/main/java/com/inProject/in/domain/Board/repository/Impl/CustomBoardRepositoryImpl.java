package com.inProject.in.domain.Board.repository.Impl;

import com.inProject.in.domain.Board.entity.Board;
import com.inProject.in.domain.Board.entity.QBoard;
import com.inProject.in.domain.MToNRelation.ClipBoardRelation.entity.QClipBoardRelation;
import com.inProject.in.domain.MToNRelation.TagBoardRelation.entity.QTagBoardRelation;
import com.inProject.in.domain.Board.repository.CustomBoardRepository;
import com.inProject.in.domain.SkillTag.entity.QSkillTag;
import com.inProject.in.domain.User.entity.QUser;
import com.inProject.in.domain.User.entity.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;


import java.util.List;


public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;
    QBoard qBoard = QBoard.board;
    QSkillTag qSkillTag = QSkillTag.skillTag;
    QTagBoardRelation qTagBoardRelation = QTagBoardRelation.tagBoardRelation;
    QClipBoardRelation qClipBoardRelation = QClipBoardRelation.clipBoardRelation;
    QUser qUser = QUser.user;

    @Autowired
    public CustomBoardRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory =jpaQueryFactory;
    }

    @Override
    public JPAQuery<Long> getCount(){
        JPAQuery<Long> count = jpaQueryFactory
                .select(qBoard.count())
                .from(qBoard);
        return count;
    }

    @Override
    public Page<Board> findBoards(Pageable pageable, String username, String title, String type, List<String> tags) {

        List<Board> content = jpaQueryFactory
                .selectFrom(qBoard)
                .where(UserIdEq(username), TitleEq(title), TypeEq(type), TagsEq(tags))
                .orderBy(qBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = getCount();
        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchOne());
    }

    @Override
    public Page<Board> searchBoardsByCliped(Pageable pageable, User user) {
//        List<Post> content = jpaQueryFactory
//                .selectFrom(qPost)
//                .where(ClipedEq(user))
//                .orderBy(qPost.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();

        List<Board> content = jpaQueryFactory
                .selectFrom(qBoard)
                .join(qBoard.clipBoardRelationList, qClipBoardRelation)
                .where(qClipBoardRelation.clipUser.id.eq(user.getId()))
                .orderBy(qBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = getCount();
        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchOne());

    }

    @Override
    public Page<Board> searchBoardsByUserInfo(Pageable pageable, User user, String type) {
        List<Board> content = jpaQueryFactory
                .selectFrom(qBoard)
                .where(qBoard.author.eq(user), qBoard.type.eq(type))
                .orderBy(qBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> count = getCount();

        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchOne());
    }

    @Override
    public Long CountsClipedBoards(User user) {
       Long count = jpaQueryFactory
                .select(qClipBoardRelation.count())
                .from(qClipBoardRelation)
                .where(qClipBoardRelation.clipUser.id.eq(user.getId()))
                .fetchOne();
        return count;
    }

    @Override
    public Long CountsUserBoards(User user, String type) {
        Long count = jpaQueryFactory
                .select(qBoard.count())
                .from(qBoard)
                .where(qBoard.author.eq(user), qBoard.type.eq(type))
                .fetchOne();
        return count;
    }

    private BooleanExpression UserIdEq(String username){ return username.isBlank() != true ? qBoard.author.username.eq(username) : null; }

    private BooleanExpression TitleEq(String title){
        return title.isBlank() != true ? qBoard.title.contains(title) : null;
    }

    private BooleanExpression TypeEq(String type){
        return type.isBlank() != true ? qBoard.type.eq(type) : null;
    }

    private BooleanExpression TagsEq(List<String> tags) {
        if (tags.isEmpty() == true) return null;

        BooleanExpression combinedExpression = Expressions.asBoolean(true).isTrue();


        for(String tag : tags){
//            QPost subqPost = new QPost("subqPost");

//            Predicate tagQuery = qPost.id.in(jpaQueryFactory
//                    .select(subqPost.id)
//                    .from(subqPost)
//                    .join(subqPost.tagPostRelationList, qTagPostRelation)
//                    .join(qTagPostRelation.skillTag, qSkillTag)
//                    .where(qSkillTag.name.eq(tag)));

            Predicate tagQuery = qBoard.id.in(
                    jpaQueryFactory.select(qTagBoardRelation.board.id)
                            .from(qTagBoardRelation)
                            .join(qTagBoardRelation.skillTag, qSkillTag)
                            .where(qSkillTag.name.eq(tag))
            );

            combinedExpression = combinedExpression.and(tagQuery);
        }

        return combinedExpression;
    }

    private BooleanExpression ClipedEq(User user){
        if (user == null) return null;

        BooleanExpression clipedquery = qBoard.id.in(jpaQueryFactory
                .select(qClipBoardRelation.clipedBoard.id)
                .from(qClipBoardRelation)
                .join(qClipBoardRelation.clipUser, qUser)
                .where(qUser.id.eq(user.getId())));

        return clipedquery;
    }
}
