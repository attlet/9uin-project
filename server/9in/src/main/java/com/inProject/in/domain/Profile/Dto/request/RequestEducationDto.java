package com.inProject.in.domain.Profile.Dto.request;

import com.inProject.in.domain.Profile.entity.Education;
import com.inProject.in.domain.User.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RequestEducationDto {

    private String school; //대학교
    private String major;  //전공
    private Float grades; //학점
    private Float max_grades; //최대 학점
    private LocalDateTime admission; //입학일
    private LocalDateTime graduated; //졸업일
    private String phone_num;  //연락처
    private String mail;
    private String isGraduated;

    public Education toEntity(User user){
        return Education.builder()
                .school(school)
                .major(major)
                .grades(grades)
                .max_grades(max_grades)
                .admission(admission)
                .graduated(graduated)
                .phone_num(phone_num)
                .mail(mail)
                .isGraduated(isGraduated)
                .user(user)
                .build();
    }
}
