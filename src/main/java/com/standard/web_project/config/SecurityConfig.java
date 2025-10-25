package com.standard.web_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 보호 기능 비활성화
            .csrf((csrf) -> csrf.disable()) 
            
            .authorizeHttpRequests(auth  -> auth 
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // FORWARD 타입의 요청은 모두 허용    
                // 로그인 없이 접근 가능한 페이지 목록
                .requestMatchers("/", "/css/**", "/js/**", "/joinForm", "/loginForm", "/loginAction").permitAll()
                // 그 외 모든 페이지는 로그인을 해야만 접근 가능
                .anyRequest().authenticated()
            )
             
            .formLogin(form -> form // Spring Security의 기본 로그인 폼 대신
                .loginPage("/loginForm")              // 커스텀 로그인 페이지 주소
                .loginProcessingUrl("/loginAction")   // 로그인 처리를 할 URL (이 URL은 Spring Security가 사용)
                .usernameParameter("userId")          // 로그인 폼의 아이디 input name
                .passwordParameter("userPw")          // 로그인 폼의 비밀번호 input name
                .defaultSuccessUrl("/myPage", true) // 로그인 성공 시 강제로 이동시킬 페이지
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 처리 URL
                .logoutSuccessUrl("/loginForm") // 로그아웃 성공 시 이동할 페이지
                .invalidateHttpSession(true) // 세션 무효화
            );
            
        return http.build();
    }
}

