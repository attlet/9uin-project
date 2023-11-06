package com.inProject.in.domain.SkillTag.Dto;

import com.inProject.in.domain.MToNRelation.TagBoardRelation.entity.TagBoardRelation;
import com.inProject.in.domain.SkillTag.entity.SkillTag;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseSkillTagDto {
    private Long skillTag_id;
    private String name;
    public ResponseSkillTagDto(SkillTag SkillTag){
        this.name = SkillTag.getName();
    }
}
