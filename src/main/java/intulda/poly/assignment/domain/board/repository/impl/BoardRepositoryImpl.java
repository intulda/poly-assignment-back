package intulda.poly.assignment.domain.board.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import intulda.poly.assignment.domain.board.model.Board;
import intulda.poly.assignment.domain.board.model.Pageable;
import intulda.poly.assignment.domain.board.model.QBoard;
import intulda.poly.assignment.domain.board.repository.BoardRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Board> findBoardAll(Pageable pageable) {
        return jpaQueryFactory
                .select(QBoard.board)
                .from(QBoard.board)
                .offset(1)
                .limit(10)
                .fetch();
    }
}
