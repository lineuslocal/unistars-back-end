package kr.lineus.unistars.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/").permitAll()
			.antMatchers("/", "/favicon.ico", "/css/**", "/js/**", "/images/**").permitAll()
            .antMatchers("/v2/**").permitAll()
            .antMatchers("/v3/**").permitAll()
            .antMatchers("/h2/**").permitAll()
            .antMatchers("/configuration/ui/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/configuration/security/**").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui/index.html", "/api-docs/**").permitAll()
            .antMatchers("/api-docs/swagger-config").permitAll()
			.antMatchers("/swagger-ui.html").permitAll()
			.antMatchers("/health").permitAll()
			.antMatchers("/api/user/**").permitAll() //TODO: close this temporary access
			.antMatchers("/api/event/**").permitAll()
			.antMatchers("/api/faq/**").permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}