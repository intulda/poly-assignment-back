package intulda.poly.assignment.domain.board.model;

import intulda.poly.assignment.domain.account.model.Account;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponse {

    private Long account_id;

    private String account;

    private String account_name;

    private List<Board> boards;

    private HashSet<Board> board;

    @Builder(builderMethodName = "boardAll")
    public BoardResponse(Account account, List<Board> boards) {
        this.account_id = account.getId();
        this.account = account.getAccount();
        this.account_name = account.getName();
        this.boards = boards;
    }
}
