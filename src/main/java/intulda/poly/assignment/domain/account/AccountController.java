package intulda.poly.assignment.domain.account;

import intulda.poly.assignment.global.configuration.jwt.model.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/*")
public class AccountController {

    @PostMapping(value = "authenticate")
    public ResponseEntity login(@RequestBody JwtRequest jwtRequest) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
