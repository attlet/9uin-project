package com.inProject.in.domain.Profile.controller;

import com.inProject.in.domain.Profile.Dto.request.RequestEducationDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseEducationDto;
import com.inProject.in.domain.Profile.service.EducationServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/education")
@Tag(name = "education ", description = "학력 관련 api")
public class EducationController {

    private final EducationServiceImpl educationService;

    @Autowired
    public EducationController(EducationServiceImpl educationService){
        this.educationService = educationService;
    }
    @PostMapping()
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseEducationDto> createEducation(@RequestBody RequestEducationDto requestEducationDto, HttpServletRequest request){
        ResponseEducationDto responseEducationDto = educationService.createEducation(requestEducationDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseEducationDto);
    }

    @PutMapping("/{education_id}")
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseEducationDto> updateEducation(@RequestBody RequestEducationDto requestEducationDto,
                                                                @PathVariable(name = "education_id") Long education_id,
                                                                HttpServletRequest request){
        ResponseEducationDto responseEducationDto = educationService.updateEducation(education_id,requestEducationDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseEducationDto);
    }

    @DeleteMapping("/{education_id}")
    public ResponseEntity<String> deleteEducation(@PathVariable(name = "education_id") Long education_id, HttpServletRequest request){
        educationService.deleteEducation(education_id, request);

        return ResponseEntity.status(HttpStatus.OK).body("education 삭제 성공");
    }
}
