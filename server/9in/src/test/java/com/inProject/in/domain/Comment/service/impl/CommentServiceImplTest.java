//package com.inProject.in.domain.Comment.service.impl;
//
//import com.inProject.in.domain.Board.entity.Board;
//import com.inProject.in.domain.Board.repository.BoardRepository;
//import com.inProject.in.domain.Comment.Dto.RequestCommentDto;;
//import com.inProject.in.domain.Comment.Dto.ResponseCommentDto;
//import com.inProject.in.domain.Comment.entity.Comment;
//import com.inProject.in.domain.Comment.repository.CommentRepository;
//import com.inProject.in.domain.Comment.service.CommentService;
//import com.inProject.in.domain.User.entity.User;
//import com.inProject.in.domain.User.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.AdditionalAnswers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.AdditionalAnswers.returnsFirstArg;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class CommentServiceImplTest {
//
//    @Mock
//    private CommentRepository commentRepository;
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BoardRepository boardRepository;
//
//    @InjectMocks
//    public CommentService commentService = new CommentServiceImpl(
//            commentRepository,
//            userRepository,
//            boardRepository
//            );
//
//    @Test
//    void getComment() {
//    }
//
//    @Test
//    @DisplayName("댓글 생성 후, 댓글 개수 증가 확인까지 테스트")
//    void createComment() {
//        //given
//        Long board_id = 1L;
//        Long user_id = 100L;
//
//        RequestCommentDto requestCommentDto = RequestCommentDto.builder()
//                .board_id(board_id)
//                .user_id(user_id)
//                .text("test text1")
//                .build();
//
//        User user100 = new User();
//        Board board1 = new Board();
//
//        user100.setId(user_id);
//        board1.setId(board_id);
//        board1.setComment_cnt(0);
//
//        given(userRepository.findById(user_id)).willReturn(Optional.ofNullable(user100));
//        given(boardRepository.findById(board_id)).willReturn(Optional.ofNullable(board1));
//
//        when(commentRepository.save(any(Comment.class))).then(AdditionalAnswers.returnsFirstArg());   //save한 엔티티 그대로 반환하도록 함.
//
//        //when
//        ResponseCommentDto responseCommentDto = commentService.createComment(requestCommentDto);
//
//        //then
//        assertEquals(responseCommentDto.getBoard_id(), board_id);
//        assertEquals(responseCommentDto.getUser_id(), user_id);
//        assertEquals(responseCommentDto.getText(), "test text1");
//        assertEquals(board1.getComment_cnt(), 1);                   // 댓글 개수 1 증가
//
//    }
//
//    @Test
//    void updateComment() {
//    }
//
//    @Test
//    void getCommentsInBoard() {
//    }
//
//    @Test
//    void deleteComment() {
//    }
//}