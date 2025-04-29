package com.example.jwt.service.board;

import java.util.List;

public interface BoardService {

    void create(BoardVO board);

    List<BoardVO> getList(BoardSearchRequest request);

    BoardVO get(String uid);

    void edit(BoardVO board);

    void delete(String uid);
}
