package com.example.jwt.web.borad;

import com.example.jwt.service.board.BoardSearchRequest;
import com.example.jwt.service.board.BoardService;
import com.example.jwt.service.board.BoardVO;
import com.example.jwt.service.menu.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<Void> save(@RequestBody BoardVO boardVO) {
        boardService.create(boardVO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getList")
    public ResponseEntity<List<BoardVO>> getList(BoardSearchRequest request) {
        return ResponseEntity.ok(boardService.getList(request));
    }

    @GetMapping("/get/{uid}")
    public ResponseEntity<BoardVO> get(@PathVariable String uid) {
        return ResponseEntity.ok(boardService.get(uid));
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@RequestBody BoardVO boardVO) {
        boardService.edit(boardVO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uid}")
    public ResponseEntity<Void> edit(@PathVariable String uid) {
        boardService.delete(uid);
        return ResponseEntity.ok().build();
    }
}
