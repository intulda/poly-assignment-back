package intulda.poly.assignment.domain.board.service;

import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.model.Pageable;
import intulda.poly.assignment.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> findBoardAll(Long offset) {
        Pageable pageable = new Pageable();
        pageable.setOffset(offset);
        return this.boardRepository.findBoardAll(pageable);
    }

    public Long write(Board board) {
        Board saveBoard = this.boardRepository.save(board);
        return saveBoard.getId();
    }
}
