package com.example.demo.controllers;

import com.example.demo.Repo.AuthRepository;
import com.example.demo.models.Auth;
import com.example.demo.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AuthController {
    @Autowired
    private AuthRepository authRepository;
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", authRepository.findAll());
        return "userList";
    }

    @GetMapping("/{id}/edit")
    public String authEdit(@PathVariable(value = "id") long id, Model model){
        Optional<Auth> user = authRepository.findById(id);
        ArrayList<Auth> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("roles", Role.values());
        return "authEdit";
    }

    @PostMapping
    public String authSave(@RequestParam String username, @RequestParam(name="roles[]", required = false) String[] roles,
                           @RequestParam("userId") Auth user){
        user.setUsername(username);
        user.getRoles().clear();
        if(roles!=null)
        {
            Arrays.stream(roles).forEach(r->user.getRoles().add(Role.valueOf(r)));
        }
        authRepository.save(user);

        return "redirect:/admin";
    }
}
