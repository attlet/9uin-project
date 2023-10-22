package com.inProject.in.domain.Profile.service;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.config.security.JwtTokenProvider;
import com.inProject.in.domain.Profile.Dto.request.RequestJob_exDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseJob_exDto;
import com.inProject.in.domain.Profile.entity.Job_ex;
import com.inProject.in.domain.Profile.repository.Job_exRepository;
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
public class Job_exServiceImpl {
    private UserRepository userRepository;
    private Job_exRepository job_exRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger log = LoggerFactory.getLogger(Job_exServiceImpl.class);
    @Autowired
    public Job_exServiceImpl(UserRepository userRepository,
                                  Job_exRepository job_exRepository,
                             JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.job_exRepository = job_exRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<ResponseJob_exDto> getJob_ex(Long user_id){
        List<Job_ex> job_ex = job_exRepository.findJob_exByUserId(user_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user_id + "는 jobex를 생성하지 않음"));

        List<ResponseJob_exDto> responseJob_exDto = new ArrayList<>();

        for(Job_ex jobEx : job_ex){
            responseJob_exDto.add(new ResponseJob_exDto(jobEx));
            log.info("jobex getjobex ==> user id : " + user_id + " job_ex : " + jobEx.toString());
        }
        return responseJob_exDto;
    }
    @Transactional
    public ResponseJob_exDto createJob_ex(RequestJob_exDto requestJob_exDto, HttpServletRequest request){
        User user = getUserFromRequest(request);
        Job_ex job_ex = requestJob_exDto.toEntity(user);
        Job_ex savedJob_ex = job_exRepository.save(job_ex);
        user.getJobExList().add(savedJob_ex);

        ResponseJob_exDto responseJob_exDto = new ResponseJob_exDto(savedJob_ex);
        log.info("JobexService createJobex ==> username : " + user.getUsername() + " jobEx : " + job_ex.toString());
        return responseJob_exDto;
    }
    @Transactional
    public ResponseJob_exDto updateJob_ex(Long jobEx_id, RequestJob_exDto requestJob_exDto, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Job_ex jobEx = job_exRepository.findById(jobEx_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user.getId() + "는 jobex를 생성하지 않음"));

        if(!jobEx.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        log.info("JobexService updateJobex ==> 업데이트 전 : " + jobEx.toString());
        jobEx.updateJobEx(requestJob_exDto);
        ResponseJob_exDto responseJob_exDto = new ResponseJob_exDto(jobEx);
        log.info("JobexService updateJobex ==> 업데이트 후 : " + jobEx.toString());

        return responseJob_exDto;
    }
    @Transactional
    public void deleteJob_ex(Long jobEx_id, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Job_ex jobEx = job_exRepository.findById(jobEx_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user.getId() + "는 jobex를 생성하지 않음"));

        if(!jobEx.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        job_exRepository.deleteById(jobEx_id);
        log.info("JobExService deleteJobex ==> 삭제 성공");
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
