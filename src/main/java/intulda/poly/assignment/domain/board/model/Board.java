package intulda.poly.assignment.domain.board.model;

import intulda.poly.assignment.domain.account.model.Account;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    private Account account;

    @Column(name = "board_title", nullable = false)
    private String title;

    @Column(name = "board_contents", nullable = false)
    private String contents;

    private LocalDateTime regDate;

    private LocalDateTime udtDate;

    public void addAccount(Account account) {
        this.account = account;
    }
}
