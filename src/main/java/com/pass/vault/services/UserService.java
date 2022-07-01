package com.pass.vault.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pass.vault.entities.UserEntity;
import com.pass.vault.repositories.UserRepository;
import com.pass.vault.requests.RegisterRequest;

@Service
public class UserService {

    @Autowired
    UserRepository uRepo;

    @Autowired
    PasswordEncoder encoder;

    public UserEntity registerUser(RegisterRequest request) {
        UserEntity entity = new UserEntity();
        try {
            UserEntity exist = uRepo.findByEmail(request.getEmail()).orElse(null);
            if (exist == null) {
                entity.setEmail(request.getEmail());
                entity.setUsername(request.getName());
                entity.setPassword(encoder.encode(request.getPassword()));
                entity.setCreateTime(new Date());
                entity.setUpdateAt(new Date());
                uRepo.save(entity);
                return entity;
            }
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
