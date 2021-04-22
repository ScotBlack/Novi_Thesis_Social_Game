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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN")
                .and()
                .withUser("ian")
                .password("ian1")
                .roles("HOST")
                .and()
                .withUser("ben")
                .password("ben1")
                .roles(Roles.CAPTAIN.name())
                .and()
                .withUser("afi")
                .password("afi1")
                .roles("PLAYER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/guest").permitAll()
                .antMatchers("/api/host/**").hasRole("HOST")
                .antMatchers("/api/game/**").hasRole("HOST")
                .antMatchers("/api/player/**").hasAnyRole("PLAYER", "HOST")
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


