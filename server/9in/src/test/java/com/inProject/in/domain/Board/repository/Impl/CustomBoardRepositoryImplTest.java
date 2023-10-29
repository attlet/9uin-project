//package com.inProject.in.domain.Board.repository.Impl;
//
//import com.inProject.in.config.QueryDslConfig;
//import com.inProject.in.domain.Board.entity.Board;
//import com.inProject.in.domain.Board.repository.BoardRepository;
//import com.inProject.in.domain.MToNRelation.ClipBoardRelation.entity.ClipBoardRelation;
//import com.inProject.in.domain.MToNRelation.ClipBoardRelation.repository.ClipBoardRelationRepository;
//import com.inProject.in.domain.MToNRelation.TagBoardRelation.entity.TagBoardRelation;
//import com.inProject.in.domain.SkillTag.entity.SkillTag;
//import com.inProject.in.domain.SkillTag.repository.SkillTagRepository;
//import com.inProject.in.domain.MToNRelation.TagBoardRelation.repository.TagBoardRelationRepository;
//import com.inProject.in.domain.User.entity.User;
//import com.inProject.in.domain.User.repository.UserRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@Import(QueryDslConfig.class)
//class CustomBoardRepositoryImplTest {
//
//    @Autowired
//    JPAQueryFactory jpaQueryFactory;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    SkillTagRepository skilltagRepository;
//
//    @Autowired
//    TagBoardRelationRepository tagBoardRelationRepository;
//    @Autowired
//    ClipBoardRelationRepository clipBoardRelationRepository;
//
//
//    @BeforeEach
//    void dataSetting(){
//
//        String []tagsname = new String[]{"react", "java", "python"};
//        List<User> userList = new ArrayList<>();
//        List<Board> boardList = new ArrayList<>();
//        List<SkillTag> skillTagList = new ArrayList<>();
//        List<TagBoardRelation> tagBoardRelationList = new ArrayList<>();
//
//        for(int i = 0 ; i < 3; i++){
//            skillTagList.add(
//                    SkillTag.builder()
//                            .name(tagsname[i])
//                            .build()
//            );
//        }
//
//        for(int i = 0 ; i < 10 ; i++){
//            userList.add(
//                    User.builder()
//                            .username("user" + i)
//                            .mail("user" + i + "@naver.com")
//                            .password(Integer.toString(i))
//                            .build()
//            );
//        }
//
//        for(int i = 0 ; i< 50 ; i++){
//
//            Board board = Board.builder()
//                    .title("title" + i)
//                    .text("text" + i)
//                    .type((i % 2 == 0) ? "study" : "project")
//                    .author(userList.get(i % 10))
//                    .period(LocalDateTime.now())
//                    .proceed_method("1 month")
//                    .build();
//
//
//            TagBoardRelation tagBoardRelation = TagBoardRelation.builder()
//                    .board(board)
//                    .skillTag(skillTagList.get(i % 3))
//                    .build();
//
//            board.setTagBoardRelationList(List.of(tagBoardRelation));
//            boardList.add(board);
//
//
//            clipBoardRelationRepository.save(ClipBoardRelation.builder()
//                    .clipedBoard(boardList.get(i))
//                    .clipUser(userList.get(i % 10))
//                    .build());
//
//
//        }
//
//        for(int i = 0; i < 50; i++){
//        tagBoardRelationList.add(
//                TagBoardRelation.builder()
//                        .board(boardList.get(i))
//                        .skillTag(skillTagList.get(i % 3))
//                        .build()
//        );
//    }
//
//        userRepository.saveAll(userList);
//        boardRepository.saveAll(boardList);
//        skilltagRepository.saveAll(skillTagList);
//        tagBoardRelationRepository.saveAll(tagBoardRelationList);  //필수적으로 추가
//}
//
//    @Test
//    @DisplayName("모든 게시글 조건없이 출력")
//    void getBoardPageOne() {
//
//        //given
//        String title = "";
//        String type = "";
//        String user_id = "";
//        List<String> tags = new ArrayList<>();
//
//        //when
//        long startmill = System.currentTimeMillis();
//        Pageable pageable = PageRequest.of(0, 8);
//        Page<Board> PostPage = boardRepository.findBoards(pageable, user_id, title, type, tags);
//        List<Board> boardList = PostPage.getContent();
//        long endmill = System.currentTimeMillis();
//        long querytime = endmill - startmill;
//
//        //then
//        assertEquals(PostPage.getSize(), 8);
//        assertEquals(boardList.size(), 8);
//
//        for(Board board : boardList){
//            System.out.println("--------------------------");
//            System.out.println("Board content :" + board.getTitle() + ' ' +  board.getCreateAt());
//            System.out.println("User id : " + board.getAuthor().getUsername());
//            System.out.println("--------------------------");
//        }
//        System.out.println("querytime = " + querytime);
//    }
//
//    @Test
//    @DisplayName("작성자 이름 검색")
//    void findUserBoard(){
//
//        //given
//        String title = "";
//        String type = "";
//        String user_id = "user1";
//        List<String> tags = new ArrayList<>();
//
//
//        //when
//        long startmill = System.currentTimeMillis();
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<Board> postPage =  boardRepository.findBoards(pageable, user_id, title, type, tags);
//        long endmill = System.currentTimeMillis();
//        long querytime = endmill - startmill;
//
//        //then
//        assertEquals(postPage.getSize(), 5);
//        List<Board> retBoardList = postPage.getContent();
//        assertEquals(retBoardList.size(), 5);
//
//        for(Board board : retBoardList){
//            System.out.println("-----------------");
//            System.out.println("Board user id " + board.getAuthor().getUsername());
//            System.out.println("Board title " + board.getTitle());
//            System.out.println("-----------------");
//        }
//
//        System.out.println("query time : " + querytime);
//    }
//
//    @Test
//    @DisplayName("태그 1개인 게시글 필터링")
//    void TagFiltering(){
//
//        //given
//        String title = "";
//        String type = "";
//        String user_id = "";
//        List<String> tags = new ArrayList<>();
//        List<Board> boardList = new ArrayList<>();
//
//        tags.add("react");
//
//        //when
//        long startmill = System.currentTimeMillis();
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<Board> postPage = boardRepository.findBoards(pageable, user_id, title, type, tags);
//        long endmill = System.currentTimeMillis();
//        long querytime = endmill - startmill;
//
//        //then
//        assertEquals(postPage.getSize(), 5);
//        List<Board> retBoardList = postPage.getContent();
//        assertEquals(retBoardList.size(), 5);
//
//        for(Board board : retBoardList){
//            System.out.println("-----------------");
//            System.out.println("Board user id " + board.getAuthor().getUsername());
//            System.out.println("Board title " + board.getTitle());
//            System.out.print("Tag : ");
//
//            board.getTagBoardRelationList()
//                    .stream().forEach(tag -> System.out.print(tag.getSkillTag().getName()));
//
//            System.out.println();
//            System.out.println("-----------------");
//        }
//
//        System.out.println("query time : " + querytime);
//
//    }
//
//    @Test
//    @DisplayName("유저들 본인이 즐겨찾기한 게시글만 출력되는 지 확인")
//    void ClipedPosts(){
//        //given
//        String title = "";
//        String type = "";
//        String user_id = "";
//        List<String> tags = new ArrayList<>();
//
//
//        //when
//        Pageable pageable = PageRequest.of(0, 5);
//
//        for(int i = 0 ; i < 10; i++){
//            User user = User.builder()
//                    .id((long)i + 1)
//                    .username("user" + i)
//                    .build();
//
//            Page<Board> postPage = boardRepository.searchBoardsByCliped(pageable, user);
//            List<Board> boardList = postPage.getContent();
//
//            //then
//
//            assertEquals(boardList.size(), 5);
//
//            for(Board board : boardList){
//                System.out.println("-----------------");
//                System.out.println("username " + user.getUsername());
//                System.out.println("Board board id " + board.getId());
//                System.out.println("-----------------");
//            }
//        }
//
//    }
//
//}
//
