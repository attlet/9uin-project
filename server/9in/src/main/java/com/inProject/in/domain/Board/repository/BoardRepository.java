package com.inProject.in.domain.Board.repository;

import com.inProject.in.domain.Board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {
    @Modifying
    @Query("UPDATE Board b SET b.view_cnt = b.view_cnt + 1 WHERE b.id = :id")
    int updateViewCnt(@Param("id") Long id);

}
