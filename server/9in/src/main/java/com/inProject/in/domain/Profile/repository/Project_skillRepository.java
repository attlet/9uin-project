package com.inProject.in.domain.Profile.repository;

import com.inProject.in.domain.Profile.entity.Certificate;
import com.inProject.in.domain.Profile.entity.Project_skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Project_skillRepository extends JpaRepository<Project_skill, Long> {
    @Query(value = "SELECT s FROM Project_skill AS s WHERE s.user.id = :user_id")
    Optional<List<Project_skill>> findProject_skillByUserId(@Param("user_id") Long user_id);
}
