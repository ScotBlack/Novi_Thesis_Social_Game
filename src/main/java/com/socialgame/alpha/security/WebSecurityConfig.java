package com.socialgame.alpha.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.socialgame.alpha.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/index.html","index", "/css/*", "/js/*" ).permitAll()
//                .antMatchers("/game/**", "/lobby/**").hasRole(ADMIN.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }
//
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails ianUser = User.builder()
//                .username("ian")
//                .password(passwordEncoder.encode("password1"))
//                .roles(ADMIN.name()) // ROLE_PLAYER
//                .build();
//
//
//        UserDetails benUser = User.builder()
//                .username("ben")
//                .password(passwordEncoder.encode("password2"))
//                .roles("HOST")
//                .build();
//
////        UserDetails ariUser = User.builder()
////                .username("ari")
////                .password(passwordEncoder.encode("password3"))
////                .roles("TEAM")
////                .build();
//
//        UserDetails afiUser = User.builder()
//                .username("afi")
//                .password(passwordEncoder.encode("password3"))
//                .roles(PLAYER.name())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                ianUser,
//                benUser,
//                afiUser
//        );
//    }





    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails annaSmithUser = User.builder()
//                .username("annasmith")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER") // ROLE_USER
//                .build();
//
//        UserDetails aliUser = User.builder()
//                .username("ali")
//                .password(passwordEncoder.encode("password1"))
//                .roles("ADMIN")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(
//                annaSmithUser,
//                aliUser
//        );
//    }


    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeRequests()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/player/**").permitAll()
                    .antMatchers("/lobby/**").permitAll()
                    .antMatchers("/game/**").permitAll()
                    .antMatchers("/minigame/**").permitAll()
                    .antMatchers("/team/**").permitAll()
                    .anyRequest().authenticated();

//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}


