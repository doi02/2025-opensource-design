package doi.game_review_community.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        // 1) 정적 리소스
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // 2) 퍼블릭(비로그인) 기능
                        .requestMatchers(
                                "/", "/index",                   // 홈
                                "/user/signup", "/check-username", "/check-email",  // 회원가입·중복검사
                                "/login"                         // 로그인 폼
                                // 필요하다면 아래와 같이 추가!
                                // , "/games/**", "/reviews/**", "/search/**"
                        ).permitAll()

                        // 3) 그 외는 로그인 필요
                        .anyRequest().authenticated()
                )

                // 로그인 페이지 및 처리
                .formLogin(form -> form
                        .loginPage("/login")               // 로그인 폼 URL
                        .loginProcessingUrl("/login")      // POST 처리 URL (기본 /login)
                        .defaultSuccessUrl("/", true)      // 성공 후 홈으로
                        .failureUrl("/login?error")        // 실패 시 에러 파라미터
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

