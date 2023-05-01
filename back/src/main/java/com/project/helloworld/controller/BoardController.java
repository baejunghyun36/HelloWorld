package com.project.helloworld.controller;


import com.project.helloworld.dto.request.*;
import com.project.helloworld.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    @PostMapping("")
    ResponseEntity<?> createBoard(@RequestBody BoardCreateBody boardCreateBody) throws Exception {

        return ResponseEntity.ok().body(boardService.createBoard(boardCreateBody));
    }

    @GetMapping("")
    ResponseEntity<?> getBoard(@RequestParam Long userSeq, @RequestParam Long boardSeq) throws Exception {

        return ResponseEntity.ok().body(boardService.getBoard(userSeq,boardSeq));
    }

    @PatchMapping("")
    ResponseEntity<?> modifyBoard(@RequestBody BoardModifyBody boardModifyBody) throws Exception {

        return ResponseEntity.ok().body(boardService.modifyBoard(boardModifyBody));
    }

    @DeleteMapping("")
    ResponseEntity<?> removeBoard(Long boardSeq) throws Exception {

        return ResponseEntity.ok().body(boardService.removeBoard(boardSeq));
    }

    @PostMapping("/comment")
    ResponseEntity<?> createComment(@RequestBody CommentCreateBody commentCreateBody) throws Exception {

        return ResponseEntity.ok().body(boardService.createComment(commentCreateBody));
    }

    @PutMapping("/comment")
    ResponseEntity<?> modifyComment(@RequestBody CommentModifyBody commentModifyBody) throws Exception {
        return ResponseEntity.ok().body(boardService.modifyComment(commentModifyBody));
    }

    @DeleteMapping("/comment")
    ResponseEntity<?> removeComment(@RequestParam("commentSeq") Long commentSeq) throws Exception {
        return ResponseEntity.ok().body(boardService.removeComment(commentSeq));
    }

    @PostMapping("/sticker")
    ResponseEntity<?> createSticker(@RequestBody StickerCreateBody stickerCreateBody) throws Exception {
        return ResponseEntity.ok().body(boardService.createSticker(stickerCreateBody));
    }

    @DeleteMapping("/sticker")
    ResponseEntity<?> deleteSticker(@RequestParam("stickerSeq") Long stickerSeq) throws Exception {
        return ResponseEntity.ok().body(boardService.removeSticker(stickerSeq));
    }


}
