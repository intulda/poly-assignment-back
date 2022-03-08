package intulda.poly.assignment.domain.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import intulda.poly.assignment.domain.board.model.Board;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account", nullable = false, unique = true)
    private String account;

    @JsonIgnore
    @Column(name = "account_password", nullable = false)
    private String password;

    @Column(name = "account_name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account")
    private List<Board> boards;

    //TODO: 공통으로 빼기
    @Column
    private LocalDateTime reg_no = LocalDateTime.now();

    @Column
    private LocalDateTime udt_no;

    protected Account() {
        this.reg_no = LocalDateTime.now();
    }

    @Builder
    public Account(AccountRequest accountRequest) {
        this.account = accountRequest.getAccount();
        this.password = accountRequest.getAccountPassword();
        this.name = accountRequest.getAccountName();
    }
}
