package com.inProject.in.domain.Notify.entity;

import com.inProject.in.Global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notify extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String alarm_type;
    @Column(nullable = false)
    private String board_name;
    @Column(nullable = false)
    private boolean isChecked;   //알림 읽었는지 여부.
}
