package com.project.expensemanager.service;

import com.project.expensemanager.domain.User;
import com.project.expensemanager.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public boolean registerNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.info(user.getPassword());
            userRepo.save(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    public User login(User input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));
        return userRepo.findByUsername(input.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found !"));
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("user not found !"));
    }

    public void deleteUser(String username) {
        getUserByUsername(username);
        userRepo.deleteByUsername(username);
    }

    public void encryptPasswordOnStartup(){
        List<User> users = userRepo.findAll();
        for (User user : users) {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepo.save(user);
        }
        log.info("all password in the startup script is encrypted");
    }
}
