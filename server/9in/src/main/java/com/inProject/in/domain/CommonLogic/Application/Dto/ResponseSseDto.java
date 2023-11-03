package com.inProject.in.domain.CommonLogic.Application.Dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ResponseSseDto {
    String title;
    Long role;
    String user_name;
}
