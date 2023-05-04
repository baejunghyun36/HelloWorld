package com.project.helloworld.controller;


import com.project.helloworld.dto.request.*;
import com.project.helloworld.elkStack.domain.BoardDocument;
import com.project.helloworld.service.BoardService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    @PostMapping("")
    ResponseEntity<?> createBoard(@Validated  @RequestBody BoardCreateBody boardCreateBody) throws Exception {

        return ResponseEntity.ok().body(boardService.createBoard(boardCreateBody));
    }

    @GetMapping("")
    ResponseEntity<?> getBoard(@RequestParam Long userSeq, @RequestParam Long boardSeq) throws Exception {

        return ResponseEntity.ok().body(boardService.getBoard(userSeq,boardSeq));
    }

    @GetMapping("/board-list")
    ResponseEntity<?> getBoards(@RequestParam int start,@RequestParam int size) throws Exception {
        return ResponseEntity.ok().body(boardService.getBoards(start,size));
    }
    @PatchMapping("")
    ResponseEntity<?> modifyBoard(@RequestBody BoardModifyBody boardModifyBody) throws Exception {

        return ResponseEntity.ok().body(boardService.modifyBoard(boardModifyBody));
    }

    @DeleteMapping("")
    ResponseEntity<?> removeBoard(@RequestParam Long boardSeq) throws Exception {

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

    @GetMapping("/getTopTen")
    public List<Object> getTopSearchTerms(){
        // 시간 측정 시작
        Set<Object> topSearchTerms = boardService.getTop10KeywordsByRedis();
        return new ArrayList<>(topSearchTerms);
    }

    // 키워드 검색
    @GetMapping("/searchByKeyword")
    public List<BoardDocument> searchBoards(@RequestParam String keyword) {
        System.out.println(keyword);
        return boardService.searchByKeyword(keyword);
    }
}
