package com.inProject.in.domain.Profile.Dto.request;

import com.inProject.in.domain.Profile.entity.Project_skill;
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
public class RequestProject_skillDto {

    private String project_name;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String brief_text;
    private String github_link;
    private String skill_in_project;
    private String performance;
    private String role;

    public Project_skill toEntity(User user){
        return Project_skill.builder()
                .project_name(project_name)
                .start_date(start_date)
                .end_date(end_date)
                .brief_text(brief_text)
                .github_link(github_link)
                .skill_in_project(skill_in_project)
                .performance(performance)
                .role(role)
                .user(user)
                .build();
    }
}
