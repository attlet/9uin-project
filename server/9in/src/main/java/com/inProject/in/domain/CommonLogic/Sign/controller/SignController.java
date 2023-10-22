package com.inProject.in.domain.CommonLogic.Sign.controller;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestFindDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestValidCodeDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseCheckIdDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseIsSuccessDto;
import com.inProject.in.domain.CommonLogic.Find.service.FindService;
import com.inProject.in.domain.CommonLogic.Mail.service.MailService;
import com.inProject.in.domain.CommonLogic.Sign.Dto.request.*;
import com.inProject.in.domain.CommonLogic.Sign.Dto.response.*;
import com.inProject.in.domain.CommonLogic.Sign.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
@Tag(name = "sign", description = "로그인, 회원가입 api")
public class SignController {
    private final Logger log = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;
    private final FindService findService;
    private final MailService mailService;

    @PostMapping("/sign-in")
    @Operation(summary = "로그인 시도", description = "생성되어있는 계정으로 로그인합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseSignInDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "로그인 실패")
    })
    public ResponseEntity<ResponseSignInDto> signIn(@RequestBody RequestSignInDto requestSignInDto) throws CustomException {
        log.info("SignController signIn ==> 로그인 시도   id : " + requestSignInDto.getUsername());

        ResponseSignInDto responseSignInDto = signService.signIn(requestSignInDto);

        if(responseSignInDto.getCode() == 0){
            log.info("로그인 성공 ==> token : " + responseSignInDto.getToken());
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseSignInDto);
    }

    @PostMapping("/sign-up/validCodeSend")
    @Operation(summary = "인증 코드 발송", description = "메일로 인증코드를 보냅니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "코드 전송 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseCheckIdDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "코드 전송 실패")
            })
    public ResponseEntity<ResponseIsSuccessDto> validCodeSend(@RequestBody RequestFindDto requestFindDto){
        ResponseIsSuccessDto responseCheckMailDto = mailService.validCodeSend(requestFindDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseCheckMailDto);
    }


    @PostMapping("/sign-up/validMail")
    @Operation(summary = "입력한 인증 코드 검증", description = "발송한 인증코드를 통해 검증합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "코드 확인 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseCheckIdDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "코드 확인 실패")
            })
    public ResponseEntity<ResponseIsSuccessDto> validMail(@RequestBody RequestValidCodeDto requestValidCodeDto){
        ResponseIsSuccessDto responseIsSuccessDto = mailService.validMail(requestValidCodeDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseIsSuccessDto);
    }

    @PostMapping("/sign-up/checkId")
    @Operation(summary = "입력한 인증 코드 검증", description = "발송한 인증코드를 통해 검증합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "코드 확인 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseCheckIdDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "코드 확인 실패")
            })
    public ResponseEntity<ResponseIsSuccessDto> checkId(@RequestBody RequestCheckIdDto requestCheckIdDto){
        ResponseIsSuccessDto responseIsSuccessDto = signService.checkId(requestCheckIdDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseIsSuccessDto);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입을 시도합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseSignUpDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "회원가입 실패")
    })
    public ResponseEntity<ResponseSignUpDto> signUp(@RequestBody RequestSignUpDto requestSignUpDto){
        log.info("SignController signUp ==> 회원가입 시도   id : " + requestSignUpDto.getUsername() + " mail : " + requestSignUpDto.getMail() +
                " role : " + requestSignUpDto.getRole());
        ResponseSignUpDto responseSignUpDto = signService.signUp(requestSignUpDto);

        log.info("회원가입 완료");
        return ResponseEntity.status(HttpStatus.OK).body(responseSignUpDto);
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "refresh 토큰으로 access 토큰을 재발급합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "재발급 성공", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseRefreshDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "재발급 실패")
    })
    public ResponseEntity<ResponseRefreshDto> reissue(@RequestBody RequestRefreshDto requestRefreshDto, HttpServletRequest request){
        log.info("SignController reissue ==> 토큰 재발급 메서드");
        ResponseRefreshDto responseRefreshDto = signService.reissue(requestRefreshDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseRefreshDto);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패")
    })
    public ResponseEntity<String> logout(@RequestBody RequestLogoutDto requestLogoutDto){
        log.info("SignController logout ==> 로그아웃 시작");
        signService.logout(requestLogoutDto);
        return ResponseEntity.ok("로그아웃 완료");
    }

    @GetMapping("/exception")
    public void exception() throws CustomException{
        throw new CustomException(ConstantsClass.ExceptionClass.SIGN, HttpStatus.UNAUTHORIZED, "접근이 금지되었습니다.");
    }



//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e){
//        HttpHeaders responseHeaders = new HttpHeaders();
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//
//        log.info("ExceptionHandler ==> " + e.getCause() + " " + e.getMessage());
//
//        Map<String, String> m = new HashMap<>();
//
//        m.put("error type", httpStatus.getReasonPhrase());
//        m.put("code", "400");
//        m.put("message", "에러 발생");
//
//        return new ResponseEntity<>(m, responseHeaders, httpStatus);
//    }
}
