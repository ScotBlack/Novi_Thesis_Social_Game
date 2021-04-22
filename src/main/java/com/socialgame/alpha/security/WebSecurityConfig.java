package com.socialgame.alpha.security;

import antlr.BaseAST;
import com.socialgame.alpha.domain.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.socialgame.alpha.domain.enums.Roles.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles(ADMIN.name())
                .and()
                .withUser("host")
                .password("host")
                .roles(HOST.name())
                .and()
                .withUser("capt")
                .password("capt")
                .roles(CAPTAIN.name())
                .and()
                .withUser("player")
                .password("player")
                .roles(PLAYER.name());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("static/**").permitAll()
                .antMatchers("/api/guest").permitAll()
                .antMatchers("/api/host/**").hasRole(HOST.name())
                .antMatchers("/api/game/**").hasRole(HOST.name())
                .antMatchers("/api/player/**").hasAnyRole(PLAYER.name(), CAPTAIN.name(), HOST.name())
            .and()
                .httpBasic()
            .and()
                .formLogin()
            .and()
                .logout().permitAll();
    }
}


