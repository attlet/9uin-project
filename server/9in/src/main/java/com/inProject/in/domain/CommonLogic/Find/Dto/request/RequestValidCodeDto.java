package com.inProject.in.domain.CommonLogic.Find.Dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestValidCodeDto {
    private String mail;
    private String validCode;
}
