package com.inProject.in.domain.CommonLogic.Find.service;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestCheckIdDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestFindDto;

import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestValidCodeDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseCheckIdDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseCheckMailDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseFindIdDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseIsSuccessDto;
import com.inProject.in.domain.CommonLogic.Mail.Dto.SendMailDto;
import com.inProject.in.domain.CommonLogic.Mail.service.MailService;
import com.inProject.in.domain.User.entity.User;
import com.inProject.in.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    private final Logger log = LoggerFactory.getLogger(FindService.class);

    char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

//    @Transactional
//    public ResponseIsSuccessDto validCodeSend(RequestFindDto requestFindDto){    //메일로 인증번호 전송하는 것.
//        log.info("mail 인증 시작");
//        String mail = requestFindDto.getMail();
//
//        User user = userRepository.getByMail(mail)
//                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.SIGN, HttpStatus.NOT_FOUND, "등록되지 않은 메일입니다."));
//
//        log.info("mail이 등록된 것 확인");
//
//        String validNum = MakeValidNum();
//
//        SendMailDto sendMailDto = new SendMailDto();
//
//        sendMailDto.setAddress(user.getMail());
//        sendMailDto.setSubject(user.getUsername() + "님, 인증번호가 전송되었습니다.");
//        sendMailDto.setText("인증번호는 " + validNum + " 입니다.");
//        mailService.sendMail(sendMailDto);
//
//        log.info("mail 전송 완료");
//
//        findRepository.setValidCode(mail, validNum);
//
//        return new ResponseIsSuccessDto("success", true);
//    }
//
//    @Transactional
//    public ResponseIsSuccessDto validMail(RequestValidCodeDto requestValidCodeDto){
//        String mail = requestValidCodeDto.getMail();
//        String inputCode = requestValidCodeDto.getValidCode();
//        String validCode = findRepository.getValidCode(mail)
//                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.FIND, HttpStatus.NOT_FOUND, "지정한 메일로 보낸 인증코드 없음"));
//
//        if(validCode.equals(inputCode)){
//            return new ResponseIsSuccessDto("success", true);
//        }
//        else{
//            return new ResponseIsSuccessDto("failed", false);
//        }
//    }

    @Transactional
    public ResponseFindIdDto findId(RequestFindDto requestFindDto){
        String mail = requestFindDto.getMail();

        User user = userRepository.getByMail(mail)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.SIGN, HttpStatus.NOT_FOUND, mail + "은 없는 mail정보입니다."));

        log.info("findId ==> user : " + user.getUsername() + " 찾음");
        log.info("username : " + user.getUsername());
        ResponseFindIdDto responseFindIdDto = new ResponseFindIdDto(user.getUsername());

        return responseFindIdDto;
    }

    @Transactional
    public ResponseIsSuccessDto findPw(RequestFindDto requestFindPwDto){

        String mail = requestFindPwDto.getMail();

//        User user = userRepository.getByUsername(username)
//                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.SIGN, HttpStatus.BAD_REQUEST, username + "는 없는 유저입니다."));

        User user = userRepository.getByMail(mail)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.SIGN, HttpStatus.NOT_FOUND, "등록되지 않은 메일입니다."));

        log.info("findPw ==> 유저 정보 확인 성공");

        String newPw = MakeTempPw();
        log.info("임시 비밀번호 생성");

        SendMailDto sendMailDto = new SendMailDto();

        sendMailDto.setAddress(user.getMail());
        sendMailDto.setSubject(user.getUsername() + "님, 임시 비밀번호가 전송되었습니다.");
        sendMailDto.setText("임시 비밀번호는 " + newPw + " 입니다.");

        mailService.sendMail(sendMailDto);
        log.info("메일 전송 성공");

        user.setPassword(passwordEncoder.encode(newPw));

        return new ResponseIsSuccessDto("임시 비밀번호 부여 성공", true);
    }
    @Transactional
    public ResponseCheckIdDto checkId(RequestCheckIdDto requestCheckIdDto){
        User user = userRepository.getByUsername(requestCheckIdDto.getUsername())
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.FIND, HttpStatus.NOT_FOUND, "존재하지 않는 유저"));

        boolean success = false;

        if(user != null){
            success = true;
        }

        ResponseCheckIdDto responseCheckIdDto = new ResponseCheckIdDto("유저 존재", success);

        return responseCheckIdDto;
    }

    public String MakeTempPw(){
        String str = "";

        int idx = 0;

        for (int i=0; i<15; i++) {
            idx = (int) (charSet.length * Math.random());   //random을 통해 0 ~ 1 사이의 double형 값 반환.
            str += charSet[idx];
        }
        return str;

    }
}
