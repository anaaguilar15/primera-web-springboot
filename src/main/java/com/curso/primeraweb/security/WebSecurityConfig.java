package com.curso.primeraweb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //registra beans
public class WebSecurityConfig {
    //metodo encriptador o {bcrypt}
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //usuarios registrados en memoria
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user1 = User.builder()
                .username("user")
                .password("$2a$10$55Ns1Itbqf/kffZY9Ru3o.Gudseuhuwz.fm4wWoT7T0GgoR5hoqqK")
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password("$2a$10$55Ns1Itbqf/kffZY9Ru3o.Gudseuhuwz.fm4wWoT7T0GgoR5hoqqK")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1,user2);
    }

    // autorizacion de rutas por rol,
    // parametros con *,
    // si no cumple manda la exepccion,
    // cualquier otra peticion aplica authenticated verificar que sea un usuario valido
    //no es necesario usar un autentication manager ya que los usu estan en memoria
    //pas encriptadas si o si

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/personas").permitAll()
                        .requestMatchers("/personas/nuevaPersona").hasAnyRole("ADMIN")
                        .requestMatchers("/personas/mostrarFormModif/*","/personas/eliminar/*").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form->form
                        .loginPage("/login")
                        .permitAll())
                .logout(l->l.permitAll())
                .exceptionHandling(e -> e.accessDeniedPage("/403"));
        return httpSecurity.build();
    }
}
