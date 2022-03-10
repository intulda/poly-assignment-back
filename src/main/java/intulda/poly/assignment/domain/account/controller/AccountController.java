package intulda.poly.assignment.domain.account.controller;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.model.AccountRequest;
import intulda.poly.assignment.domain.account.service.AccountService;
import intulda.poly.assignment.global.configuration.jwt.model.JwtRequest;
import intulda.poly.assignment.global.configuration.jwt.model.JwtResponse;
import intulda.poly.assignment.global.configuration.jwt.provider.JwtTokenProvider;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class AccountController {

    private static final String PREFIX_URI = "account";

    private final AccountService accountService;

    private final JwtTokenProvider jwtTokenProvider;

    public AccountController(AccountService accountService, JwtTokenProvider jwtTokenProvider) {
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation(value = "find myInformation", notes = "내 정보 찾기")
    @GetMapping(value = PREFIX_URI)
    public ResponseEntity findMe(@Valid final HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String getId = null;

        if (token != null) {
            getId = jwtTokenProvider.getUsernameFromToken(token);
        }

        Account user = accountService.findUser(Long.parseLong(getId)).orElseThrow(IllegalArgumentException::new);

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @ApiOperation(value = "account register", notes = "계정 생성")
    @PostMapping(value = PREFIX_URI)
    public ResponseEntity register(@RequestBody @Valid AccountRequest accountRequest) {
        Optional<Account> account = accountService.saveUser(accountRequest);

        return new ResponseEntity(account, HttpStatus.OK);
    }

    @ApiOperation(value = "login", notes = "로그인")
    @PostMapping(value = PREFIX_URI + "/login")
    public ResponseEntity login(@RequestBody @Valid JwtRequest jwtRequest) {


        AccountRequest accountRequest = AccountRequest.builder()
                .account(jwtRequest.getUsername())
                .accountPassword(jwtRequest.getPassword())
                .build();

        Account account = Account.builder()
                .accountRequest(accountRequest)
                .build();

        Account result = accountService.findUser(account);
        String token = null;

        try {
            token = jwtTokenProvider.generateToken(result);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND);
        }

        JwtResponse jwtResponse = JwtResponse.builder()
                .token(token)
                .build();

        return new ResponseEntity(jwtResponse, HttpStatus.OK);
    }

}
