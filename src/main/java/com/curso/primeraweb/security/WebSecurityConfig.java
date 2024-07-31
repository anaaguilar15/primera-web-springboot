package com.curso.primeraweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration //registra beans
@EnableWebSecurity //habilita la seguridad y permite trabajar con la db?? si aplica ambas anotaciones es para desactivar la configuración de seguridad de la aplicación web predeterminada y agregar la suya propia.
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    //metodo para configurar el proceso de autenticacion
    // validacion desde la base de datos con jdbc
    @Autowired
    public void configAutentication (AuthenticationManagerBuilder builder) throws Exception {

        builder.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")//para cargar los usuarios
                .authoritiesByUsernameQuery("select username, role from users where username=?");//carga roles

    }

    //metodo encriptador o {bcrypt}
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
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
