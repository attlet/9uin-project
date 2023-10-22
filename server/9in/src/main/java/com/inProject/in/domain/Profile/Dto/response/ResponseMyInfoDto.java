package com.inProject.in.domain.Profile.Dto.response;

import com.inProject.in.domain.Profile.entity.MyInfo;
import com.inProject.in.domain.SkillTag.Dto.ResponseSkillTagDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMyInfoDto {
    private Long myinfo_id;
    private String nickname;
    private String role;
    private String career;
    private String school;
    private String major;
    private String graduated;
    private List<ResponseSkillTagDto> responseSkillTagDtoList = new ArrayList<>();

    public ResponseMyInfoDto(MyInfo myInfo){
        this.myinfo_id = myInfo.getId();
        this.nickname = myInfo.getNickname();
        this.role = myInfo.getRole();
        this.career = myInfo.getCareer();
        this.school = myInfo.getSchool();
        this.major = myInfo.getMajor();
        this.graduated = myInfo.getGraduated();
    }
}
