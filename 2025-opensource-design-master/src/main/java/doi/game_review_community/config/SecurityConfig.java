package doi.game_review_community.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    /**
     * PasswordEncoder 빈 등록 (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager 설정
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return auth.build();
    }

    /**
     * HTTP 보안 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 정적 리소스
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // 퍼블릭(비로그인) 기능
                        .requestMatchers(
                                "/", "/index",                   // 홈
                                "/user/signup", "/user/check-username", "/user/check-email",  // 회원가입·중복검사
                                "/login"                         // 로그인 폼
                        ).permitAll()
                        // 게임 조회 및 검색 허용
                        .requestMatchers(HttpMethod.GET, "/games/*").permitAll()
                        // 리뷰 등록, 수정, 삭제, 좋아요/싫어요 로그인필요
                        .requestMatchers(HttpMethod.POST, "/games/*/reviews/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/games/*/reviews/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/games/*/reviews/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/games/*/reviews/*/like").authenticated()
                        .requestMatchers(HttpMethod.POST, "/games/*/reviews/*/dislike").authenticated()
                        // 그외는 로그인필요
                        .anyRequest().authenticated()
                )

                // 로그인 페이지 및 처리
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}

