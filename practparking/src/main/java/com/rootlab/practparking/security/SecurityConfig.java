package com.rootlab.practparking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
             .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) //로그인하지 않어도 들어올 수 있게 함
		     .csrf((csrf) -> csrf //개발시에는 기능을 꺼둠
		    		 .ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
		     
	         .formLogin((formLogin) -> formLogin
	                    .loginPage("/signin")
	                    .defaultSuccessUrl("/"))
	         .logout((logout) -> logout
	                    .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
	                    .logoutSuccessUrl("/")
	                    .invalidateHttpSession(true))
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
