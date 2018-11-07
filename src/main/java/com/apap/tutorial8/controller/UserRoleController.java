package com.apap.tutorial8.controller;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;
import com.apap.tutorial8.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    @Autowired
    private UserRoleDb userDb;


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserRoleModel user) {
        userService.addUser(user);
        return "home";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    private String updateUserSubmit(Principal principal, HttpServletRequest httpReq, Model model) {
        String username = principal.getName();
        String oldPassword = httpReq.getParameter("oldPassword");
        String newPassword = httpReq.getParameter("newPassword");
        String confirmPassword = httpReq.getParameter("confirmPassword");
        String currentPassword = userDb.findByUsername(principal.getName()).getPassword();
        System.out.println(oldPassword);
        System.out.println(currentPassword);

        PasswordEncoder passEncoder = new BCryptPasswordEncoder();
        boolean matchesPass = passEncoder.matches(oldPassword, currentPassword);
        if(matchesPass){
           model.addAttribute("updateUser", "success");
             userService.updatePassword(username, newPassword);
        }
        else if(!newPassword.equalsIgnoreCase(confirmPassword)) {
            model.addAttribute("updateUser", "This input password is not same!");
        }
        else {
            model.addAttribute("updateUser", "Incorrect old password! Please try again");
        }
        return "home";
    }
}