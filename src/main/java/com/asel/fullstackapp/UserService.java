package com.asel.fullstackapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired RoleRepository roleRepo;

    public void saveUserWithDefaultRole(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);


        Role roleUser = roleRepo.findByName("User");
        user.addRole(roleUser);

        userRepo.save(user);
    }

    //added a save method to save user
    public void save (User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);

    }

    //will change the roles assigned to a user.
    public List<User> listAll() {
        return userRepo.findAll();
    }

    //implemented the following two methods
    public User get(Long id) {
        return userRepo.findById(id).get();
    }

    public List<Role> listRoles() {
        return roleRepo.findAll();
    }


}