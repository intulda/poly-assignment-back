package intulda.poly.assignment.domain.board.service;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.service.AccountService;
import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.model.BoardRequest;
import intulda.poly.assignment.domain.board.model.BoardResponse;
import intulda.poly.assignment.domain.board.repository.BoardRepository;
import org.springframework.data.domain.Pageable;
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

    public List<Board> findBoardAll(Pageable pageable) {
        return this.boardRepository.findBoardAll(pageable);
    }

    public Long write(Board board) {
        Board saveBoard = this.boardRepository.save(board);
        return saveBoard.getId();
    }

    public Optional<Board> findBoard(Long id) {
        return this.boardRepository.findBoardById(id);
    }

    public BoardResponse findMyBoardAll(Long id, Pageable pageable) {
        List<Board> myBoardAll = boardRepository.findMyBoardAll(id, pageable);
        Account account = accountService.findUser(id).orElseThrow(IllegalAccessError::new);

        return BoardResponse.builder()
                .account(account)
                .boards(myBoardAll)
                .build();
    }

    public Board delete(BoardRequest boardRequest) {
        Board board = findBoard(boardRequest.getBoardId()).orElseThrow(IllegalArgumentException::new);
        board.changeState(boardRequest.getBoardState());
        Board save = this.boardRepository.save(board);
        return save;
    }

    public Board update(BoardRequest boardRequest) {
        Board board = findBoard(boardRequest.getBoardId()).orElseThrow(IllegalArgumentException::new);
        board.changeContents(board.getContents());
        return this.boardRepository.save(board);
    }
}
