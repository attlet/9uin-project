package com.inProject.in.domain.Notification.Dto.response;

import com.inProject.in.domain.Notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseNotificationDto {
    private Long id;
    private Long receiver_id;
    private String message;
    private String alarm_type;
    private LocalDateTime createAt;

    public ResponseNotificationDto(Notification notification){
        this.id = notification.getId();
        this.receiver_id = notification.getReceiver().getId();
        this.message = notification.getMessage();
        this.alarm_type = notification.getAlarm_type();
        this.createAt = notification.getCreateAt();
    }

}
