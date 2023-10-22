package com.inProject.in.domain.Profile.Dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseProfileDto {
    private List<ResponseCertificateDto> certificateDtoList;
    private List<ResponseProject_skillDto> projectSkillDtoList;
    private List<ResponseEducationDto> educationDtoList;
    private List<ResponseJob_exDto> jobExDtoList;
    private ResponseMyInfoDto myInfoDto;
    private Long clipedCounts;
    private Long projectCounts;
    private Long studyCounts;

//    public ResponseProfileDto(ResponseCertificateDto responseCertificateDto,
//                              ResponseProject_skillDto responseProjectSkillDto,
//                              ResponseJob_exDto responseJobExDto,
//                              ResponseEducationDto responseEducationDto,
//                              ResponseMyInfoDto responseMyInfoDto,
//                              ){
//
//        this.certificateDto = responseCertificateDto;
//        this.educationDto = responseEducationDto;
//        this.projectSkillDto = responseProjectSkillDto;
//        this.jobExDto = responseJobExDto;
//        this.myInfoDto = responseMyInfoDto;
//
//    }
}
