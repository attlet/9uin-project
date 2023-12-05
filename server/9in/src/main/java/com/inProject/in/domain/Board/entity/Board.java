package com.inProject.in.domain.Board.entity;

import com.inProject.in.Global.BaseEntity;
import com.inProject.in.domain.Board.Dto.request.RequestUpdateBoardDto;
import com.inProject.in.domain.Comment.entity.Comment;
import com.inProject.in.domain.MToNRelation.ApplicantBoardRelation.entity.ApplicantBoardRelation;
import com.inProject.in.domain.MToNRelation.ClipBoardRelation.entity.ClipBoardRelation;
import com.inProject.in.domain.MToNRelation.RoleBoardRelation.entity.RoleBoardRelation;
import com.inProject.in.domain.MToNRelation.TagBoardRelation.entity.TagBoardRelation;
import com.inProject.in.domain.User.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board") //테이블과 매핑
@SQLDelete(sql = "UPDATE board SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String proceed_method; //진행 방식
    @Column(nullable = false)
    private LocalDateTime period; //예상 기간
    @Column(nullable = false)
    @Builder.Default
    private int view_cnt = 0;   //게시글 조회수
    @Column(nullable = false)
    @Builder.Default
    private int comment_cnt = 0;  //댓글 개수
    @Column
    private boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;               //작성자 정보 접근

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @Builder.Default
    private List<ApplicantBoardRelation> applicantBoardRelationList = new ArrayList<>();     //게시글에 지원서를 제출한 유저에 대한 정보

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> commentList = new ArrayList<>();    //게시글에 작성된 댓글들

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @Builder.Default
    private List<TagBoardRelation> tagBoardRelationList = new ArrayList<>();   //태그

    @OneToMany(mappedBy = "clipedBoard", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @Builder.Default
    private List<ClipBoardRelation> clipBoardRelationList = new ArrayList<>(); //관심 클립으로 지정한 유저들

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @Builder.Default
    private List<RoleBoardRelation> roleBoardRelationList = new ArrayList<>();   //직군

    public void updateBoard(RequestUpdateBoardDto requestUpdateBoardDto){
        this.type = requestUpdateBoardDto.getType();
        this.title = requestUpdateBoardDto.getTitle();
        this.text = requestUpdateBoardDto.getText();
        this.proceed_method = requestUpdateBoardDto.getProceed_method();
        this.period = requestUpdateBoardDto.getPeriod();

//        this.roleBoardRelationList.clear();
//        this.tagBoardRelationList.clear();


//        this.tagBoardRelationList = requestBoardDto.getTagBoardRelationList();
//        this.roleBoardRelationList = requestBoardDto.getRoleBoardRelationList();
    }
}
