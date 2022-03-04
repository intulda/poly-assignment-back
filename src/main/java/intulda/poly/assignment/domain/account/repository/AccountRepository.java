package intulda.poly.assignment.domain.account.repository;

import intulda.poly.assignment.domain.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

}
