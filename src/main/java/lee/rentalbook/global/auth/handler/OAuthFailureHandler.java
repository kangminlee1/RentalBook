package lee.rentalbook.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * @file OAuthFailureHandler.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
public class OAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "소셜 로그인에 실패했습니다.");
    }
}
