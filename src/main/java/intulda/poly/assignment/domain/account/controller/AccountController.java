package intulda.poly.assignment.domain.account.controller;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.model.AccountDTO;
import intulda.poly.assignment.domain.account.service.AccountService;
import intulda.poly.assignment.global.configuration.jwt.model.JwtRequest;
import intulda.poly.assignment.global.configuration.jwt.model.JwtResponse;
import intulda.poly.assignment.global.configuration.jwt.provider.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/**")
public class AccountController {

    private static final String PREFIX_URI = "account";

    private final AccountService accountService;

    private final JwtTokenProvider jwtTokenProvider;

    public AccountController(AccountService accountService, JwtTokenProvider jwtTokenProvider) {
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = PREFIX_URI)
    public ResponseEntity register(@RequestBody @Valid AccountDTO accountDTO) {
        Optional<Account> account = accountService.saveUser(
                Account.builder()
                        .accountDTO(accountDTO)
                        .build()
        );

        return new ResponseEntity(account, HttpStatus.OK);
    }

    @PostMapping(value = PREFIX_URI + "/login")
    public ResponseEntity login(@RequestBody @Valid JwtRequest jwtRequest) {
        AccountDTO accountDTO = AccountDTO.builder()
                .account(jwtRequest.getUsername())
                .accountPassword(jwtRequest.getPassword())
                .build();

        Account account = Account.builder()
                .accountDTO(accountDTO)
                .build();

        Optional<Account> result = accountService.findUser(account);
        Account user = null;
        String token = null;


        try {
            user = result.orElseThrow(IllegalArgumentException::new);
            token = jwtTokenProvider.generateToken(user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND);
        }

        JwtResponse jwtResponse = JwtResponse.builder()
                .token(token)
                .build();

        return new ResponseEntity(jwtResponse, HttpStatus.OK);
    }

}
