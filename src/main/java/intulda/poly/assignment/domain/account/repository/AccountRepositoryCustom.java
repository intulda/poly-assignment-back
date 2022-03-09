package intulda.poly.assignment.domain.account.repository;

import intulda.poly.assignment.domain.account.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepositoryCustom {

    Optional<Account> findUserById(Account account);

}
