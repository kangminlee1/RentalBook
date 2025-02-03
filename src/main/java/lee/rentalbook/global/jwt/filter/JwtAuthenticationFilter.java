package lee.rentalbook.global.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lee.rentalbook.global.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @file JwtAuthenticationFilter.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-15
 * 로그인 후 요청 처리 토큰 검증, 인증정보 설정
 * 인증권한이 필요한 요청이 왔을 때 이 필터 사용
*/
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    private final UserRepository userRepository;
    private final JwtProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //토큰 추출
        String accessToken = extractToken(request);

        //토큰 유효 확인
        if (provider.isValidToken(accessToken)) {
            setAuthentication(accessToken);
        }else{
            String reissueAccessToken = provider.reissueAccessJwtToken(accessToken);
            if (StringUtils.hasText(reissueAccessToken)) {
                setAuthentication(reissueAccessToken);
                response.setHeader("Authorization", "Bearer " + reissueAccessToken);//이건 이미 header가 존재하면 덮어씌움
            }
        }
        //인증 정보 저장 및 다음 필터로
        chain.doFilter(request, response);
    }

    /**
     * Security Session에 접근하여 Authentication 객체 저장
     * @param accessToken
     */
    private void setAuthentication(String accessToken) {
        Authentication authentication = provider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 토큰 추출
     * @param request
     * @return
     */
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return header.replace("Bearer ", "");
        }
        return null;
    }

}
