package ru.kduskov.vkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kduskov.vkapi.interfaces.UserRepository;
import ru.kduskov.vkapi.model.CustomUserDetails;
import ru.kduskov.vkapi.model.User;
import ru.kduskov.vkapi.model.UserEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if(user ==  null) {
            throw  new UsernameNotFoundException("User was not found");
        }
        return new CustomUserDetails(user);
    }
}
