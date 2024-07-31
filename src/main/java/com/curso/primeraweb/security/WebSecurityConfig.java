package com.curso.primeraweb.security;

import com.curso.primeraweb.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //registra beans
@EnableWebSecurity //habilita la seguridad y permite trabajar con la db?? si aplica ambas anotaciones es para desactivar la configuración de seguridad de la aplicación web predeterminada y agregar la suya propia.
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //DaoAuthenticationProvider es una implementacion del AuthenticationProvider
    //Se utiliza  para autenticar usuarios en bases de datos
    //Es responsable de verificar las credenciales del usuario y autenticar el usuario
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        //cuando se realiza una solicitud el administrador de autenticación usará el provider
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    //Define las reglas de autorizacion para las solicitudes HTTP
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
                                .requestMatchers("/personas").hasAnyAuthority("USER","CREATOR","EDITOR","ADMIN")
                                .requestMatchers("/personas/nueva").hasAnyAuthority("ADMIN","CREATOR")
                                .requestMatchers("/personas/editar/*").hasAnyAuthority("ADMIN","EDITOR")
                                .requestMatchers("/personas/eliminar/*").hasAnyAuthority("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(l -> l.permitAll())
                .exceptionHandling(e -> e.accessDeniedPage("/403"));
        return httpSecurity.build();
    }
}
