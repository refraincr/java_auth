package com.security.uunnm.services;

import com.security.uunnm.entity.Users;
import com.security.uunnm.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    private final BCryptPasswordEncoder encoder;

    public UserService() {
        this.encoder = new BCryptPasswordEncoder(12);
    }

    public Users save(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
