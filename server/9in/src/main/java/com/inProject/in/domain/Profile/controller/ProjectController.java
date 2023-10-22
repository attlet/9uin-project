package com.inProject.in.domain.Profile.controller;

import com.inProject.in.domain.Profile.Dto.request.RequestProject_skillDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseProject_skillDto;
import com.inProject.in.domain.Profile.service.Project_skillServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project_skill")
@Tag(name = "project_skill", description = "프로젝트 경험 관련 api")
public class ProjectController {

    private final Project_skillServiceImpl projectSkillService;

    @Autowired
    public ProjectController(Project_skillServiceImpl projectSkillService){
        this.projectSkillService = projectSkillService;
    }
    @PostMapping()
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseProject_skillDto> createProject_skill(@RequestBody RequestProject_skillDto requestProjectSkillDto, HttpServletRequest request){
        ResponseProject_skillDto responseProjectSkillDto = projectSkillService.createProject_skill(requestProjectSkillDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseProjectSkillDto);
    }

    @PutMapping("/{projectSkill_id}")
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseProject_skillDto> updateProject_skill(@RequestBody RequestProject_skillDto requestProjectSkillDto,
                                                                        @PathVariable(name = "projectSkill_id") Long projectSkill_id,
                                                                        HttpServletRequest request){
        ResponseProject_skillDto responseProjectSkillDto = projectSkillService.updateProject_skill(projectSkill_id, requestProjectSkillDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseProjectSkillDto);
    }

    @DeleteMapping("/{projectSkill_id}")
    public ResponseEntity<String> deleteProjectSkill(@PathVariable(name = "projectSkill_id") Long projectSkill_id, HttpServletRequest request){
        projectSkillService.deleteProject_skill(projectSkill_id, request);

        return ResponseEntity.status(HttpStatus.OK).body("project skill 삭제 성공");
    }
}
