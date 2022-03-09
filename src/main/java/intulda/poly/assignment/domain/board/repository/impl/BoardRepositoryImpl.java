package intulda.poly.assignment.domain.board.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.model.BoardState;
import intulda.poly.assignment.domain.board.model.QBoard;
import intulda.poly.assignment.domain.board.repository.BoardRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Board> findMyBoardAll(Long id, Pageable pageable) {
        QBoard board = QBoard.board;

        List<Board> fetch = jpaQueryFactory
                .selectFrom(board)
                .where(board.account.id.eq(id).and(board.boardState.eq(BoardState.STABLE)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return fetch;
    }

    @Override
    public List<Board> findBoardAll(Pageable pageable) {
        QBoard board = QBoard.board;

        List<Board> fetch = jpaQueryFactory
                .selectFrom(board)
                .where(board.boardState.eq(BoardState.STABLE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return fetch;
    }

    @Override
    public Optional<Board> findBoardById(Long id) {
        QBoard board = QBoard.board;

        return Optional.ofNullable(jpaQueryFactory.selectFrom(board)
                .where(
                        board.boardState.eq(BoardState.STABLE),
                        board.id.eq(id)
                ).fetchOne());

    }
}
