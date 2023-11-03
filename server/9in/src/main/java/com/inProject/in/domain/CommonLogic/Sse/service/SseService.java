//sseService
package com.inProject.in.domain.CommonLogic.Sse.service;

import com.inProject.in.domain.CommonLogic.Application.Dto.RequestApplicationDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


public interface SseService {
    SseEmitter subscribe(Long user_id, String data);
    void sendToClient(Long id, String data);
    SseEmitter createEmitter(HttpServletRequest request);

    void closeEmitter(Long id);
}
