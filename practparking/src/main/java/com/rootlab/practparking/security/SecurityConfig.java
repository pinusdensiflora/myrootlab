package com.rootlab.practparking.security;

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

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
             .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) //로그인하지 않어도 들어올 수 있게 함
		     //.requestMatchers("/signin").permitAll() // 로그인 페이지 접근 허용
	         //.anyRequest().authenticated()) // 나머지 요청은 인증 필요
		 	 .csrf((csrf) -> csrf //개발시에는 기능을 꺼둠
		    		 .ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
		     		//.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/**")))
	         .formLogin((formLogin) -> formLogin
	                    .loginPage("/parking/signin")
	                    .defaultSuccessUrl("/parking/signup"))
	         .logout((logout) -> logout
	                    .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
	                    .logoutSuccessUrl("/")
	                    .invalidateHttpSession(true))
	         //.requiresChannel(channel -> channel
	         //.anyRequest().requiresSecure()); // HTTPS를 강제화
	         ;
		 
		 return http.build();
	}
	
	@Bean //비번 객체
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean //인가 객체
	 AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
