package com.inProject.in.domain.CommonLogic.Find.controller;

import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestCheckIdDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestValidCodeDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseCheckIdDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseCheckMailDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseIsSuccessDto;
import com.inProject.in.domain.CommonLogic.Find.service.FindService;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestFindDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseFindIdDto;
import com.inProject.in.domain.CommonLogic.Mail.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/find")
@Tag(name = "find", description = "아이디/비밀번호 찾기 api")
public class FindController {
    private final Logger log = LoggerFactory.getLogger(FindController.class);
    private final FindService findService;
    private final MailService mailService;


    @PostMapping("/findId")
    @Operation(summary = "아이디 찾기", description = "메일을 통해 아이디를 찾습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "아이디 찾기 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseFindIdDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "아이디 찾기 실패")
            })
    public ResponseEntity<ResponseFindIdDto> findId(@RequestBody RequestFindDto requestFindDto){
        log.info("SignController findId ==> 아이디 찾기 시작");
        ResponseFindIdDto responseFindIdDto = findService.findId(requestFindDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseFindIdDto);
    }

    @PostMapping("/validCodeSend")
    @Operation(summary = "인증 코드 발송", description = "회원가입 시 등록한 메일로 인증코드를 보냅니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "코드 전송 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseIsSuccessDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "코드 전송 실패")
            })
    public ResponseEntity<ResponseIsSuccessDto> validCodeSend(@RequestBody RequestFindDto requestFindDto){
        ResponseIsSuccessDto responseIsSuccessDto = mailService.validCodeSend(requestFindDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseIsSuccessDto);
    }


    @PostMapping("/validMail")
    @Operation(summary = "입력한 인증 코드 검증", description = "발송한 인증코드를 통해 검증합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "코드 확인 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseIsSuccessDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "코드 확인 실패")
            })
    public ResponseEntity<ResponseIsSuccessDto> validMail(@RequestBody RequestValidCodeDto requestValidCodeDto){
        ResponseIsSuccessDto responseIsSuccessDto = mailService.validMail(requestValidCodeDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseIsSuccessDto);
    }

    @PostMapping("/checkId")
    @Operation(summary = "아이디 체크", description = "아이디를 통해 유저가 맞는지 확인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "아이디 체크 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseCheckIdDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "아이디 체크 실패")
            })
    public ResponseEntity<ResponseCheckIdDto> checkId(@RequestBody RequestCheckIdDto requestCheckIdDto){
        ResponseCheckIdDto responseCheckIdDto = findService.checkId(requestCheckIdDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseCheckIdDto);
    }

    @PostMapping("/findPw")
    @Operation(summary = "비밀번호 찾기", description = "임시 비밀번호를 받습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "비밀번호 찾기 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseIsSuccessDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "비밀번호 찾기실패")
            })
    public ResponseEntity<ResponseIsSuccessDto> findPw(@RequestBody RequestFindDto requestFindDto){
        log.info("SignController findPw ==> 비밀번호 찾기 시작");
        findService.findPw(requestFindDto);
        String message = "임시 비밀번호 생성 성공";

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseIsSuccessDto(message, true));
    }



}
