package com.inProject.in.domain.Notification.controller;

import com.inProject.in.domain.Notification.Dto.response.ResponseNotificationDto;
import com.inProject.in.domain.Notification.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<ResponseNotificationDto>> getNotifications(@RequestParam Long user_id){
        List<ResponseNotificationDto> responseNotificationDtoList = notificationService.getNotificationList(user_id);

        return ResponseEntity.status(HttpStatus.OK).body(responseNotificationDtoList);
    }
}
