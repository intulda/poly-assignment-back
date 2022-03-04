package intulda.poly.assignment.domain.account.service;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findUser(Long id) {
        return this.accountRepository.findById(id);
    }

    public Optional<Account> findUser(Account account) {
        return this.accountRepository.findUser(account);
    }

}
