package com.inProject.in.domain.SkillTag.Dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RequestCreateSkillTagDto {
    private List<String> name;
}
