package com.inProject.in.domain.Profile.Dto.response;

import com.inProject.in.domain.Profile.entity.Job_ex;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseJob_exDto {
    private Long jobex_id;
    private String company_name;
    private LocalDateTime join_date;  //입사일
    private LocalDateTime leave_date; //퇴사일
    private String job_explanation; //직무 설명
    private String skill_in_job; //직무 경험 시 경험한 기술 스택
    private Long user_id;

    public ResponseJob_exDto(Job_ex jobEx){
        this.jobex_id = jobEx.getId();
        this.company_name = jobEx.getCompany_name();
        this.join_date = jobEx.getJoin_date();
        this.leave_date = jobEx.getLeave_date();
        this.job_explanation = jobEx.getJob_explanation();
        this.skill_in_job = jobEx.getSkill_in_job();
        this.user_id = jobEx.getUser().getId();
    }
}
