package com.inProject.in.domain.Notification.service;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.Board.service.impl.BoardServiceImpl;
import com.inProject.in.domain.Notification.Dto.request.RequestNotificationDto;
import com.inProject.in.domain.Notification.Dto.response.ResponseNotificationDto;
import com.inProject.in.domain.Notification.entity.Notification;
import com.inProject.in.domain.Notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);

    public List<ResponseNotificationDto> getNotificationList(Long user_id){

        List<Notification> notificationList = notificationRepository.getNotifications(user_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.NOTIFICATION, HttpStatus.NOT_FOUND, "NotificationService에서 " + user_id + " 은 찾지 못함"));

        List<ResponseNotificationDto> responseNotificationDtoList = new ArrayList<>();

        for(Notification notification : notificationList){
            ResponseNotificationDto responseNotificationDto = new ResponseNotificationDto(notification);
            responseNotificationDtoList.add(responseNotificationDto);
        }

        return responseNotificationDtoList;
    }

    public void deleteNotification(Long id){
        notificationRepository.deleteById(id);
        log.info("notification Service ==> " + id + " 알림 삭제");
    }

}
