package com.inProject.in.domain.Profile.controller;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.Board.Dto.response.ResponseBoardListDto;
import com.inProject.in.domain.Board.service.BoardService;
import com.inProject.in.domain.Profile.Dto.request.*;
import com.inProject.in.domain.Profile.Dto.response.*;
import com.inProject.in.domain.Profile.service.*;
import com.inProject.in.domain.User.Dto.ResponseUserDto;
import com.inProject.in.domain.User.entity.User;
import com.inProject.in.domain.User.repository.UserRepository;
import com.inProject.in.domain.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@Tag(name = "profile", description = "프로필 전체 관련 api")
public class ProfileController {
    private final ProfileService profileService;
    private final BoardService boardService;
    private final UserRepository userRepository;

    @Autowired
    public ProfileController(ProfileService profileService,
                             BoardService boardService,
                             UserRepository userRepository){

        this.profileService = profileService;
        this.boardService = boardService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    @Parameter(name = "username", description = "username 입력", in = ParameterIn.PATH)
    @Operation(summary = "전체 프로필 가져오는 api", description = "한 유저의 모든 프로필 내용을 반환합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "프로필 가져오기 성공", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseProfileDto.class))
            })
    })
    public ResponseEntity<ResponseProfileDto> getProfile(@PathVariable(name = "username") String username){

        ResponseProfileDto responseProfileDto = profileService.getProfile(username);

        return ResponseEntity.status(HttpStatus.OK).body(responseProfileDto);
    }

    @GetMapping("/myBoards/{username}")
    @Parameter(name = "username", description = "username 입력", in = ParameterIn.PATH)
    @Operation(summary = "프로필에 내 게시글 가져오는 api", description = "한 유저가 작성한 글들을 반환합니다. type을 지정할 수 있습니다.", responses = {
            @ApiResponse(responseCode = "200", description = "게시글 가져오기 성공", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseProfileDto.class))
            })
    })
    public ResponseEntity<List<ResponseBoardListDto>> getMyBoards(@PageableDefault(size = 5) Pageable pageable,
                                                                         @RequestParam String type,
                                                                         @PathVariable(name = "username") String username
                                                                         ){
        List<ResponseBoardListDto> responseBoardListDtoList = boardService.getBoardListForUserInfo(pageable, username, type);

        return ResponseEntity.status(HttpStatus.OK).body(responseBoardListDtoList);
    }
}

