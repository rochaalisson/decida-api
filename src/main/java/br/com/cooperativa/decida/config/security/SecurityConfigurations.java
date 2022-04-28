package br.com.cooperativa.decida.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.cooperativa.decida.repository.UsuarioRepository;
import br.com.cooperativa.decida.service.AutenticacaoService;
import br.com.cooperativa.decida.service.TokenService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	private AutenticacaoService autenticacaoService;
	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	// Autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// Autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/pautas").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated().and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(new AutenticacaoTokenFilter(tokenService, usuarioRepository),
						UsernamePasswordAuthenticationFilter.class);
	}

	// Recursos estáticos
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
        	.antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");
	}
}
