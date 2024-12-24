package com.rootlab.practparking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rootlab.practparking.user.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

	
	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/api/**", "/signup", "/signin") // 로그인, 회원가입, API 경로 허용
                .permitAll()
                .requestMatchers("/static/**", "/js/**", "/css/**", "/images/**") // 정적 자원 경로 허용
                .permitAll()
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/**")) // 개발 시 CSRF 보호 끄기
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/signin") // 로그인 페이지 경로
                .failureUrl("/signin?error=true")//실패 시 URL
                .defaultSuccessUrl("/home", true) // 로그인 성공 시 이동할 URL
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/signout")) // 로그아웃 경로
                .logoutSuccessUrl("/home") // 로그아웃 후 이동할 URL
                .invalidateHttpSession(true) // 세션 무효화
            );

		 return http.build();
	}
	
	@Bean //비번 객체
	PasswordEncoder passwordEncoder() {
		//return new Sha256PasswordEncoder(); // SHA-256 기반 암호화 인코더)
		return new BCryptPasswordEncoder();// BCrypt 기반암호화
	}
	
	@Bean //인가 객체
	 AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
/*
 * 	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
             //.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) //로그인하지 않어도 들어올 수 있게 함
		 	 .requestMatchers("/signin", "/signup").permitAll()) // 로그인/회원가입 페이지는 인증 없이 접근 가능
		     //.requestMatchers("/signin").permitAll() // 로그인 페이지 접근 허용
	         //.anyRequest().authenticated()) // 나머지 요청은 인증 필요
		 	 .csrf((csrf) -> csrf //개발시에는 기능을 꺼둠
		    		 .ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
		     		//.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/**")))
	         .formLogin((formLogin) -> formLogin
	                    .loginPage("/signin")
	                    //.loginProcessingUrl("/parking/login") // 로그인 요청 경로
	                    .defaultSuccessUrl("/home"))
	         .logout((logout) -> logout
	                    .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
	                    .logoutSuccessUrl("/home")
	                    .invalidateHttpSession(true))
	         //.requiresChannel(channel -> channel
	         //.anyRequest().requiresSecure()); // HTTPS를 강제화
	         ;
		 
		 return http.build();
	}*/
