package lee.rentalbook.global.jwt.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lee.rentalbook.domain.redis.Token;
import lee.rentalbook.domain.redis.service.TokenService;
//import lee.rentalbook.domain.user.User;
import lee.rentalbook.global.auth.PrincipalDetails;
import lee.rentalbook.global.exception.TokenException;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static lee.rentalbook.global.exception.ErrorCode.INVALID_JWT_SIGNATURE;
import static lee.rentalbook.global.exception.ErrorCode.INVALID_TOKEN;

/**
 * @file JwtProvider.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-15
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${secret.key}")
    private String key;
    private SecretKey secretKey;
    private final static Long ACCESS_EXPIRED_TIME = 1000L*60*5;
    private final static Long REFRESH_EXPIRED_TIME = 1000L * 60 * 60 * 24 * 7; //재발급 토큰은 7일간 유지
    private final static String KEY_ROLE = "role";
    private final TokenService tokenService;

    /**
     * 비밀키 생성
     */
    @PostConstruct
    private void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }


    /**
     * JWT 생성
     * @param authentication
     * @return
     */
    public String generateJwtToken(Authentication authentication, long expiredTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiredTime);
        //권한을 문자열로 변경
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(KEY_ROLE, authorities)
                .addClaims(Map.of("name", authentication.getName(),
                        "email", ((PrincipalDetails) authentication.getPrincipal()).getEmail()))
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Access 토큰 생성
     * @param authentication
     * @return
     */
    public String generateAccessJwtToken(Authentication authentication) {
        return generateJwtToken(authentication, ACCESS_EXPIRED_TIME);
    }

    /**
     * refresh 토큰 생성
     * @param authentication
     * @param accessToken
     */
    public void generateRefreshJwtToken(Authentication authentication, String accessToken) {
        String refreshToken = generateJwtToken(authentication, REFRESH_EXPIRED_TIME);
        tokenService.saveOrUpdate(authentication.getName(), refreshToken, accessToken);
    }

    public Authentication getAuthentication(String token) {
       Claims claims = parseClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
//        String role = claims.get(KEY_ROLE).toString();
        return Collections.singletonList(new SimpleGrantedAuthority(claims.get(KEY_ROLE).toString()));
    }


    //재발급
    public String reissueAccessJwtToken(String accessToken) {
        if (StringUtils.hasText(accessToken)) {
            Token token = tokenService.findByAccessTokenOrThrow(accessToken);
            String refreshToken = token.getRefreshToken();
            if (isValidToken(refreshToken)) {
                String reissueAccessToken = generateAccessJwtToken(getAuthentication(refreshToken));
                tokenService.updateToken(reissueAccessToken, token);
                return refreshToken;
            }
        }
        return null;
    }


    /**
     * 토큰 유효성 검사
     * @param token
     * @return
     */
    public boolean isValidToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        Claims claims = parseClaims(token);
        //현재 시간과 비교해서 만료되었는지 안되었는지 확인
        return claims.getExpiration().after(new Date());
    }

    /**
     * JWT 토큰 파싱 후 payload 반환
     * @param token
     * @return
     */
    private Claims parseClaims(String token) {
        try {
//            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            //
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {//토큰이 만료된 경우
            return e.getClaims();
        } catch (MalformedJwtException e) {//토큰 형식이 올바르지 않을 경우
            throw new TokenException(INVALID_TOKEN);
        } catch (SecurityException e) {//토큰이 변조되었거나 유효하지 않을 경우
            throw new TokenException(INVALID_JWT_SIGNATURE);
        }
    }
    //refresh ->   2개   만료 or 탈취 -> 이걸로 accessToken 재발급
    // 토큰 캐싱 탈취, ㅁㄹ -> 캐시



}
