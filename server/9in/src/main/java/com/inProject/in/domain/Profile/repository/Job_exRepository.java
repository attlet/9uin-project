package com.inProject.in.domain.Profile.repository;

import com.inProject.in.domain.Profile.entity.Certificate;
import com.inProject.in.domain.Profile.entity.Job_ex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Job_exRepository extends JpaRepository<Job_ex, Long> {
    @Query(value = "SELECT s FROM Job_ex AS s WHERE s.user.id = :user_id")
    Optional<List<Job_ex>> findJob_exByUserId(@Param("user_id") Long user_id);
}
