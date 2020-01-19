package com.dslab.video_management_service;

import com.dslab.video_management_service.entity.User;
import com.dslab.video_management_service.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoManagerDetailUserService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if ( user == null ){
            throw new UsernameNotFoundException("Utente non trovato!!!");
        }
        final org.springframework.security.core.userdetails.User user1;
        user1 = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                getEncoder().encode(user.getPassword()),
                true,
                true,
                true,
                true,
                getAuth(user.getRoles())
        );
        return user1;
    }

    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<GrantedAuthority> getAuth(String roles){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles));
        return authorities;
    }
}
