package com.inProject.in.domain.Profile.service;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.config.security.JwtTokenProvider;
import com.inProject.in.domain.Profile.Dto.request.RequestCertificateDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseCertificateDto;
import com.inProject.in.domain.Profile.entity.Certificate;
import com.inProject.in.domain.Profile.repository.CertificateRepository;
import com.inProject.in.domain.User.entity.User;
import com.inProject.in.domain.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateServiceImpl {
    private UserRepository userRepository;
    private CertificateRepository certificateRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger log = LoggerFactory.getLogger(CertificateServiceImpl.class);
    @Autowired
    public CertificateServiceImpl(UserRepository userRepository,
                                  CertificateRepository certificateRepository,
                                  JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<ResponseCertificateDto> getCertificate(Long user_id){

        List<Certificate> certificateList = certificateRepository.findCertificateByUserId(user_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.NOT_FOUND, user_id + "는 certificate를 생성하지 않았음."));

        List<ResponseCertificateDto> responseCertificateDtoList = new ArrayList<>();

        for(Certificate certificate : certificateList){
            responseCertificateDtoList.add(new ResponseCertificateDto(certificate));
            log.info("CertificateService getCertificate ==> user id : " + user_id + " 자격증 정보 : " + certificate);
        }

        return responseCertificateDtoList;
    }
    @Transactional
    public ResponseCertificateDto createCertificate(RequestCertificateDto requestCertificateDto, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Certificate certificate = requestCertificateDto.toEntity(user);
        Certificate savedCertificate = certificateRepository.save(certificate);
        user.getCertificateList().add(savedCertificate);

        ResponseCertificateDto responseCertificateDto = new ResponseCertificateDto(savedCertificate);

        log.info("CertificateService createCertificate ==> username : " + user.getUsername() + " 자격증 이름 : " + savedCertificate.getCertificate_name());
        return responseCertificateDto;
    }

    @Transactional
    public ResponseCertificateDto updateCertificate(Long certificate_id, RequestCertificateDto requestCertificateDto, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Certificate certificate = certificateRepository.findById(certificate_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.NOT_FOUND, certificate_id + "를 찾지 못함" ));
        
        if(!certificate.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        log.info("CertificateService updateCertificate ==> 업데이트 전 : " + certificate.toString());
        certificate.updateCertificate(requestCertificateDto);
        ResponseCertificateDto responseCertificateDto = new ResponseCertificateDto(certificate);

        log.info("CertificateService updateCertificate ==> 업데이트 후 + " + certificate.toString());
        return responseCertificateDto;
    }
    @Transactional
    public void deleteCertificate(Long certificate_id, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Certificate certificate = certificateRepository.findById(certificate_id)
                        .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.NOT_FOUND, certificate_id + "를 찾지 못함" ));

        if(!certificate.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        certificateRepository.deleteById(certificate_id);
        log.info("CertificateService deleteCertificate ==> 삭제 완료");
    }

    private User getUserFromRequest(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        User user;

        if(token != null && jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsername(token);

            return user = userRepository.getByUsername(username)
                    .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.BOARD, HttpStatus.NOT_FOUND, "request로부터 user를 찾지 못함"));
        }
        else{
            throw new CustomException(ConstantsClass.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "token이 없거나, 권한이 유효하지 않습니다.");
        }
    }
}
