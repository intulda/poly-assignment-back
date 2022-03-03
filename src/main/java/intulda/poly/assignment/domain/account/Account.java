package intulda.poly.assignment.domain.account;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "account_password", nullable = false)
    private String password;

    @Column(name = "account_name")
    private String name;

    //TODO: 공통으로 빼기
    @Column
    private LocalDateTime reg_no;

    @Column
    private LocalDateTime udt_no;

}
