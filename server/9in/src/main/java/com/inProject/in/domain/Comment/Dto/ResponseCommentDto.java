package com.inProject.in.domain.Comment.Dto;

import com.inProject.in.domain.Comment.entity.Comment;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseCommentDto {
    private Long comment_id;
    private Long user_id;
    private String username;
    private Long board_id;
    private String text;

    public ResponseCommentDto(Comment comment){
        this.comment_id = comment.getId();
        this.user_id = comment.getUser().getId();
        this.username = comment.getUsername();
        this.board_id = comment.getBoard().getId();
        this.text = comment.getText();
    }
}
