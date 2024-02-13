package ru.gb.springdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.springdemo.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration{
//    @Autowired
//    private UserRepository userRepository;
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
////        converter.setJwtGrantedAuthoritiesConverter(source -> {
////            Map<String, Object> resourceAccess = source.getClaim("realm_access");
////            List<String> roles = (List<String>) resourceAccess.get("roles");
////
////            return roles.stream()
////                    .map(SimpleGrantedAuthority::new)
////                    .collect(Collectors.toList());
////        });
//
//
//
//        httpSecurity
//                .authorizeHttpRequests(configurer -> configurer
//                        .requestMatchers("/issue/**").hasAuthority("admin")
//                        .requestMatchers("/reader/**").hasAuthority("reader")
//                        .requestMatchers("/book/**").authenticated()
//                        .requestMatchers("/ui/**").authenticated()
//                        .anyRequest().permitAll()
//                )
//                .formLogin(Customizer.withDefaults());
////                .oauth2ResourceServer(configurer -> configurer
////                        .jwt(jwtConfigurer -> jwtConfigurer
////                                .jwtAuthenticationConverter(converter))
////
//        return httpSecurity.build();
//    }
//
//}
