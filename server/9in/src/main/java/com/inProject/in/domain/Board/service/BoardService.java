package com.inProject.in.domain.Board.service;

import com.inProject.in.domain.Board.Dto.request.RequestCreateBoardDto;
import com.inProject.in.domain.Board.Dto.request.RequestSearchBoardDto;
import com.inProject.in.domain.Board.Dto.request.RequestUpdateBoardDto;
import com.inProject.in.domain.Board.Dto.response.ResponseBoardDto;
import com.inProject.in.domain.Board.Dto.response.ResponseBoardListDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BoardService {
    ResponseBoardDto getBoard(Long id, HttpServletRequest request);
    ResponseBoardDto createBoard(RequestCreateBoardDto requestCreateBoardDto,
                                 HttpServletRequest request);
    ResponseBoardDto updateBoard(Long id, RequestUpdateBoardDto requestUpdateBoardDto, HttpServletRequest request);
    void deleteBoard(Long id, HttpServletRequest request);
    List<ResponseBoardListDto> getBoardList(Pageable pageable, RequestSearchBoardDto requestSearchBoardDto);
    List<ResponseBoardListDto> getBoardListForUserInfo(Pageable pageable, String username, String type);
}
