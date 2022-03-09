package intulda.poly.assignment.domain.account.service;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.model.AccountRequest;
import intulda.poly.assignment.domain.account.repository.AccountRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountService(AccountRepository accountRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<Account> findUser(Long id) {
        return this.accountRepository.findById(id);
    }

    public Account findUser(Account account) {
        Optional<Account> userById = this.accountRepository.findUserById(account);

        Account user = userById.orElseThrow(IllegalArgumentException::new);

        if (!bCryptPasswordEncoder.matches(account.getPassword(), user.getPassword())) {
            return null;
        }

        return user;
    }

    public Optional<Account> saveUser(AccountRequest accountRequest) {
        String bcryptPassword = bCryptPasswordEncoder.encode(accountRequest.getAccountPassword());
        accountRequest.setAccountPassword(bcryptPassword);

        Account account = Account.builder()
                .accountRequest(accountRequest)
                .build();

        return Optional.of(this.accountRepository.save(account));
    }

}
