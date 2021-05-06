package com.socialgame.alpha.security;

import com.socialgame.alpha.domain.enums.ERole;
import com.socialgame.alpha.repository.RoleRepository;
import com.socialgame.alpha.security.jwt.AuthEntryPointJwt;
import com.socialgame.alpha.security.jwt.AuthTokenFilter;
import com.socialgame.alpha.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hier gebruiken we de EnableGlobalMethodSecurity(prePostIsEnabled = true) om de @PreAuthorize annotaties te gebruiken
 * op andere plekken in de applicatie.
 */
@Configuration
//@ComponentScan("com.socialgame.alpha")
@EnableWebSecurity
//@EnableTransactionManagement
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;



    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    public void setRoleRepository (RoleRepository roleRepository) {this.roleRepository = roleRepository;}

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


//    @Bean
//    public RoleRepository roleRepository() { return new RoleRepository(); }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//
//    @Bean
//    public UserDetailsService userDetailsService()  {
//        UserDetailsService userDetailsService =
//                new UserDetailsServiceImpl();
//        return userDetailsService;
//    }





    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/game/**").hasRole("GAMEHOST")
                .antMatchers("/api/host/**").hasRole("GAMEHOST")
                .antMatchers("/api/player/**").hasRole("PLAYER")
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .anyRequest().authenticated();

        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/login");

        // Apply JWT
//        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
//
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}