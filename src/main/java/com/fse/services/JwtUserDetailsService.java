package com.fse.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.modals.Users;
import com.fse.repositories.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class JwtUserDetailsService implements UserDetailsService  {
	@Autowired
    private UsersRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users foundedUser = userRepository.findByUserName(username);
        if(foundedUser == null) return null;
        String name = foundedUser.getUserName();
        String pwd = foundedUser.getPassword();
        return new User(name, pwd, new ArrayList<>());
    }
}
