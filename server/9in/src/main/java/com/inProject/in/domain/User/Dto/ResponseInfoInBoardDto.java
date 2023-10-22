package com.inProject.in.domain.User.Dto;

import com.inProject.in.domain.MToNRelation.ApplicantBoardRelation.entity.ApplicantBoardRelation;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseInfoInBoardDto {
    private String nickname;
    private String role;
    private String career;
    private boolean isAuthor;   //작성자인지 확인.
    public ResponseInfoInBoardDto(ApplicantBoardRelation applicantBoardRelation){
        if(applicantBoardRelation.getBoard_applicant().getMyInfo() == null){
            this.nickname = applicantBoardRelation.getBoard_applicant().getUsername();       //닉네임을 생성하지 않음.
            this.role = "";
            this.career = "";
        }
        else {
            this.nickname = applicantBoardRelation.getBoard_applicant().getMyInfo().getNickname();
            this.role = applicantBoardRelation.getBoard_applicant().getMyInfo().getRole();
            this.career = applicantBoardRelation.getBoard_applicant().getMyInfo().getCareer();
        }

    }
}
