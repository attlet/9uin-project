package com.inProject.in.domain.CommonLogic.Find.Dto.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFindPwDto {
    private String message;
    private boolean success;
}
