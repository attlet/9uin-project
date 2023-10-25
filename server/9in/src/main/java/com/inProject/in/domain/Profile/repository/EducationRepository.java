package com.inProject.in.domain.Profile.repository;

import com.inProject.in.domain.Profile.entity.Certificate;
import com.inProject.in.domain.Profile.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Long> {
    @Query(value = "SELECT s FROM Education AS s WHERE s.user.id = :user_id")
    Optional<Education> findEducationByUserId(@Param("user_id") Long user_id);
}
