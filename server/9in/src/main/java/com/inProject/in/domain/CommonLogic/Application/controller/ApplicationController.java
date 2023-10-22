package com.inProject.in.domain.CommonLogic.Application.controller;

import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.CommonLogic.Application.Dto.ResponseApplicationDto;
import com.inProject.in.domain.CommonLogic.Application.Dto.RequestApplicationDto;
import com.inProject.in.domain.CommonLogic.Application.service.ApplicationService;
import com.inProject.in.domain.CommonLogic.Sse.service.SseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@Tag(name = "application", description = "게시글에 지원하는 api")
public class ApplicationController {
    private ApplicationService applicationService;
    private  SseService sseService;
    @Autowired
    public ApplicationController(ApplicationService applicationService, SseService sseService){
        this.applicationService = applicationService;
        this.sseService = sseService;
    }

    @PostMapping()
    @Operation(summary = "지원하기", description = "게시글에 지원합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "지원 조회 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseApplicationDto.class))
                    })
            })
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseApplicationDto> createApplication(@RequestBody RequestApplicationDto requestApplicationDto){

        try{
            ResponseApplicationDto responseApplicationDto = applicationService.createApplication(requestApplicationDto);
            //sseEvent 게시자의 id 로 바꿔야됨.
            sseService.subscribe(String.valueOf(requestApplicationDto.getUser_id()),requestApplicationDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseApplicationDto);
        }catch (CustomException e){
            throw e;
        }
    }

    @DeleteMapping()
    @Operation(summary = "지원 취소", description = "게시글에 지원한 걸 취소합니다.")
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<String> deleteApplication(RequestApplicationDto requestApplicationDto){
        try{
            applicationService.deleteApplication(requestApplicationDto);

            return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
        }catch (CustomException e){
            throw e;
        }
    }

    @PostMapping("reject")
    @Operation(summary = "지원 거절", description = "게시글에 지원한 걸 거절합니다.")
    public ResponseEntity<String> rejectApplication(RequestApplicationDto requestApplicationDto){
        try{
            applicationService.rejectApplication(requestApplicationDto);
            return ResponseEntity.status(HttpStatus.OK).body("거절완료");
        }catch (CustomException e){
            throw e;
        }
    }

    @PostMapping("accept")
    @Operation(summary = "지원 거절", description = "게시글에 지원한 걸 거절합니다.")
    public ResponseEntity<String> acceptApplication(RequestApplicationDto requestApplicationDto){
        try{
            applicationService.acceptApplication(requestApplicationDto);
            return ResponseEntity.status(HttpStatus.OK).body("수락완료");
        }catch (CustomException e){
            throw e;
        }
    }
}