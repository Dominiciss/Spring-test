package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.User;
import com.example.demo.Repo.UserRepo;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo ur;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = ur.findEmail(email);

        if (user != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + "USER");
            permisos.add(p);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
