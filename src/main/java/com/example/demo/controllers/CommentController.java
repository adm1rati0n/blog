package com.example.demo.controllers;

import com.example.demo.Repo.CommentRepository;
import com.example.demo.Repo.UserRepository;
import com.example.demo.models.Comment;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blog/comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public String commentsMain(Model model){
        Iterable<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        return "comment-main";
    }

    @GetMapping("/add")
    public String commentAdd(Model model, Comment comment){
        return "comment-add";
    }

    @PostMapping("/add")
    public String commentAdd(
            @ModelAttribute("comment")
            @Valid Comment comment,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "comment-add";
        }
        comment.setDate(new Date());
        commentRepository.save(comment);
        return "redirect:/blog/comments";
    }
    @GetMapping("/filter")
    public String commentFilter(Model model) {return "comment-filter";}

    @PostMapping("/filter/result")
    public String commentResult(@RequestParam String fullText, Model model)
    {
        List<Comment> result = commentRepository.findByFullTextContains(fullText);
        model.addAttribute("result", result);
        return "comment-filter";
    }

    @PostMapping("/{id}/delete")
    public String commentDelete(@PathVariable("id") long id, Model model){
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);
        return "redirect:/blog/comments";
    }
    @PostMapping("/{id}/edit")
    public String commentUpdate(@PathVariable("id")long id,
                                @ModelAttribute("comment")
                                @Valid Comment comment,
                                BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "comment-edit";
        }
        commentRepository.save(comment);
        return "redirect:/blog/comments";
    }

    @GetMapping("/{id}/edit")
    public String commentEdit(@PathVariable("id")long id,
                           Model model)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            return "redirect:/blog/comments";
        }
        model.addAttribute("comment", comment.get());
        return "comment-edit";
    }
    @GetMapping("/{id}")
    public String commentDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        ArrayList<Comment> res = new ArrayList<>();
        comment.ifPresent(res::add);
        model.addAttribute("comments", res);
        if(!commentRepository.existsById(id))
        {
            return "redirect:/blog/comments";
        }
        return "comment-details";
    }
}
