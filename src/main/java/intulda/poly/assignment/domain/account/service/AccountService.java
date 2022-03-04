package intulda.poly.assignment.domain.account.service;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.model.AccountDTO;
import intulda.poly.assignment.domain.account.repository.AccountRepository;
import intulda.poly.assignment.global.configuration.jwt.model.JwtRequest;
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

    public Optional<Account> saveUser(AccountDTO accountDTO) {
        String bcryptPassword = bCryptPasswordEncoder.encode(accountDTO.getAccountPassword());
        accountDTO.setAccountPassword(bcryptPassword);

        Account account = Account.builder()
                .accountDTO(accountDTO)
                .build();

        return Optional.of(this.accountRepository.save(account));
    }

}
