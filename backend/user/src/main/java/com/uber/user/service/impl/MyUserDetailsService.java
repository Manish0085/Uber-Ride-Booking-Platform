package com.uber.user.service.impl;

//import com.uber.user.entity.UserPrincipal;
import com.uber.user.entity.User;
import com.uber.user.entity.UserPrinciple;
import com.uber.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepo.findById(userId)
                .map(UserPrinciple::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
    }


}