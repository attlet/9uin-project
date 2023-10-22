package com.inProject.in.domain.Profile.entity;

import com.inProject.in.Global.BaseEntity;
import com.inProject.in.domain.Profile.Dto.request.RequestEducationDto;
import com.inProject.in.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@DynamicUpdate
@Table(name = "education")
public class Education extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String school; //대학교
    @Column
    private String major;  //전공
    @Column
    private Float grades; //학점
    @Column
    private Float max_grades; //최대 학점
    @Column
    private LocalDateTime admission; //입학일
    @Column
    private LocalDateTime graduated; //졸업일
    @Column
    private String phone_num;  //연락처
    @Column
    private String mail; //메일
    @Column
    private String isGraduated; //졸업 여부
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    public void updateEducation(RequestEducationDto requestEducationDto){
        this.school = requestEducationDto.getSchool();
        this.major = requestEducationDto.getMajor();
        this.grades = requestEducationDto.getGrades();
        this.max_grades = requestEducationDto.getMax_grades();
        this.admission = requestEducationDto.getAdmission();
        this.graduated = requestEducationDto.getGraduated();
        this.phone_num = requestEducationDto.getMajor();
        this.mail = requestEducationDto.getMajor();
    }
}
