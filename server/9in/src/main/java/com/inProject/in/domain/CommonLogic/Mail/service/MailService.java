package com.inProject.in.domain.CommonLogic.Mail.service;


import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestFindDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.request.RequestValidCodeDto;
import com.inProject.in.domain.CommonLogic.Find.Dto.response.ResponseIsSuccessDto;
import com.inProject.in.domain.CommonLogic.Find.service.FindService;
import com.inProject.in.domain.CommonLogic.Mail.Dto.SendMailDto;
import com.inProject.in.domain.CommonLogic.Mail.repository.MailRepository;
import com.inProject.in.domain.User.entity.User;
import com.inProject.in.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(FindService.class);
    private static final String FROM_ADMIN = "tlsdbstjd124@gmail.com";
    char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    @Transactional
    public void sendMail(SendMailDto sendMailDto){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(sendMailDto.getAddress());
        simpleMailMessage.setSubject(sendMailDto.getSubject());
        simpleMailMessage.setText(sendMailDto.getText());
        simpleMailMessage.setFrom(FROM_ADMIN);

        javaMailSender.send(simpleMailMessage);
    }


    @Transactional
    public ResponseIsSuccessDto validCodeSend(RequestFindDto requestFindDto){    //메일로 인증번호 전송하는 것.
        log.info("mail 인증 시작");
        String mail = requestFindDto.getMail();

        User user = userRepository.getByMail(mail)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.SIGN, HttpStatus.NOT_FOUND, "등록되지 않은 메일입니다."));

        log.info("mail이 등록된 것 확인");

        String validNum = MakeValidNum();

        SendMailDto sendMailDto = new SendMailDto();

        sendMailDto.setAddress(user.getMail());
        sendMailDto.setSubject(user.getUsername() + "님, 인증번호가 전송되었습니다.");
        sendMailDto.setText("인증번호는 " + validNum + " 입니다.");
        sendMail(sendMailDto);

        log.info("mail 전송 완료");

        mailRepository.setValidCode(mail, validNum);

        return new ResponseIsSuccessDto("success", true);
    }

    @Transactional
    public ResponseIsSuccessDto validCodeSendForSignUp(RequestFindDto requestFindDto){
        log.info("mail 인증 시작");
        String mail = requestFindDto.getMail();

        log.info("mail이 등록된 것 확인");

        String validNum = MakeValidNum();

        SendMailDto sendMailDto = new SendMailDto();

        sendMailDto.setAddress(mail);
        sendMailDto.setSubject("9uin 회원가입 인증번호가 전송되었습니다.");
        sendMailDto.setText("인증번호는 " + validNum + " 입니다.");
        sendMail(sendMailDto);

        log.info("mail 전송 완료");

        mailRepository.setValidCode(mail, validNum);

        return new ResponseIsSuccessDto("success", true);
    }

    @Transactional
    public ResponseIsSuccessDto validMail(RequestValidCodeDto requestValidCodeDto){
        String mail = requestValidCodeDto.getMail();
        String inputCode = requestValidCodeDto.getValidCode();
        String validCode = mailRepository.getValidCode(mail)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.FIND, HttpStatus.NOT_FOUND, "지정한 메일로 보낸 인증코드 없음"));

        if(validCode.equals(inputCode)){
            return new ResponseIsSuccessDto("success", true);
        }
        else{
            return new ResponseIsSuccessDto("failed", false);
        }
    }

    public String MakeValidNum(){
        String str = "";

        int idx = 0;

        for (int i=0; i<6; i++) {
            idx = (int) (charSet.length * Math.random());   //random을 통해 0 ~ 1 사이의 double형 값 반환.
            str += charSet[idx];
        }
        return str;
    }

}
