package lee.rentalbook.domain.redis.service;


import lee.rentalbook.domain.redis.Token;
import lee.rentalbook.domain.redis.repository.TokenRepository;
import lee.rentalbook.global.exception.ErrorCode;
import lee.rentalbook.global.exception.TokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lee.rentalbook.global.exception.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public void deleteRefreshToken(String memberKey) {
        tokenRepository.deleteById(memberKey);
    }

    @Transactional
    public void saveOrUpdate(String memberKey, String refreshToken, String accessToken) {
        Token token = tokenRepository.findByAccessToken(accessToken)
                .map(o -> o.updateRefreshToken(refreshToken))
                .orElseGet(() -> new Token(memberKey, refreshToken, accessToken));
        tokenRepository.save(token);
    }

    public Token findByAccessTokenOrThrow(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new TokenException(TOKEN_EXPIRED));
    }

    public void updateToken(String accessToken, Token token) {
        token.updateRefreshToken(accessToken);
        tokenRepository.save(token);
    }
}
