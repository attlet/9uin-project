//package com.inProject.in.domain.Comment.controller;
//
//import com.inProject.in.domain.Comment.Dto.ResponseCommentDto;
//import com.inProject.in.domain.Comment.service.CommentService;
//import com.inProject.in.domain.Comment.service.impl.CommentServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.BDDMockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CommentController.class)
//class CommentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CommentService commentService;
//
//    @Test
//    @DisplayName("0페이지 댓글 3개까지 get하기")
//    void getComments() {
//
//        //given
//        Long board_id = 1L;
//        Pageable pageable = PageRequest.of(0, 3);
//
//        ResponseCommentDto responseCommentDto1 = new ResponseCommentDto(1L , 1L, 1L, "comment1");
//        ResponseCommentDto responseCommentDto2 = new ResponseCommentDto(2L , 2L, 1L, "comment2");
//        ResponseCommentDto responseCommentDto3 = new ResponseCommentDto(3L , 3L, 1L, "comment3");
//
//        List<ResponseCommentDto> responseCommentDtoList = List.of(responseCommentDto1, responseCommentDto2, responseCommentDto3);
//
//        given(commentService.getCommentsInBoard(pageable, board_id)).willReturn(responseCommentDtoList);
//
//        //when
////        mockMvc.perform(
////                get("/comments/?page=0&board_id=1"))
////                .andExpect(status().isOk())
////
//
//
//        //then
//    }
//
//    @Test
//    void createComment() {
//    }
//
//    @Test
//    void updateComment() {
//    }
//
//    @Test
//    void deleteComment() {
//    }
//}