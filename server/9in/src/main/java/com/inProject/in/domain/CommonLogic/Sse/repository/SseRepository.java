//sserepository
package com.inProject.in.domain.CommonLogic.Sse.repository;


import com.inProject.in.config.security.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class SseRepository {
    // 모든 Emitters를 저장하는 ConcurrentHashMap
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Logger log = LoggerFactory.getLogger(SseRepository.class);
    /**
     * 주어진 아이디와 이미터를 저장
     *
     *  id      - 사용자 아이디.
     * @param emitter - 이벤트 Emitter.
     */

    public void save(String username, SseEmitter emitter) {

        log.info("emitter save ==> username : " + username);
        emitters.put(username, emitter);
    }
    //(id , emitter<time_out>)
    /**
     * 주어진 아이디의 Emitter를 제거
     *
     * id - 사용자 아이디.
     */
    public void deleteById(String username) {
        emitters.remove(username);
    }

    /**
     * 주어진 아이디의 Emitter를 가져옴.
     *
     * id - 사용자 아이디.
     * @return SseEmitter - 이벤트 Emitter.
     */
    public SseEmitter get(String username) {
        return emitters.get(username);
    }
}