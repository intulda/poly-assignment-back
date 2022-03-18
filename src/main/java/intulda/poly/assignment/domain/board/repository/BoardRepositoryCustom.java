package intulda.poly.assignment.domain.board.repository;

import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.model.BoardRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepositoryCustom {

    List<Board> findMyBoardAll(Long id, Pageable pageable);

    List<Board> findBoardAll(Pageable pageable, String type, String keyword);

    Optional<Board> findBoardById(Long id);

    Optional<Board> findMyBoard(Long id, Long accountId);
}
