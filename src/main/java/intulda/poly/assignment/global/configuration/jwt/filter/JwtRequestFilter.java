package intulda.poly.assignment.global.configuration.jwt.filter;

import intulda.poly.assignment.global.configuration.jwt.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {

            } catch (IllegalArgumentException e) {
                logger.info("가져올 수 없는 JWT 토큰입니다.");
            } catch (ExpiredJwtException e) {
                logger.info("JWT 토근이 만료되었습니다.");
            }
        } else {
            logger.warn("JWT 토근의 전달 문자열로 시작하지 않습니다.");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            /**
             * TODO: 유저정보 가져온 뒤 validateToken에서 비교 후  UsernamePasswordAuthentication 넣어줘야한다.
             */
//            if (this.jwtTokenUtil.validateToken(jwtToken, user)) {
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, authorities
//                );
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
        }

        filterChain.doFilter(request, response);
    }
}
