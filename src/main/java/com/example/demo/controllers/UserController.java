package com.example.demo.controllers;

import com.example.demo.Repo.UserRepository;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/blog/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String usersMain(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user-main";
    }

    @GetMapping("/add")
    public String userAdd(User user, Model model) {
        return "user-add";
    }

    @PostMapping("/add")
    public String userAdd(
            @ModelAttribute("user")
            @Valid User user,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "user-add";
        }
        user.setFollowers(0);
        userRepository.save(user);
        return "redirect:/blog/users";
    }
    @GetMapping("/filter")
    public String userFilter(Model model) {return "user-filter";}

    @PostMapping("/filter/result")
    public String userResult(@RequestParam String surname, Model model)
    {
        List<User> result = userRepository.findBySurnameContains(surname);
        model.addAttribute("result", result);
        return "user-filter";
    }
    @PostMapping("/{id}/delete")
    public String userDelete(@PathVariable("id") long id, Model model){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/blog/users";
    }
    @PostMapping("/{id}/edit")
    public String userUpdate(@PathVariable("id")long id,
                             @ModelAttribute("user")
                             @Valid User user,
                             BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "user-add";
        }
        user.setFollowers(0);
        userRepository.save(user);
        return "redirect:/blog/users";
    }

    @GetMapping("/{id}/edit")
    public String userEdit(@PathVariable("id")long id,
                           Model model)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return "redirect:/blog/users";
        }
        model.addAttribute("user", user.get());
        return "user-edit";
    }
    @GetMapping("/{id}")
    public String userDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("users", res);
        if(!userRepository.existsById(id))
        {
            return "redirect:/blog/users";
        }
        return "user-details";
    }

}
