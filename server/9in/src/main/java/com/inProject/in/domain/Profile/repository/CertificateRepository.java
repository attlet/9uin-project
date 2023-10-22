package com.inProject.in.domain.Profile.repository;

import com.inProject.in.domain.Profile.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    @Query("SELECT s FROM Certificate AS s WHERE s.user.id = :user_id")
    Optional<List<Certificate>> findCertificateByUserId(@Param("user_id") Long user_id);
}
