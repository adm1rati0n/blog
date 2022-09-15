package com.example.demo.controllers;

import com.example.demo.Repo.PostRepository;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/add")
    public String blogAdd(Post post, Model model){
        return "blog-add";
    }

    @PostMapping("/add")
    public String blogAdd(
            @ModelAttribute("post")
            @Valid Post post,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "blog-add";
        }
        postRepository.save(post);

        return "redirect:";
    }

    @GetMapping("/{id}")
    public String blogDetails(@PathVariable(value = "id") long id,Model model)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id))
        {
            return "redirect:";
        }
        return "blog-details";
    }

    @GetMapping("/{id}/edit")
    public String blogEdit(@PathVariable("id")long id,
                           Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:";
        }
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            return "redirect:/blog";
        }
        model.addAttribute("post", post.get());
        return "blog-edit";
    }

    @PostMapping("/{id}/edit")
    public String blogPostUpdate(@PathVariable("id")long id,
                                 @ModelAttribute("post")
                                 @Valid Post post,
                                 BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "blog-add";
        }
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/filter")
    public String blogFilter(Model model) {return "blog-filter";}

    @PostMapping("/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> result = postRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "blog-filter";
    }

    @PostMapping("/{id}/remove")
    public String blogBlogDelete(@PathVariable("id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
