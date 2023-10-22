package com.inProject.in.domain.Profile.repository;

import com.inProject.in.domain.Profile.entity.Job_ex;
import com.inProject.in.domain.Profile.entity.MyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyInfoRepository extends JpaRepository<MyInfo, Long> {
    @Query(value = "SELECT s FROM MyInfo AS s WHERE s.user.id = :user_id")
    Optional<MyInfo>findMyInfoByUserId(@Param("user_id") Long user_id);
}
