//package com.socialgame.alpha.configuration.security;
//
//import com.socialgame.alpha.domain.User;
//import com.socialgame.alpha.service.UserDetailsImpl;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import java.util.Arrays;
//
//@TestConfiguration
//public class SpringSecurityWebAuxTestConfig {
//
//    @Bean
//    @Primary
//    public UserDetailsService userDetailsService() {
//        User basicUser = new UserDetailsImpl(1L, "Basic User",  "password");
//        UserActive basicActiveUser = new UserActive(basicUser, Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_USER"),
//                new SimpleGrantedAuthority("PERM_FOO_READ")
//        ));
//
//        User managerUser = new UserDetailsImpl("Manager User", "manager@company.com", "password");
//        UserActive managerActiveUser = new UserActive(managerUser, Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_MANAGER"),
//                new SimpleGrantedAuthority("PERM_FOO_READ"),
//                new SimpleGrantedAuthority("PERM_FOO_WRITE"),
//                new SimpleGrantedAuthority("PERM_FOO_MANAGE")
//        ));
//
//        return new InMemoryUserDetailsManager(Arrays.asList(
//                basicActiveUser, managerActiveUser
//        ));
//    }
//}
