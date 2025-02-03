package lee.rentalbook.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @file OAuthSuccessHandler.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String accessToken = tokenProvider
        response.sendError(HttpServletResponse.SC_OK, "소셜 로그인에 성공했습니다.");
    }
}
