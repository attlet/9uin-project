package com.inProject.in.domain.RoleNeeded.entity;


import com.inProject.in.Global.BaseEntity;
import com.inProject.in.domain.MToNRelation.ApplicantRoleRelation.entity.ApplicantRoleRelation;
import com.inProject.in.domain.MToNRelation.RoleBoardRelation.entity.RoleBoardRelation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "roleNeeded")
public class RoleNeeded extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "roleNeeded")
    private List<RoleBoardRelation> roleBoardRelationList;              //여러 게시글과 연관

    @OneToMany(mappedBy = "roleNeeded")
    private List<ApplicantRoleRelation> applicantRoleRelationList;   //여러 지원자들과 연관

    public void updateRoleNeeded(RoleNeeded roleNeeded){
        this.name = roleNeeded.getName();
    }
}
