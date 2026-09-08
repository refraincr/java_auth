package com.security.uunnm.services;

import com.security.uunnm.entity.Users;
import com.security.uunnm.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder;

    public UserService() {
        this.encoder = new BCryptPasswordEncoder(12);
    }

    public Users save(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String login(Users user) {
        // 验证数据库的用户名密码
        Authentication authentication = authManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                ));
        if (authentication.isAuthenticated()) return jwtService.getJwt(user.getUsername());
        return "Fail";
    }
}
