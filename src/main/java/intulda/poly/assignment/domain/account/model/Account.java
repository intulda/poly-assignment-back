package intulda.poly.assignment.domain.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account", nullable = false)
    private String account;

    @JsonIgnore
    @Column(name = "account_password", nullable = false)
    private String password;

    @Column(name = "account_name", nullable = false)
    private String name;

    //TODO: 공통으로 빼기
    @Column
    private LocalDateTime reg_no;

    @Column
    private LocalDateTime udt_no;

    @Builder
    public Account(AccountDTO accountDTO) {
        this.account = accountDTO.getAccount();
        this.password = accountDTO.getAccountPassword();
        this.name = accountDTO.getAccountName();
    }
}
