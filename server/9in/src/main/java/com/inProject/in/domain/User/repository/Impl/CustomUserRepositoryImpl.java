package com.inProject.in.domain.User.repository.Impl;

import com.inProject.in.domain.User.entity.QUser;
import com.inProject.in.domain.User.entity.User;
import com.inProject.in.domain.User.repository.CustomUserRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Autowired
    public CustomUserRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private QUser qUser = QUser.user;

    @Override
    public List<User> findSomeUser() {
        List<User> users = jpaQueryFactory
                .select(qUser)
                .from(qUser)
                .fetch();

        return users;
    }

    @Override
    public User findByIdAndPassword(Long id,String password ){
        JPAQuery<User> user = jpaQueryFactory.
                select(qUser)
                .from(qUser)
                .where(qUser.id.eq(id).and(qUser.password.eq(password)));
        return user.fetchOne();
    }
}
