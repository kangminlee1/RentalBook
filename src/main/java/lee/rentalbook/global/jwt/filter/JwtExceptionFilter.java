package lee.rentalbook.global.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lee.rentalbook.global.exception.TokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @file JwtExceptionFilter.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-15
 */
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
//토큰에 발생한 오류 처리 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenException e) {
            response.sendError(e.getErrorCode().getHttpStatus().value(), e.getMessage());
        }
    }
}
