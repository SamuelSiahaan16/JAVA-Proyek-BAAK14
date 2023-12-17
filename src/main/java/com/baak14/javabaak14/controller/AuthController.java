package com.baak14.javabaak14.controller;

import com.baak14.javabaak14.enums.UserStatus;
import com.baak14.javabaak14.model.User;
import com.baak14.javabaak14.service.UserService;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView; 

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session) {
        User oauthUser = userService.login(user.getUsername(), user.getPassword());

        if (Objects.nonNull(oauthUser)) {
            session.setAttribute("userId", oauthUser.getId()); // Store user ID in the session
            session.setAttribute("username", oauthUser.getUsername());
            session.setAttribute("email", oauthUser.getEmail());
            session.setAttribute("role", oauthUser.getRole());

            if (oauthUser.getRole() == UserStatus.admin) {
                return "redirect:/admin";
            } else if (oauthUser.getRole() == UserStatus.mahasiswa) {
                return "redirect:/user";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/register") 
    public ModelAndView register() {
    	ModelAndView mav = new ModelAndView("auth/register");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        User existingUser = userService.getUserByUsername(user.getUsername());

        if (existingUser == null) {
            userService.register(user.getUsername(), user.getPassword(), UserStatus.mahasiswa, user.getNoKtp(), user.getEmail(), user.getNim(), user.getNama());
            return "redirect:/";
        } else {
            return "redirect:/register";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutDo(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }


}
