package intulda.poly.assignment.domain.board.controller;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.service.AccountService;
import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.service.BoardService;
import intulda.poly.assignment.global.configuration.jwt.provider.JwtTokenProvider;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/*")
public class BoardController {

    private final BoardService boardService;

    private final AccountService accountService;

    private final JwtTokenProvider jwtTokenProvider;

    public BoardController(BoardService boardService, AccountService accountService, JwtTokenProvider jwtTokenProvider) {
        this.boardService = boardService;
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(value = "boards")
    public ResponseEntity<List<Board>> findBoardAll(@Param("offset") Long offset) {
        return new ResponseEntity<>(this.boardService.findBoardAll(offset), HttpStatus.OK);
    }

    @PostMapping(value = "board")
    public ResponseEntity<Long> writeBoard(@RequestBody Board board, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long userId = Long.parseLong(jwtTokenProvider.getUsernameFromToken(token));
        Account account = accountService.findUser(userId).orElseThrow(IllegalArgumentException::new);
        board.addAccount(account);
        Long boardId = this.boardService.write(board);
        return new ResponseEntity<>(boardId, HttpStatus.OK);
    }
}

