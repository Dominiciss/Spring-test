package com.example.demo.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entities.Image;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.ImageException;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Services.ImageService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalController {
    @Autowired
    private UserRepo ur;
    @Autowired
    private ImageService is;

    @GetMapping("/")
    public String index(HttpSession session, ModelMap modelo) {
        User user = (User) session.getAttribute("usersession");

        if (user != null) {
            modelo.put("usersession", user);
        }

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
    public String registro(@RequestParam String email, @RequestParam String password,
            @RequestParam MultipartFile file) {
        User user = new User();

        for (User u : ur.findAll()) {
            if (u.getEmail().equals(email)) {
                return "redirect:/registrar?error=email%20ya%20utilizado";
            }
        }

        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        if (!file.isEmpty()) {
            try {
                Image image = is.create(file.getName(), file.getContentType(), file.getBytes());

                user.setImage(image);
            } catch (ImageException e) {
                return "redirect:/registrar";
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        user = ur.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}
