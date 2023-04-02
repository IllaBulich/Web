package com.example.web.services;

import com.example.web.models.Immovables;
import com.example.web.models.Rental;
import com.example.web.models.Role;
import com.example.web.models.User;
import com.example.web.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImmovablesService immovablesService;

    public boolean createUser(User user) {
        String userEmail = user.getEmail();
        if (userRepository.findByEmail(userEmail) != null) return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new User with email: {}", userEmail);
        userRepository.save(user);
        return true;
    }

    public List<User> list(){
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if( user != null){
            if(user.isActive()){
                user.setActive(false);
                log.info("Ban user id={}; email={}",user.getId(),user.getEmail());
            }else {
                user.setActive(true);
                log.info("UnBan user id={}; email={}",user.getId(),user.getEmail());
            }
        }
        userRepository.save(user);
    }

    public void createUserRoles(User user, Map<String, String> form) {

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return  new User();
        return userRepository.findByEmail(principal.getName());
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<Immovables> getImmovablesRental(List<Rental> rentals) {
        List<Immovables> immovables = new ArrayList<>();
        for(Rental rental : rentals){

            immovables.add(immovablesService.getImmovablesById(rental.getImmovable().getId()));
        }
        return immovables;
    }
}
