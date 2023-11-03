//sseServiceimple
package com.inProject.in.domain.CommonLogic.Sse.service.impl;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.config.security.CustomAccessDeniedHandler;
import com.inProject.in.config.security.JwtTokenProvider;
import com.inProject.in.domain.CommonLogic.Application.Dto.RequestApplicationDto;
import com.inProject.in.domain.CommonLogic.Sse.repository.SseRepository;
import com.inProject.in.domain.CommonLogic.Sse.service.SseService;
import com.inProject.in.domain.User.entity.User;
import com.inProject.in.domain.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


@Service
public class SseServiceImpl implements SseService {
    // 기본 타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private  SseRepository sseRepository;
    private ApplicationEventPublisher applicationEventPublisher;
    private final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    @Autowired
    SseServiceImpl(SseRepository sseRepository , JwtTokenProvider jwtTokenProvider, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher){
        this.userRepository = userRepository;
        this.sseRepository = sseRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    /**
     * 클라이언트가 구독을 위해 호출하는 메서드.
     *
     * @param user_id - 구독하는 클라이언트의 사용자 아이디.
     * @return SseEmitter - 서버에서 보낸 이벤트 Emitter
     */
    //SseEmitter inputEmitter
    public SseEmitter subscribe(Long user_id, String data) {

        SseEmitter emitter = sseRepository.get(user_id);
        sendToClient(user_id,data);

        return emitter;
    }




    /**
     * 클라이언트에게 데이터를 전송
     *
     * @param id   - 데이터를 받을 사용자의 아이디.
     * @param data - 전송할 데이터.
     */

    public void sendToClient(Long id, String data) {
        SseEmitter emitter = sseRepository.get(id);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(id))
                        .name("sse")
                        .data(data));
                log.info("data 전송 완료 : message => " + data);
            } catch (IOException exception) {
                sseRepository.deleteById(id);
                emitter.completeWithError(exception);
            }
        }
        else if(emitter == null){
            log.info("sseEmitter is null");
        }
    }



    public SseEmitter createEmitter(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        User user;

        if(token != null && jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsername(token);
            user = userRepository.getByUsername(username)
                    .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.SSE, HttpStatus.NOT_FOUND, "sseCreate user를 찾지 못함"));
        }
        else{
            throw new CustomException(ConstantsClass.ExceptionClass.SSE, HttpStatus.UNAUTHORIZED, "token이 없거나, 권한이 유효하지 않습니다.");
        }

        Long id =  user.getId();

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        //더미 데이터 send
//        RequestApplicationDto dummy = new RequestApplicationDto(Long.valueOf(0),Long.valueOf(0),Long.valueOf(0),"dummy data");
        String dummy = "dummy";
        sendToClient(id,dummy);

        sseRepository.save(id, emitter);

//        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.한번 보내고 말것이 아니라 주석처리 하였음.
//        emitter.onCompletion(() -> sseRepository.deleteById(id));

        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> sseRepository.deleteById(id));

        return emitter;
    }

    //로그아웃 시 호출
    public void closeEmitter(Long id){
        sseRepository.deleteById(id);
    }
}