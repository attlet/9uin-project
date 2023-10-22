package com.inProject.in.domain.Profile.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.inProject.in.Global.BaseEntity;
import com.inProject.in.domain.MToNRelation.TagMyInfoRelation.entity.TagMyInfoRelation;
import com.inProject.in.domain.Profile.Dto.request.RequestMyInfoDto;
import com.inProject.in.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@DynamicUpdate
public class MyInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname; //닉네임
    private String role;    //역할
    private String career;   //경력
    private String school; //학교
    private String major;  //전공
    private String graduated;  //졸업여부

    //기술 태그는 다대다관계를 설계해야 할 것 같음
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "myInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TagMyInfoRelation> tagMyInfoRelationList;

    public void updateMyInfo(RequestMyInfoDto requestMyInfoDto){
        this.nickname = requestMyInfoDto.getNickname();
        this.role = requestMyInfoDto.getRole();
        this.career = requestMyInfoDto.getCareer();
        this.school = requestMyInfoDto.getSchool();
        this.major = requestMyInfoDto.getMajor();
        this.graduated = requestMyInfoDto.getGraduated();
    }

}
