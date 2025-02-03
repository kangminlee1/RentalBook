package lee.rentalbook.global.auth.config;

import lee.rentalbook.global.jwt.filter.JwtAuthenticationFilter;
import lee.rentalbook.global.jwt.filter.JwtExceptionFilter;
import lee.rentalbook.global.auth.handler.CustomAccessDeniedHandler;
import lee.rentalbook.global.auth.handler.CustomAuthenticationEntryPoint;
import lee.rentalbook.global.auth.handler.OAuthFailureHandler;
import lee.rentalbook.global.auth.handler.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @file SecurityConfig.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-15
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final OAuth2UserService oAuth2UserService;
    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
        * 필터 순서
        * 1. 인증 필터는 SecurityContextPersistenceFilter 관련 필터 전에 위치
        * 2. 권한 필터는 인증 후 최종적으로 권한 검사하는 필터 뒤에 위치
        * 3. 에외 처리 필터는 인증, 인가 필터 뒤에 위치하여 예외 발생시 처리가능하게 배치
        * */
        return http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)//Token 방식 - Bearer
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))//세션 허용 X

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                        .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
                        .anyRequest().permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(info -> info.userService(oAuth2UserService))
                        .successHandler(oAuthSuccessHandler)
                        .failureHandler(new OAuthFailureHandler())
                )

                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)//Login 과정 전에 인증처리하는 필터 추가
//                .addFilter(new JwtAuthorizationFilter(authenticationManager))
                .addFilterBefore(new JwtExceptionFilter(), authenticationFilter.getClass())//JWT 인증 전에 오류 처리 필터 추가

                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
