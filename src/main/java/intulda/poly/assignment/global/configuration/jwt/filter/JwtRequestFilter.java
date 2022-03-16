package intulda.poly.assignment.global.configuration.jwt.filter;

import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.service.AccountService;
import intulda.poly.assignment.global.configuration.jwt.provider.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final AccountService accountService;

    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider, AccountService accountService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String userId = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userId = jwtTokenProvider.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.info("가져올 수 없는 JWT 토큰입니다.");
            } catch (ExpiredJwtException e) {
                logger.info("JWT 토근이 만료되었습니다.");
            }
        } else {
            logger.warn("JWT 토근의 전달 문자열로 시작하지 않습니다.");
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            Optional<Account> user = accountService.findUser(Long.parseLong(userId));
            if (user.isPresent()) {
                if (this.jwtTokenProvider.validateToken(jwtToken, user.get().getId())) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user.get(), null, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

            filterChain.doFilter(request, response);
    }
}
