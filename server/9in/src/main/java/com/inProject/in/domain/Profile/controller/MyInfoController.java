package com.inProject.in.domain.Profile.controller;

import com.inProject.in.domain.Profile.Dto.request.RequestMyInfoDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseMyInfoDto;
import com.inProject.in.domain.Profile.service.MyInfoServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myinfo")
@Tag(name = "myinfo", description = "내 정보 관련 api")
public class MyInfoController {

    private final MyInfoServiceImpl myInfoService;

    @Autowired
    public MyInfoController(MyInfoServiceImpl myInfoService){
        this.myInfoService = myInfoService;
    }
    @PostMapping()
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseMyInfoDto> createMyInfo(@RequestBody RequestMyInfoDto requestMyInfoDto, HttpServletRequest request){
        ResponseMyInfoDto responseMyInfoDto = myInfoService.createMyInfo(requestMyInfoDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseMyInfoDto);
    }

    @PutMapping()
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseMyInfoDto> updateMyInfo(@RequestBody RequestMyInfoDto requestMyInfoDto, HttpServletRequest request){
        ResponseMyInfoDto responseMyInfoDto = myInfoService.updateMyInfo(requestMyInfoDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseMyInfoDto);
    }
}
