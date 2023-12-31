package com.inProject.in.domain.CommonLogic.Sign.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRefreshDto {
    private String accessToken;
    private String refreshToken;
}
