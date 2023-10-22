package com.inProject.in.domain.Notify.repository;

import com.inProject.in.domain.Notify.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notify, Long> {
}
