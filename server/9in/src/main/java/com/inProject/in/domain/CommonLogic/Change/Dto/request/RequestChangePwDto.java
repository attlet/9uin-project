package com.inProject.in.domain.CommonLogic.Change.Dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestChangePwDto {
    private String username;
    private String curPw;
    private String newPw;
    private String checkPw;
}
