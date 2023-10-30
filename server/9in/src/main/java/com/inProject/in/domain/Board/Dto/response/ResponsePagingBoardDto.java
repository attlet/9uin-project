package com.inProject.in.domain.Board.Dto.response;

import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePagingBoardDto {
    private List<ResponseBoardListDto> content;
    private long totalCount;
    private int totalPage;
}
