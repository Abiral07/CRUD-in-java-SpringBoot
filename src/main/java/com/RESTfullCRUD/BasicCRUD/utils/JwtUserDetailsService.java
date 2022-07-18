package com.RESTfullCRUD.BasicCRUD.utils;

import com.RESTfullCRUD.BasicCRUD.dto.CustomUserDetails;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import com.RESTfullCRUD.BasicCRUD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findMatchedUser(username);
        if(user != null)
            return new CustomUserDetails(user);
        else
            throw new UsernameNotFoundException("User not Found");
    }
}
