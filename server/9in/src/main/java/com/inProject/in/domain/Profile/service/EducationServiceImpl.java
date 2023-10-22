package com.inProject.in.domain.Profile.service;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.config.security.JwtTokenProvider;
import com.inProject.in.domain.Profile.Dto.request.RequestEducationDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseEducationDto;
import com.inProject.in.domain.Profile.entity.Education;
import com.inProject.in.domain.Profile.repository.EducationRepository;
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
public class EducationServiceImpl {
    private UserRepository userRepository;
    private EducationRepository educationRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger log = LoggerFactory.getLogger(EducationServiceImpl.class);
    @Autowired
    public EducationServiceImpl(UserRepository userRepository,
                                EducationRepository educationRepository,
                                JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<ResponseEducationDto> getEducation(Long user_id){
        List<Education> educationList = educationRepository.findEducationByUserId(user_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.NOT_FOUND, "EducationService getEducation 에서 유효하지 않은 user id : " + user_id));

        List<ResponseEducationDto> responseEducationDto = new ArrayList<>();

        for(Education education : educationList){
            responseEducationDto.add(new ResponseEducationDto(education));
            log.info("EducationService getEducation ==> user id : " + user_id + " education : " + education.toString());
        }

        return responseEducationDto;
    }
    @Transactional
    public ResponseEducationDto createEducation(RequestEducationDto requestEducationDto, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Education education = requestEducationDto.toEntity(user);
        Education savedEducation = educationRepository.save(education);
        user.getEducationList().add(savedEducation);

        ResponseEducationDto responseEducationDto = new ResponseEducationDto(savedEducation);

        log.info("EducationService createEducation ==> username : " + user.getUsername() + " education : " + savedEducation.toString());
        return responseEducationDto;
    }
    @Transactional
    public ResponseEducationDto updateEducation(Long education_id, RequestEducationDto requestEducationDto, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Education education = educationRepository.findById(education_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user.getId() + "는 education을 생성하지 않음"));

        if(!education.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        log.info("EducationService updateEducation ==> 업데이트 전 : " + education.toString());

        education.updateEducation(requestEducationDto);
        ResponseEducationDto responseEducationDto = new ResponseEducationDto(education);

        log.info("EducationService updateEducation ==> 업데이트 후 : " + education.toString());
        return responseEducationDto;
    }
    @Transactional
    public void deleteEducation(Long education_id, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Education education = educationRepository.findById(education_id)
                        .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user.getId() + "는 education을 생성하지 않음"));

        if(!education.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        educationRepository.deleteById(education_id);
        log.info("EducationService deleteEducation ==> 삭제 완료");
    }

    private User getUserFromRequest(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        User user;

        if(token != null && jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsername(token);

            return user = userRepository.getByUsername(username)
                    .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.BOARD, HttpStatus.BAD_REQUEST, "BoardService ==> request로부터 user를 찾지 못함"));
        }
        else{
            throw new CustomException(ConstantsClass.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "token이 없거나, 권한이 유효하지 않습니다.");
        }


    }
}
