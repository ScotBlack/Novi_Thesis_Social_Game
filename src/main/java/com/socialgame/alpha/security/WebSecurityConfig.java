package com.socialgame.alpha.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.socialgame.alpha.domain.enums.ERole.*;

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

    //    @Bean
//    public PasswordEncoder passwordEncoder () {
//        return new BCryptPasswordEncoder(10);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
////                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .antMatchers("/api/**").hasRole("ADMIN")
//                .antMatchers("/player/**").permitAll()
//                .antMatchers("/lobby/**").permitAll()
//                .antMatchers("/game/**").permitAll()
//                .antMatchers("/minigame/**").permitAll()
//                .antMatchers("/team/**").permitAll()
//                .anyRequest().authenticated();
//
////        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
}


