package com.inProject.in.domain.User.repository;

import com.inProject.in.domain.Board.repository.CustomBoardRepository;
import com.inProject.in.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
    Optional<User> getByUsername(String username);
    Optional<User> getByMail(String mail);
}
