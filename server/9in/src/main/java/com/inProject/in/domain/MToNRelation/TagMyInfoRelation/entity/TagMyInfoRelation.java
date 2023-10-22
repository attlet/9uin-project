package com.inProject.in.domain.MToNRelation.TagMyInfoRelation.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.inProject.in.Global.BaseEntity;
import com.inProject.in.domain.Profile.entity.MyInfo;
import com.inProject.in.domain.SkillTag.entity.SkillTag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tagMyInfoRelation")
public class TagMyInfoRelation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne
    @JoinColumn(name = "myinfo_id")
    private MyInfo myInfo;

    @ManyToOne
    @JoinColumn(name = "skillTag_id")
    private SkillTag skillTag;
}
