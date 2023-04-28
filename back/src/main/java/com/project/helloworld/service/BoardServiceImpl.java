package com.project.helloworld.service;

import com.project.helloworld.domain.Board;
import com.project.helloworld.domain.Comment;
import com.project.helloworld.domain.Sticker;
import com.project.helloworld.domain.User;
import com.project.helloworld.dto.MessageResponse;
import com.project.helloworld.dto.request.*;
import com.project.helloworld.repository.BoardRepository;
import com.project.helloworld.repository.CommentRepository;
import com.project.helloworld.repository.StickerRepository;
import com.project.helloworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private  final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final StickerRepository stickerRepository;
    @Override
    public ResponseEntity<?> createBoard(BoardCreateBody boardCreateBody) throws Exception {
        User user = userRepository.findById(boardCreateBody.getUserSeq()).orElseThrow(()-> new Exception("not exist user : "+boardCreateBody.getUserSeq()));
        Board board = Board.builder().title(boardCreateBody.getTitle()).content(boardCreateBody.getContent()).
                imgUrl("").likeCnt(0).helpfulCnt(0).understandCnt(0)
                .user(user).build();
        boardRepository.save(board);
        MessageResponse messageResponse = MessageResponse.builder().message("게시글 작성 성공하였습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> getBoard() {
        // 검색 조건에 따라 보내야 할 것 같은데....

        return ResponseEntity.ok().body(null);
    }

    @Override
    public ResponseEntity<?> modifyBoard(BoardModifyBody boardModifyBody) throws Exception {
        Board board = boardRepository.findById(boardModifyBody.getBoardSeq()).orElseThrow(()-> new Exception("not exist board : "+boardModifyBody.getBoardSeq()));
        Board newBoard = Board.builder().boardSeq(board.getBoardSeq()).title(boardModifyBody.getTitle()).content(boardModifyBody.getContent())
                .imgUrl(board.getImgUrl()).likeCnt(board.getLikeCnt()).helpfulCnt(board.getHelpfulCnt())
                .understandCnt(board.getUnderstandCnt()).user(board.getUser()).build();
        boardRepository.save(newBoard);
        MessageResponse messageResponse = MessageResponse.builder().message("게시글 수정 성공하였습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> removeBoard(Long boardSeq) throws Exception {
        boardRepository.deleteById(boardSeq);
        MessageResponse messageResponse = MessageResponse.builder().message("게시글 삭제 되었습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> createComment(CommentCreateBody commentCreateBody) throws Exception {
        Board board = boardRepository.findById(commentCreateBody.getBoardSeq()).orElseThrow(()-> new Exception("not exist board : "+commentCreateBody.getBoardSeq()));
        Comment comment = Comment.builder().userSeq(commentCreateBody.getUserSeq()).content(commentCreateBody.getContent()).board(board).build();
        commentRepository.save(comment);

        MessageResponse messageResponse = MessageResponse.builder().message("댓글 등록 되었습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> modifyComment(CommentModifyBody commentModifyBody) throws Exception {
        Comment comment = commentRepository.findById(commentModifyBody.getCommentSeq()).orElseThrow(() -> new Exception("not exist comment : "+commentModifyBody.getCommentSeq()));
        Comment newComment = Comment.builder().commentSeq(comment.getCommentSeq()).userSeq(comment.getUserSeq())
                .content(commentModifyBody.getContent()).date(comment.getDate()).board(comment.getBoard()).build();
        commentRepository.save(newComment);
        MessageResponse messageResponse = MessageResponse.builder().message("댓글 수정 되었습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> removeComment(Long commentSeq) throws Exception {
        commentRepository.deleteById(commentSeq);
        MessageResponse messageResponse = MessageResponse.builder().message("댓글 삭제 되었습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> createSticker(StickerCreateBody stickerCreateBody) throws Exception {
        User user = userRepository.findById(stickerCreateBody.getUserSeq()).orElseThrow(()-> new Exception("not exist user : "+stickerCreateBody.getUserSeq()));
        Board board = boardRepository.findById(stickerCreateBody.getBoardSeq()).orElseThrow(()-> new Exception("not exist board : "+stickerCreateBody.getBoardSeq()));
        Sticker sticker = Sticker.builder().user(user).board(board).type(stickerCreateBody.getType()).build();
        stickerRepository.save(sticker);
        MessageResponse messageResponse = MessageResponse.builder().message("반응이 추가되었습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> removeSticker(Long stickerSeq) throws Exception {

        stickerRepository.deleteById(stickerSeq);
        MessageResponse messageResponse = MessageResponse.builder().message("반응이 삭제되었습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }


}
