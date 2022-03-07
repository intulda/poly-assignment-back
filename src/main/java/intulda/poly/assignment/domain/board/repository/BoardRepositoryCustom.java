package intulda.poly.assignment.domain.board.repository;

import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.model.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepositoryCustom {

    List<Board> findBoardAll(Pageable pageable);
}
