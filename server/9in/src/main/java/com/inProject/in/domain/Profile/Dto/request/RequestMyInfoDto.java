package com.inProject.in.domain.Profile.Dto.request;

import com.inProject.in.domain.Profile.entity.MyInfo;
import com.inProject.in.domain.SkillTag.Dto.RequestSkillTagDto;
import com.inProject.in.domain.User.entity.User;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestMyInfoDto {
    private String nickname;
    private String role;
    private String career;
    private String school;
    private String major;
    private String graduated;
    private Long user_id;
    private List<RequestSkillTagDto> requestSkillTagDtoList;
    public MyInfo toEntity(User user){
        return MyInfo.builder()
                .nickname(nickname)
                .role(role)
                .career(career)
                .school(school)
                .major(major)
                .graduated(graduated)
                .user(user)
                .build();
    }
}
