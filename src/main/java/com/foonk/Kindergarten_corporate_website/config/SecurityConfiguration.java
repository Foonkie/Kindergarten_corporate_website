package com.foonk.Kindergarten_corporate_website.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.foonk.Kindergarten_corporate_website.database.Role.ADMIN;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
/*Конфигурирование авторизации и аунтификации. Метод configure отвечает за конфигурацию процесса авторизации и аунтификации. Метод passwordEncoder отвечает за преобразование пароля в зашифрованное значение.*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**", "/users/**", "/user/").permitAll()
                        .antMatchers("/users/{\\d+}/delete").hasAuthority(ADMIN.getAuthority())
                        .antMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
//                .httpBasic(Customizer.withDefaults());
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/default"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
