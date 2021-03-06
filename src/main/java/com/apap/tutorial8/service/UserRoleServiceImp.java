package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImp implements UserRoleService {
    @Autowired
    private UserRoleDb userDb;

    @Override
    public UserRoleModel addUser(UserRoleModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        return encode;
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        UserRoleModel user = userDb.findByUsername(username);
        String password = encrypt(newPassword);
        user.setPassword(password);
        userDb.save(user);
    }
}
