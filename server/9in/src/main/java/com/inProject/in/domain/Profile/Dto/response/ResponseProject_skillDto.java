package com.inProject.in.domain.Profile.Dto.response;

import com.inProject.in.domain.Profile.entity.Project_skill;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseProject_skillDto {
    private Long projectSkill_id;
    private String project_name;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String brief_text;
    private String github_link;
    private String skill_in_project;
    private String performance;
    private String role;

    public ResponseProject_skillDto(Project_skill projectSkill){
        this.projectSkill_id = projectSkill.getId();
        this.project_name = projectSkill.getProject_name();
        this.start_date = projectSkill.getStart_date();
        this.end_date = projectSkill.getEnd_date();
        this.brief_text = projectSkill.getBrief_text();
        this.github_link = projectSkill.getGithub_link();
        this.skill_in_project = projectSkill.getSkill_in_project();
        this.performance = projectSkill.getPerformance();
        this.role = projectSkill.getRole();
    }
}
