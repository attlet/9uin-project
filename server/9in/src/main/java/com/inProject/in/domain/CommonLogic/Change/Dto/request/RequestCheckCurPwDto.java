package com.inProject.in.domain.CommonLogic.Change.Dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCheckCurPwDto {
    private String username;
    private String cur_pw;
}
