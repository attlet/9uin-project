package com.inProject.in.domain.Comment.entity;

import com.inProject.in.Global.BaseEntity;
import com.inProject.in.domain.Board.entity.Board;
import com.inProject.in.domain.Comment.Dto.UpdateCommentDto;
import com.inProject.in.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")       //N : 1
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")       //N : 1
    private Board board;

    @Column
    private boolean deleted = Boolean.FALSE;
    public void updateComment(UpdateCommentDto updateCommentDto){
        this.text = updateCommentDto.getText();
    }
}
