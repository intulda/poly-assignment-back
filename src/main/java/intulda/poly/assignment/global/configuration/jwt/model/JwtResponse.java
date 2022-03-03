package intulda.poly.assignment.global.configuration.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Builder
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 1001168421322344931L;

    private String token;
}
