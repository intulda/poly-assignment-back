package intulda.poly.assignment.domain.board.model;

import intulda.poly.assignment.domain.account.model.Account;
import lombok.*;

import java.util.HashSet;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardOneResponse {

    private Long account_id;

    private String account;

    private String account_name;

    private List<Board> boards;

    private HashSet<Board> board;

    @Builder(builderMethodName = "boardOne")
    public BoardOneResponse(Board board) {
        this.account_id = board.getAccount().getId();
        this.account = board.getAccount().getAccount();
        this.account_name = board.getAccount().getName();
        HashSet<Board> boardSet = new HashSet();
        boardSet.add(board);

        this.board = boardSet;
    }
}
