package com.inProject.in.domain.User.Dto;

import com.inProject.in.domain.Board.entity.Board;
import com.inProject.in.domain.Comment.entity.Comment;
import com.inProject.in.domain.MToNRelation.ApplicantBoardRelation.entity.ApplicantBoardRelation;
import com.inProject.in.domain.MToNRelation.ApplicantRoleRelation.entity.ApplicantRoleRelation;
import com.inProject.in.domain.MToNRelation.ClipBoardRelation.entity.ClipBoardRelation;
import com.inProject.in.domain.User.entity.User;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseUserDto {

    private Long user_id;
    private String username;
    private String mail;


    public ResponseUserDto(User user){
        this.user_id = user.getId();
        this.username = user.getUsername();
        this.mail = user.getMail();
    }
}
