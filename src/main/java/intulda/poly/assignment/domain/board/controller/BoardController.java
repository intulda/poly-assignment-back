package intulda.poly.assignment.domain.board.controller;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.service.AccountService;
import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.model.BoardOneResponse;
import intulda.poly.assignment.domain.board.model.BoardRequest;
import intulda.poly.assignment.domain.board.model.BoardResponse;
import intulda.poly.assignment.domain.board.service.BoardService;
import intulda.poly.assignment.global.configuration.jwt.provider.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/**")
public class BoardController {

    private final BoardService boardService;

    private final AccountService accountService;

    private final JwtTokenProvider jwtTokenProvider;

    public BoardController(BoardService boardService, AccountService accountService, JwtTokenProvider jwtTokenProvider) {
        this.boardService = boardService;
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation(value = "find board all", notes = "게시물 전체 조회")
    @GetMapping(value = "boards")
    public ResponseEntity<List<Board>> findBoardAll(@Param("pageNumber") int pageNumber,@Param("keyword") String keyword,@Param("type") String type) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 100);
        return new ResponseEntity<>(this.boardService.findBoardAll(pageRequest, type, keyword), HttpStatus.OK);
    }

    @ApiOperation(value = "write board", notes = "게시글 등록")
    @PostMapping(value = "board")
    public ResponseEntity<Long> writeBoard(@RequestBody BoardRequest boardRequest, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long userId = Long.parseLong(jwtTokenProvider.getUsernameFromToken(token));
        Account account = accountService.findUser(userId).orElseThrow(IllegalArgumentException::new);

        Board board = Board.builder()
                .boardRequest(boardRequest)
                .build();

        board.addAccount(account);
        Long boardId = this.boardService.write(board);
        return new ResponseEntity<>(boardId, HttpStatus.OK);
    }

    @ApiOperation(value = "find board select", notes = "해당 게시글 찾기")
    @GetMapping(value = "board/{id}")
    public ResponseEntity<BoardOneResponse> findBoard(@PathVariable("id") String id, HttpServletRequest request) {
        BoardOneResponse board = this.boardService.findBoard(Long.parseLong(id));
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @ApiOperation(value = "find my board", notes = "내 게시글 찾기")
    @GetMapping(value = "board/me")
    public ResponseEntity<BoardResponse> findMyBoardAll(final HttpServletRequest request) {
        String token = getHeaderAuthorization(request);
        Long userId = Long.parseLong(jwtTokenProvider.getUsernameFromToken(token));
        PageRequest of = PageRequest.of(0, 5);
        BoardResponse myBoardAll = this.boardService.findMyBoardAll(userId, of);
        return new ResponseEntity<>(myBoardAll, HttpStatus.OK);
    }

    @ApiOperation(value = "board delete", notes = "게시물 삭제")
    @DeleteMapping(value = "board")
    public ResponseEntity<Long> deleteMyBoard(@RequestBody BoardRequest boardRequest, HttpServletRequest request) {
        String token = getHeaderAuthorization(request);
        if (token == null) {
            throw new IllegalArgumentException();
        }
        Long accountId = Long.parseLong(jwtTokenProvider.getUsernameFromToken(token));
        boardRequest.setAccountId(accountId);
        Long delete = this.boardService.delete(boardRequest);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @ApiOperation(value = "board update", notes = "게시물 수정")
    @PutMapping(value = "board")
    public ResponseEntity<Board> updateContents(@RequestBody BoardRequest boardRequest, HttpServletRequest request) {
        String token = getHeaderAuthorization(request);
        if (token == null) {
            throw new IllegalArgumentException();
        }
        Long accountId = Long.parseLong(jwtTokenProvider.getUsernameFromToken(token));
        boardRequest.setAccountId(accountId);
        Board updateBoard = this.boardService.update(boardRequest);
        return new ResponseEntity<>(updateBoard, HttpStatus.OK);
    }

    public String getHeaderAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}

