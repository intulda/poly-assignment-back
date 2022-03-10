package intulda.poly.assignment.domain.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import intulda.poly.assignment.domain.account.model.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_title", nullable = false)
    private String title;

    @Column(name = "board_contents", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime regDate;

    private LocalDateTime udtDate;

    @Column(name = "board_delete")
    @Enumerated(value = EnumType.STRING)
    private BoardState boardState = BoardState.STABLE;

    @Builder
    public Board(BoardRequest boardRequest) {
        this.title = boardRequest.getBoardTitle();
        this.contents = boardRequest.getBoardContents();
        this.regDate = LocalDateTime.now();
    }

    public void addAccount(Account account) {
        this.account = account;
    }

    public void changeState(String code) {
        this.boardState = BoardState.findHasCode(code);
        updateDate();
    }

    public void changeBoard(String title, String contents) {
        this.title = title;
        this.contents = contents;
        updateDate();
    }

    public void updateDate() {
        this.udtDate = LocalDateTime.now();
    }
}
