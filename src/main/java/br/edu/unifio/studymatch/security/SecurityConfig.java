package br.edu.unifio.studymatch.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
@Configuration @EnableWebSecurity
public class SecurityConfig {
  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
  @Bean UserDetailsService usuarios(PasswordEncoder encoder) {
    UserDetails admin=User.withUsername("admin@studymatch.test").password(encoder.encode("Admin@1234")).roles("ADMIN").build();
    UserDetails aluno=User.withUsername("aluno@studymatch.test").password(encoder.encode("Aluno@1234")).roles("ALUNO").build();
    return new InMemoryUserDetailsManager(admin,aluno);
  }
  @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf->csrf.disable()) // API didática sem sessão/cookie: Basic em cada requisição
      .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth->auth
        .requestMatchers(HttpMethod.GET,"/api/**").authenticated()
        .requestMatchers(HttpMethod.POST,"/api/solicitacoes").authenticated()
        .requestMatchers("/api/**").hasRole("ADMIN")
        .anyRequest().permitAll())
      .httpBasic(Customizer.withDefaults()).build();
  }
}
