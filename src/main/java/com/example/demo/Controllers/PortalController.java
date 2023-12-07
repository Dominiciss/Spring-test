package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entities.User;
import com.example.demo.Repo.UserRepo;


@Controller
@RequestMapping("/")
public class PortalController {
    @Autowired
    private UserRepo ur;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", error);
        }

        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registro(@RequestParam String email, @RequestParam String password) {
        User user = new User();

        for (User u : ur.findAll()) {
            if (u.getEmail().equals(email)) {
                return "redirect:/registrar?error=email%20ya%20utilizado";
            }
        }
        
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        user = ur.save(user);

        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}
