package kr.lineus.unistars.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	 @Autowired
	 private AuthenticationSuccessHandler authenticationSuccessHandler;

	 @Autowired
	 private CustomAuthenticationEntryPoint entryPoint;

	 @Autowired
	 private CustomAuthenticationProvider authWProvider;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        
    	 httpSecurity.csrf().disable();
    	 httpSecurity.httpBasic().disable();
    	 /*
         httpSecurity.authorizeRequests()
                 .antMatchers("/", "/home", "/favicon.ico", "/css/**", "/js/**", "/partials/**", "/images/**").permitAll()
                 .antMatchers("/h2/**").permitAll()
                 .antMatchers("/health").permitAll()
                 .antMatchers("/login").permitAll()
                 .antMatchers("/debugloginlist").permitAll()
                 .anyRequest() 
                 .authenticated()
                 .and()
                 .httpBasic().authenticationEntryPoint(entryPoint);*/
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}user")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{noop}admin")
                .roles("ADMIN");
    }*/
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authWProvider);
    }
}