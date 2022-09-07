package com.asel.fullstackapp;

//imports auto- generated
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//public class Appcontroller added annotation Controller
@Controller
public class AppController {

    //added annotation  @Autowired
    @Autowired
    private UserService service;


    //added getmapping method
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    //added a new handler method in the controller class to show the user registration form (sign up), with the following code:
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        //will return sign up html
        return "signup_form";
    }

    //post mapping method
    @PostMapping("/process_register")
    public String processRegister(User user) {
        service.saveUserWithDefaultRole(user);
        //will return register_success
        return "register_success";
    }

    //added Getmapping method will map the list_users and assign list of user to user repo with find method
    @GetMapping("/list_users")
    public String viewUserList(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        //added return will -->  return users
        return "users";
    }

    //added get mapping for editing
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = service.get(id);
        List<Role> listRoles = service.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }


    //aadded postMapping method and implement save method from Userservice  class
    @PostMapping("/users/save")
    public String saveUser(User user) {
         service.save(user);
         return "redirect:/list_users";
    }

}
