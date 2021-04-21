package hh.swd20.palvelinohjelmointiprojekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import hh.swd20.palvelinohjelmointiprojekti.domain.UserRepository;
import hh.swd20.palvelinohjelmointiprojekti.domain.UserDetailServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().antMatchers("/elokuvalista").permitAll()
		.and()
		.authorizeRequests().antMatchers("/elokuvatTagilla/*").permitAll()
		.and()
		.authorizeRequests().antMatchers("/elokuvatGenrella/*").permitAll()
		.and()
		.authorizeRequests().anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/elokuvalista")
			.permitAll()
			.and()
		.logout()
			.permitAll();
		
	} 
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	
	
}
