package com.inProject.in.domain.CommonLogic.Sse.controller;




import com.inProject.in.domain.CommonLogic.Application.Dto.RequestApplicationDto;
import com.inProject.in.domain.CommonLogic.Sse.service.SseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController("/sse")
public class SseController {

    private final SseService sseService;
    private SseController(SseService sseService){
        this.sseService = sseService;
    }


    //로그인성공시 반드시 sseConnect를 호출해야 합니다.
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    @GetMapping(value = "/connect")  //produces = MediaType.TEXT_EVENT_STREAM_VALUE
    public void SseConnect(HttpServletRequest request){
        SseEmitter sse = sseService.createEmitter(request);
    }


    // id 에게 알림메세지를 보낸다. (test용 api 입니다. 원래는 api call로 작동하는 로직이 아닙니다.)
    @GetMapping(value = "/apply") //반드시 json으로 반환 produces = MediaType.APPLICATION_JSON_VALUE//
    public SseEmitter subscribe(@PathVariable String id,  @RequestBody RequestApplicationDto data) {
        Long board_id_long = Long.parseLong(id);
        String data_1 = "test";
        return sseService.subscribe(board_id_long,data_1);
    }
}
