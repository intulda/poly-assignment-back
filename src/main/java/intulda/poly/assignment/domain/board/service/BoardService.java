package intulda.poly.assignment.domain.board.service;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.service.AccountService;
import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.model.BoardOneResponse;
import intulda.poly.assignment.domain.board.model.BoardRequest;
import intulda.poly.assignment.domain.board.model.BoardResponse;
import intulda.poly.assignment.domain.board.repository.BoardRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final AccountService accountService;

    private final BoardRepository boardRepository;

    public BoardService(AccountService accountService, BoardRepository boardRepository) {
        this.accountService = accountService;
        this.boardRepository = boardRepository;
    }

    public List<Board> findBoardAll(Pageable pageable, String type, String keyword) {
        return this.boardRepository.findBoardAll(pageable, type, keyword);
    }

    public Long write(Board board) {
        Board saveBoard = this.boardRepository.save(board);
        return saveBoard.getId();
    }

    public BoardOneResponse findBoard(Long id) {
        Board boardById = this.boardRepository.findBoardById(id).orElseThrow(IllegalArgumentException::new);
        Account account = accountService.findUser(boardById.getAccount().getId()).orElseThrow(IllegalArgumentException::new);
        boardById.addAccount(account);
        return BoardOneResponse.boardOne()
                .board(boardById)
                .build();
    }

    public BoardResponse findMyBoardAll(Long id, Pageable pageable) {
        List<Board> myBoardAll = boardRepository.findMyBoardAll(id, pageable);
        Account account = accountService.findUser(id).orElseThrow(IllegalAccessError::new);

        return BoardResponse.boardAll()
                .account(account)
                .boards(myBoardAll)
                .build();
    }

    public Optional<Board> findMyBoard(Long id, Long accountId) {
        return this.boardRepository.findMyBoard(id ,accountId);
    }

    public Long delete(BoardRequest boardRequest) {
        Board board = findMyBoard(boardRequest.getBoardId(), boardRequest.getAccountId()).orElseThrow(IllegalArgumentException::new);
        board.changeState(boardRequest.getBoardState());
        Board save = this.boardRepository.save(board);
        return save.getId();
    }

    public Board update(BoardRequest boardRequest) {
        Board board = findMyBoard(boardRequest.getBoardId(), boardRequest.getAccountId()).orElseThrow(IllegalArgumentException::new);
        board.changeBoard(boardRequest.getBoardTitle(), boardRequest.getBoardContents());
        return this.boardRepository.save(board);
    }
}
