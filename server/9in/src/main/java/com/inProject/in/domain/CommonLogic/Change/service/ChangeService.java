package com.inProject.in.domain.CommonLogic.Change.service;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.CommonLogic.Change.Dto.request.RequestChangePwDto;
import com.inProject.in.domain.CommonLogic.Change.Dto.response.ResponseChangeDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestCheckIdDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseCheckIdDto;
import com.inProject.in.domain.User.entity.User;
import com.inProject.in.domain.User.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(ChangeService.class);
    @Autowired
    public ChangeService(UserRepository userRepository,
                         PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public ResponseChangeDto changePw(RequestChangePwDto requestChangePwDto){
        String username = requestChangePwDto.getUsername();
        String curPw = requestChangePwDto.getCurPw();
        String newPw = requestChangePwDto.getNewPw();
        String checkPw = requestChangePwDto.getCheckPw();

        log.info("ChangeService changePw ==> username : " + username + " 비밀번호 변경 시작");

        if(!newPw.equals(checkPw)){
            throw new CustomException(ConstantsClass.ExceptionClass.CHANGE, HttpStatus.BAD_REQUEST, "새 비밀번호와 확인 비밀번호가 다름");
        }

        User user = userRepository.getByUsername(username)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.CHANGE, HttpStatus.NOT_FOUND, username + "은 없는 유저입니다."));

        if(!passwordEncoder.matches(curPw, user.getPassword())){
            throw new CustomException(ConstantsClass.ExceptionClass.CHANGE, HttpStatus.CONFLICT, "기존 비밀번호 틀림");
        }

        log.info("changePw ==> DB에 유저 확인");

        user.setPassword(passwordEncoder.encode(newPw));
        log.info("changePw ==> 비밀번호 변경");

        return new ResponseChangeDto("비밀번호 변경 성공", true);
    }
    @Transactional
    public ResponseCheckIdDto checkId(RequestCheckIdDto requestCheckIdDto){
        User user = userRepository.getByUsername(requestCheckIdDto.getUsername())
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.FIND, HttpStatus.NOT_FOUND,"존재하지 않는 유저"));
        boolean success = false;

        if(user != null){
            success = true;
        }

        ResponseCheckIdDto responseCheckIdDto = new ResponseCheckIdDto("유저 존재", success);

        return responseCheckIdDto;
    }
}
