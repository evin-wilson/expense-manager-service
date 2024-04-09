package com.project.expensemanager.service;

import com.project.expensemanager.domain.User;
import com.project.expensemanager.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepo userRepo;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public boolean registerNewUser(User user) {
        try {
            userRepo.save(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("user not found !"));
    }

    public void deleteUser(String username) {
        getUserByUsername(username);
        userRepo.deleteByUsername(username);
    }
}
