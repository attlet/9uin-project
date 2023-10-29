//package com.inProject.in.domain.Board.service.impl;
//
//import com.inProject.in.config.security.JwtTokenProvider;
//import com.inProject.in.domain.Board.Dto.request.RequestBoardDto;
//import com.inProject.in.domain.Board.entity.Board;
//import com.inProject.in.domain.Board.repository.ViewCountRepository;
//import com.inProject.in.domain.MToNRelation.RoleBoardRelation.entity.RoleBoardRelation;
//import com.inProject.in.domain.MToNRelation.RoleBoardRelation.repository.RoleBoardRelationRepository;
//import com.inProject.in.domain.MToNRelation.TagBoardRelation.entity.TagBoardRelation;
//import com.inProject.in.domain.MToNRelation.TagBoardRelation.repository.TagBoardRelationRepository;
//import com.inProject.in.domain.Board.Dto.response.ResponseBoardDto;
//import com.inProject.in.domain.Board.repository.BoardRepository;
//import com.inProject.in.domain.Board.service.BoardService;
//import com.inProject.in.domain.RoleNeeded.Dto.RequestUsingInBoardDto;
//import com.inProject.in.domain.RoleNeeded.entity.RoleNeeded;
//import com.inProject.in.domain.RoleNeeded.repository.RoleNeededRepository;
//import com.inProject.in.domain.SkillTag.Dto.RequestSkillTagDto;
//import com.inProject.in.domain.SkillTag.entity.SkillTag;
//import com.inProject.in.domain.SkillTag.repository.SkillTagRepository;
//import com.inProject.in.domain.User.entity.User;
//import com.inProject.in.domain.User.repository.UserRepository;
//import jakarta.servlet.http.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.cglib.core.Local;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//
//
//@ExtendWith(MockitoExtension.class)   //mockito 의 mock객체를 사용하기 위한 어노테이션.
//class BoardServiceImplTest {
//    @Mock
//    BoardRepository boardRepository;
//    @Mock
//    SkillTagRepository skillTagRepository;
//    @Mock
//    RoleNeededRepository roleNeededRepository;
//    @Mock
//    TagBoardRelationRepository tagBoardRelationRepository;
//    @Mock
//    RoleBoardRelationRepository roleBoardRelationRepository;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    JwtTokenProvider jwtTokenProvider;
//    @Mock
//    ViewCountRepository viewCountRepository;
//
//    BoardService boardService;
////    @InjectMocks                                       //생성한 mock 객체를 주입받음.
////    BoardService boardService = new BoardServiceImpl(
////            boardRepository,
////            skillTagRepository,
////            roleNeededRepository,
////            tagBoardRelationRepository,
////            roleBoardRelationRepository,
////            userRepository,
////            jwtTokenProvider,
////            viewCountRepository
////    );
//
//    @BeforeEach
//    void dataSetting(){
//        boardService = new BoardServiceImpl(
//                boardRepository,
//                skillTagRepository,
//                roleNeededRepository,
//                tagBoardRelationRepository,
//                roleBoardRelationRepository,
//                userRepository,
//                jwtTokenProvider,
//                viewCountRepository
//        );
//    }
//
//    @Test
//    @DisplayName("로그인 안 한 유저가 게시글 하나 조회하는 로직")
//    void getBoardWithoutLogin() {
//
//        //given
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//        User user = User.builder()
//                .username("user1")
//                .password(passwordEncoder.encode("1234"))
//                .mail("aa@naver.com")
//                .build();
//
//        Board board = Board.builder()
//                .title("title1")
//                .text("text1")
//                .type("project")
//                .period(LocalDateTime.now())
//                .proceed_method("")
//                .author(user)
//                .build();
//
//        SkillTag skillTag = SkillTag.builder()
//                .name("react")
//                .build();
//
//        RoleNeeded roleNeeded = RoleNeeded.builder()
//                .name("frontend")
//                .build();
//
//        TagBoardRelation tagBoardRelation = TagBoardRelation.builder()
//                .skillTag(skillTag)
//                .board(board)
//                .build();
//
//        RoleBoardRelation roleBoardRelation = RoleBoardRelation.builder()
//                .roleNeeded(roleNeeded)
//                .board(board)
//                .build();
//
//        board.setTagBoardRelationList(List.of(tagBoardRelation));
//        board.setRoleBoardRelationList(List.of(roleBoardRelation));
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.addHeader("X-AUTH-TOKEN", "token");
//
//        given(jwtTokenProvider.resolveToken(request)).willReturn(null);
//        given(boardRepository.findById(1L)).willReturn(Optional.ofNullable(board));
//
//
//        //when
//        ResponseBoardDto responseBoardDto = boardService.getBoard(1L, request);
//
//        //then
//        assertEquals(board.getId(), responseBoardDto.getBoard_id());
//        assertEquals(board.getView_cnt(), responseBoardDto.getView_cnt());
//        assertEquals(responseBoardDto.getView_cnt(), 0);
//
//        System.out.println("--------------");
//        System.out.println("view cnt : " + responseBoardDto.getView_cnt());
//        System.out.println("--------------");
//    }
//
//    @Test
//    @DisplayName("로그인 한 유저가 게시글 하나 조회")
//    void getBoardWithLogin(){
//        //given
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//        User user = User.builder()
//                .username("user1")
//                .password(passwordEncoder.encode("1234"))
//                .mail("aa@naver.com")
//                .build();
//
//        Board board = Board.builder()
//                .title("title1")
//                .text("text1")
//                .type("project")
//                .period(LocalDateTime.now())
//                .proceed_method("")
//                .author(user)
//                .build();
//
//        SkillTag skillTag = SkillTag.builder()
//                .name("react")
//                .build();
//
//        RoleNeeded roleNeeded = RoleNeeded.builder()
//                .name("frontend")
//                .build();
//
//        TagBoardRelation tagBoardRelation = TagBoardRelation.builder()
//                .skillTag(skillTag)
//                .board(board)
//                .build();
//
//        RoleBoardRelation roleBoardRelation = RoleBoardRelation.builder()
//                .roleNeeded(roleNeeded)
//                .board(board)
//                .build();
//
//        board.setTagBoardRelationList(List.of(tagBoardRelation));
//        board.setRoleBoardRelationList(List.of(roleBoardRelation));
//
//        List<String> boardList = new ArrayList<>();
//        boardList.add("1");
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        String token = jwtTokenProvider.createToken("user1", List.of("user"));
//
//        given(jwtTokenProvider.createToken("user1",List.of("user"))).willReturn(token);
//        given(jwtTokenProvider.resolveToken(request)).willReturn(token);
//
//        given(viewCountRepository.getBoardList(anyString())).willReturn(boardList);   //viewCountRepository 부분을 나눠서 작성.
//
//        given(boardRepository.updateViewCnt(any(Long.class))).willReturn(1);
//        given(boardRepository.findById(1L)).willReturn(Optional.ofNullable(board));
//
////        request.addHeader("X-AUTH-TOKEN", token);
//        //when
//        ResponseBoardDto responseBoardDto = boardService.getBoard(1L, request);
//
//        //then
//        assertEquals(board.getId(), responseBoardDto.getBoard_id());
//        assertEquals(board.getView_cnt(), responseBoardDto.getView_cnt());
//        assertEquals(responseBoardDto.getView_cnt(), 1);
//
//        System.out.println("--------------");
//        System.out.println("view cnt : " + responseBoardDto.getView_cnt());
//        System.out.println("--------------");
//
//    }
//
////    @Test
////    @DisplayName("게시글 하나 생성하는 로직")
////    void createBoard() {
////
////        //given
////        Long testId = 1l;
////
////        RequestBoardDto requestBoardDto = RequestBoardDto.builder()
////                .title("title1")
////                .text("text1")
////                .build();
////
////        RequestSkillTagDto requestSkillTagDto = RequestSkillTagDto.builder()
////                .name("react")
////                .build();
////        ;
////
////        RequestUsingInBoardDto requestUsingInBoardDto = RequestUsingInBoardDto.builder()
////                .name("backend")
////                .pre_cnt(0)
////                .want_cnt(4)
////                .build();
////
////        Board board = requestBoardDto.toEntity();
////        SkillTag skillTag = requestSkillTagDto.toEntity();
////
////        RoleNeeded roleNeeded = RoleNeeded.builder()
////                .name("backend")
////                .build();
////
////        board.setId(testId);
////        skillTag.setId(testId);
////        roleNeeded.setId(testId);
////
////        TagBoardRelation tagBoardRelation = TagBoardRelation.builder()
////                .board(board)
////                .skillTag(skillTag)
////                .build();
////
////        RoleBoardRelation roleBoardRelation = RoleBoardRelation.builder()
////                .roleNeeded(roleNeeded)
////                .board(board)
////                .build();
////
////        List<RequestSkillTagDto> requestSkillTagDtoList = new ArrayList<>();
////        List<RequestUsingInBoardDto> requestRoleNeededDtoList = new ArrayList<>();
////
////        requestSkillTagDtoList.add(requestSkillTagDto);
////        requestRoleNeededDtoList.add(requestUsingInBoardDto);
////
////
////        HttpServletRequest request;
////
////        given(boardRepository.save(any(Board.class))).willReturn(board);
////        given(skillTagRepository.findTagByName(any(String.class))).willReturn(Optional.of(skillTag));
////        given(roleNeededRepository.findRoleByName(any(String.class))).willReturn(Optional.of(roleNeeded));
////        given(tagBoardRelationRepository.save(any(TagBoardRelation.class))).willReturn(tagBoardRelation);
////        given(roleBoardRelationRepository.save(any(RoleBoardRelation.class))).willReturn(roleBoardRelation);
////
////
////        //when
////        ResponseBoardDto responseBoardDto = boardService.createBoard( );
////
////        //then
////        assertEquals(responseBoardDto.getBoard_id(), board.getId());
////
////    }
//
//    @Test
//    @DisplayName("게시글 수정 로직")
//    void updateBoard() {
////        //given
////        Long id = 1L;
////
////        RequestBoardDto requestBoardDto = RequestBoardDto.builder()
////                .title("title1")
////                .text("text1")
////                .build();
////
////        RequestUpdateBoardDto requestBoardDto2 = RequestUpdateBoardDto.builder()
////                .title("title2")
////                .text("text2")
////                .build();
////
////        Board board = requestBoardDto.toEntity();
////        Board board2 = Board.builder()
////                .title("title2")
////                .text("text2")
////                .build();
////
////        board2.setId(id);
////
////        given(boardRepository.findById(id)).willReturn(Optional.ofNullable(board)); //ofNullable을 통해
////        given(boardRepository.save(eq(board))).willReturn(board2);
////
////        //when
////        ResponseBoardDto responseBoardDto = boardService.updateBoard(1L, requestBoardDto2);
////
////
////        //then
////
////        assertEquals(responseBoardDto.getBoard_id(), board2.getId());
////        assertEquals(responseBoardDto.getTitle(), board.getTitle());
////        assertEquals(responseBoardDto.getText(), board.getText());
////        assertEquals(responseBoardDto.getPeriod(), board.getPeriod());
////        assertEquals(responseBoardDto.getProceed_method(), board.getProceed_method());
//
//    }
//
//
//
//    @Test
//    @DisplayName("게시글 리스트 출력 로직")
//    void getBoardList() {
//
////        //given
////        RequestBoardDto requestBoardDto1 = RequestBoardDto.builder()
////                .title("title1")
////                .text("text1")
////                .build();
////
////        RequestBoardDto requestBoardDto2 = RequestBoardDto.builder()
////                .title("title2")
////                .text("text2")
////                .build();
////
////        RequestBoardDto requestBoardDto3 = RequestBoardDto.builder()
////                .title("title3")
////                .text("text3")
////                .build();
////
////        Board board1 = requestBoardDto1.toEntity();
////        Board board2 = requestBoardDto2.toEntity();
////        Board board3 = requestBoardDto3.toEntity();
////
////        Pageable pageable = PageRequest.of(0 , 3);
////        String username = null;
////        String title = null;
////        String type = null;
////        List<String> tags = null;
////
////        RequestSearchBoardDto requestSearchBoardDto = RequestSearchBoardDto.builder()
////                .build();
////
////        List<Board> boardList = List.of(board1, board2, board3);
////        Page<Board> page = new PageImpl<>(boardList, pageable, boardList.size()); //이렇게 해도 가능!
////
////        given(boardRepository.findBoards(pageable, username, title, type, tags)).willReturn(page);
////
////        //when
////        List<ResponseBoardDto> responseBoardDtoList = boardService.getBoardList(pageable, requestSearchBoardDto);
////
////        //then
////        int i = 1;
////        assertEquals(3, responseBoardDtoList.size());
////
////        for(ResponseBoardDto responseBoardDto : responseBoardDtoList){
////            assertEquals(responseBoardDto.getTitle(), "title" + i);
////            i++;
////        }
//    }
//}