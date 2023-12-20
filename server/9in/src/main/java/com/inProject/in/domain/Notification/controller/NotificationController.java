package com.inProject.in.domain.Notification.controller;

import com.inProject.in.domain.Board.Dto.response.ResponseBoardListDto;
import com.inProject.in.domain.Notification.Dto.response.ResponseNotificationDto;
import com.inProject.in.domain.Notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notify")
@Tag(name = "notification", description = "알림 관련 api")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping()
    @Operation(summary ="사용자의 알림 리스트 조회", description = "확인하지 않은 알림들을 가져옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "알림 리스트 조회 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseNotificationDto.class))
                    })
            })
    public ResponseEntity<List<ResponseNotificationDto>> getNotifications(@RequestParam Long user_id){
        List<ResponseNotificationDto> responseNotificationDtoList = notificationService.getNotificationList(user_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseNotificationDtoList);
    }

    @PutMapping()
    @Operation(summary = "알림 확인하기", description = "읽지 않은 알림을 확인으로 체크합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "알림 확인 성공", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ResponseNotificationDto.class))
                    })
            })
    public ResponseEntity<ResponseNotificationDto> updateNotification(@RequestParam Long notification_id){
        ResponseNotificationDto responseNotificationDto = notificationService.updateToCheckNotification(notification_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseNotificationDto);
    }
}
