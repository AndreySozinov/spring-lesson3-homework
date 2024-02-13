package ru.gb.springdemo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
//public class CustomPasswordEncoder implements PasswordEncoder {
//    @Override
//    public String encode(CharSequence rawPassword) {
//        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
//        //return String.valueOf(passwordEncoder.encode(rawPassword));
//        return (String) rawPassword;
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return encode(rawPassword).equals(encodedPassword);
//    }
//}
