package com.inProject.in.domain.SkillTag.entity;

import com.inProject.in.Global.BaseEntity;
import com.inProject.in.domain.MToNRelation.TagBoardRelation.entity.TagBoardRelation;
import com.inProject.in.domain.MToNRelation.TagMyInfoRelation.entity.TagMyInfoRelation;
import com.inProject.in.domain.SkillTag.Dto.RequestSkillTagDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "skilltag")
public class SkillTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "skillTag")
    @ToString.Exclude
    private List<TagBoardRelation> tagBoardRelationList;

    @OneToMany(mappedBy = "skillTag", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TagMyInfoRelation> tagMyInfoRelationList;

    public void updateSkillTag(RequestSkillTagDto requestSkillTagDto){
        this.name = requestSkillTagDto.getName();
    }
}
