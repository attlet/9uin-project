//package com.inProject.in.domain.Board.repository.Impl;
//
//import com.inProject.in.domain.Board.entity.Board;
//import com.inProject.in.domain.Board.repository.BoardRepository;
//import com.inProject.in.domain.MToNRelation.TagBoardRelation.entity.TagBoardRelation;
//import com.inProject.in.domain.SkillTag.entity.SkillTag;
//import com.inProject.in.domain.SkillTag.repository.SkillTagRepository;
//import com.inProject.in.domain.MToNRelation.TagBoardRelation.repository.TagBoardRelationRepository;
//import com.inProject.in.domain.User.entity.User;
//import com.inProject.in.domain.User.repository.UserRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
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
//
//
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
//        for(int i = 1 ; i<= 50 ; i++){
//            boardList.add(
//                    Board.builder()
//                            .title("title" + i)
//                            .text("text" + i)
//                            .type((i % 2 == 0) ? "study" : "project")
//                            .author(userList.get(i % 10))
//                            .build()
//            );
//        }
//
//        for(int i = 0; i < 50; i++){
//            tagBoardRelationList.add(
//                    TagBoardRelation.builder()
//                            .board(boardList.get(i))
//                            .skillTag(skillTagList.get(i % 3))
//                            .build()
//            );
//        }
//
//        userRepository.saveAll(userList);
//        boardRepository.saveAll(boardList);
//        skilltagRepository.saveAll(skillTagList);
//        tagBoardRelationRepository.saveAll(tagBoardRelationList);  //필수적으로 추가
//    }
//
//    @Test
//    @DisplayName("모든 게시글 조건없이 출력")
//    void findAllPost() {
//
//        //given
//        String title = "";
//        String type = "";
//        String user_id = "";
//        List<String> tags = new ArrayList<>();
//        User user = null;
//
//        //when
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<Board> PostPage = boardRepository.findBoards(pageable, user_id, title, type, tags);
//        List<Board> boardList = PostPage.getContent();
//
//        //then
//        assertEquals(PostPage.getSize(), 5);
//        assertEquals(boardList.size(), 5);
//
//        for(Board board : boardList){
//            System.out.println("--------------------------");
//            System.out.println("Post content :" + board.getTitle() + ' ' +  board.getCreateAt());
//            System.out.println("User id : " + board.getAuthor().getUsername());
//            System.out.println("--------------------------");
//        }
//    }
//
//    @Test
//    @DisplayName("작성자 이름 검색")
//    void findUserPost(){
//
//        //given
//        String title = "";
//        String type = "";
//        String user_id = "user1";
//        List<String> tags = new ArrayList<>();
//        User user = null;
//        List<Board> boardList = new ArrayList<>();
//
//        //when
//        long startmill = System.currentTimeMillis();
//        Pageable pageable = PageRequest.of(0, 3);
//        Page<Board> postPage =  boardRepository.findBoards(pageable, user_id, title, type, tags);
//        long endmill = System.currentTimeMillis();
//        long querytime = endmill - startmill;
//
//        //then
//        assertEquals(postPage.getSize(), 3);
//        List<Board> retBoardList = postPage.getContent();
//        assertEquals(retBoardList.size(), 3);
//
//        for(Board board : retBoardList){
//            System.out.println("-----------------");
//            System.out.println("Post user id " + board.getAuthor().getUsername());
//            System.out.println("Post title " + board.getTitle());
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
//        User user = null;
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
//            System.out.println("Post user id " + board.getAuthor().getUsername());
//            System.out.println("Post title " + board.getTitle());
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
//    @DisplayName("클립한 게시글만 출력")
//    void ClipedPosts(){
//        //given
//        String title = "";
//        String type = "";
//        String user_id = "";
//        List<String> tags = new ArrayList<>();
//        User user = User.builder()
//                .username("user1")
//                .build();
//
//        List<Board> boardList = new ArrayList<>();
//
//        //when
//        long startmill = System.currentTimeMillis();
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<Board> postPage = boardRepository.findBoards(pageable, user_id, title, type, tags);
//        long endmill = System.currentTimeMillis();
//        long querytime = endmill - startmill;
//
//
//    }
//
//}