package com.inProject.in.domain.Profile.service;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.config.security.JwtTokenProvider;
import com.inProject.in.domain.Profile.Dto.request.RequestProject_skillDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseProject_skillDto;
import com.inProject.in.domain.Profile.entity.Project_skill;
import com.inProject.in.domain.Profile.repository.Project_skillRepository;
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
public class Project_skillServiceImpl {
    private UserRepository userRepository;
    private Project_skillRepository project_skillRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger log = LoggerFactory.getLogger(Project_skillServiceImpl.class);
    @Autowired
    public Project_skillServiceImpl(UserRepository userRepository,
                                  Project_skillRepository project_skillRepository,
                                    JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.project_skillRepository = project_skillRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<ResponseProject_skillDto> getProject_skill(HttpServletRequest request){
        User user = getUserFromRequest(request);

        List<Project_skill> project_skill = project_skillRepository.findProject_skillByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Project_skillService getProject_skill 에서 유효하지 않은 user id : " + user.getId()));

        List<ResponseProject_skillDto> responseProject_skillDto = new ArrayList<>();

        for(Project_skill projectSkill : project_skill){
            responseProject_skillDto.add(new ResponseProject_skillDto(projectSkill));
        }

        return responseProject_skillDto;
    }
    @Transactional
    public ResponseProject_skillDto createProject_skill(RequestProject_skillDto requestProject_skillDto, HttpServletRequest request){
        User user = getUserFromRequest(request);
        Project_skill project_skill = requestProject_skillDto.toEntity(user);
        Project_skill savedProject_skill = project_skillRepository.save(project_skill);
        user.getProjectSkillList().add(savedProject_skill);

        ResponseProject_skillDto responseProject_skillDto = new ResponseProject_skillDto(savedProject_skill);

        return responseProject_skillDto;
    }
    @Transactional
    public ResponseProject_skillDto updateProject_skill(Long projectSkill_id, RequestProject_skillDto requestProject_skillDto, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Project_skill project_skill = project_skillRepository.findById(projectSkill_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user.getId() + "는 유효하지 않은 id값입니다."));

        if(!project_skill.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        project_skill.updateProjectSkill(requestProject_skillDto);
        ResponseProject_skillDto responseProject_skillDto = new ResponseProject_skillDto(project_skill);

        return responseProject_skillDto;
    }
    @Transactional
    public void deleteProject_skill(Long projectSkill_id, HttpServletRequest request){
        User user = getUserFromRequest(request);

        Project_skill project_skill = project_skillRepository.findById(projectSkill_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user.getId() + "는 유효하지 않은 id값입니다."));

        if(!project_skill.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        project_skillRepository.deleteById(projectSkill_id);
        log.info("ProjectSkillService deleteProjectSkill ==> 삭제 성공");
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
