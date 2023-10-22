package com.inProject.in.domain.CommonLogic.Change.controller;

import com.inProject.in.domain.CommonLogic.Change.Dto.request.RequestChangePwDto;
import com.inProject.in.domain.CommonLogic.Change.Dto.response.ResponseChangeDto;
import com.inProject.in.domain.CommonLogic.Change.service.ChangeService;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestCheckIdDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseCheckIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.util.XMLEventAllocator;

@RestController
@RequestMapping("/change")
@Tag(name = "change", description = "비밀번호 변경 api")
public class ChangeController {

    private final ChangeService changeService;

    @Autowired
    public ChangeController(ChangeService changeService){
        this.changeService = changeService;
    }


    @PostMapping("/password")
    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseChangeDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "비밀번호 변경 실패")
            })
    public ResponseEntity<ResponseChangeDto> changePw(@RequestBody RequestChangePwDto requestChangePwDto){
        ResponseChangeDto responseChangeDto = changeService.changePw(requestChangePwDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseChangeDto);
    }

    @PostMapping("/checkId")
    @Operation(summary = "비밀번호 변경 전 체크", description = "비밀번호 변경 전에, 아이디가 있는 지 체크합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "아이디 존재 확인", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseCheckIdDto.class))
            })
    })
    public ResponseEntity<ResponseCheckIdDto> checkId(@RequestBody RequestCheckIdDto requestCheckIdDto){
        ResponseCheckIdDto responseCheckIdDto = changeService.checkId(requestCheckIdDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseCheckIdDto);
    }
}
