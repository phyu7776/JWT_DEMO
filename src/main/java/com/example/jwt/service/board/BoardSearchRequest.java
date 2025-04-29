package com.example.jwt.service.board;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardSearchRequest {

    private Integer page = 0;
    private Integer limit = 20;
    private String search;
}
