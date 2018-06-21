package com.dhr.config.security;

import com.dhr.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private static final String DEFAULT_ROLE = "GUEST";

    @Inject
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.dhr.model.User userFromRepo = userRepository.findByLogin(userName);
        if (userFromRepo != null) {
            List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(DEFAULT_ROLE));
            return new User(userFromRepo.getLogin(), userFromRepo.getPassword(), grantedAuthorities);
        }
        throw new UsernameNotFoundException("User " + userName + " not found!");
    }
}
