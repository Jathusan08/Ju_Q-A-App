package com.JU.QuestionAndAnswer_App.config;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration  // this to tell Spring make this class as Spring Java configuration class
@EnableWebSecurity				// enable Spring Security in our spring application.
public class WebSpringSecurity {

	private UserDetailsService userDetailsService;
	
	
	public WebSpringSecurity() {}
	
	@Autowired
	public WebSpringSecurity(UserDetailsService userDetailsService) {
	
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public static  PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}

	// define security filer chain bean
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		
		// configure Spring Security using HttpSecurity object
		  http.csrf().disable()
          .authorizeHttpRequests((authorize) ->
          			//	authorize.anyRequest().authenticated()
                      authorize.requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()
                             .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                             .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyRole("ADMIN", "GUEST")
                            .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
              //              .requestMatchers(HttpMethod.POST, "/comments/**").permitAll()
                   //         .requestMatchers("/comments").permitAll()
                   //         .requestMatchers("/comments/**").permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/post/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/**/comments")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/page/search")).permitAll()
                   //         .requestMatchers(new AntPathRequestMatcher("/page/search)).permitAll()
                          .anyRequest().authenticated()
          ) 
          .formLogin( form -> form
        		  		.loginPage("/login")
        		  		.defaultSuccessUrl("/admin/posts")
        		  		.loginProcessingUrl("/login")
        		  		.permitAll()
        		  	)
          .logout( logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
          );
  return http.build();
		
	  
		
		
		
//		
	}
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		
		builder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
		
		
		
	}
	
	
	
	
	
	
}
