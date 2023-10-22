package com.inProject.in.domain.Profile.Dto.response;

import com.inProject.in.domain.Profile.entity.Education;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseEducationDto {
    private Long education_id;
    private String school; //대학교
    private String major;  //전공
    private Float grades; //학점
    private Float max_grades; //최대 학점
    private LocalDateTime admission; //입학일
    private LocalDateTime graduated; //졸업일
    private String phone_num;
    private String mail;
    private String isGraduated;
    private Long user_id;

    public ResponseEducationDto(Education education){
        this.education_id = education.getId();
        this.school = education.getSchool();
        this.major = education.getMajor();
        this.grades = education.getGrades();
        this.max_grades = education.getMax_grades();
        this.admission = education.getAdmission();
        this.graduated = education.getGraduated();
        this.phone_num = education.getPhone_num();
        this.mail = education.getMail();
        this.isGraduated = education.getIsGraduated();
        this.user_id = education.getUser().getId();
    }
}
