package com.inProject.in.domain.MToNRelation.TagMyInfoRelation.repository;

import com.inProject.in.domain.MToNRelation.TagMyInfoRelation.entity.TagMyInfoRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMyInfoRelationRepository extends JpaRepository<TagMyInfoRelation, Long> {
}
