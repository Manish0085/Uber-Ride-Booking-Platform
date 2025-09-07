package com.ride.captain.service;


import com.ride.captain.entity.UserPrinciple;
import com.ride.captain.repo.CaptainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private CaptainRepo captainRepo;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return captainRepo.findById(userId)
                .map(UserPrinciple::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
    }


}