package com.tts.ecomspring.controller;

import com.tts.ecomspring.model.User;
import com.tts.ecomspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/signin")
    public String login(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "signin";
    }

    @PostMapping(value = "/signin")
    public String signup(@Valid User user, @RequestParam String submit, BindingResult bindingResult,
                         HttpServletRequest request, Model model) throws ServletException {
        model.addAttribute("user", new User());

        String password = user.getPassword();
        if (submit.equals("up")) {

            User userExists = userService.findByUsername(user.getUsername());
            if (userExists != null) {
                bindingResult.rejectValue("username", "error.user", "Username is already taken");
                return "signin";
            }
            if (!bindingResult.hasErrors()) {
                userService.saveNew(user);
            }
        }

        request.login(user.getUsername(), password);
        return "redirect:/";
    }
}
