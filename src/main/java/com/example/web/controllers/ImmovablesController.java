package com.example.web.controllers;

import com.example.web.models.Post;
import com.example.web.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImmovablesController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/immovables")
    public String immovablesMain(Model  module){

        Iterable<Post> posts = postRepository.findAll();
        module.addAttribute("posts",posts);
        return "immovables-main";
    }
    @GetMapping("/immovables/add")
    public String immovablesAdd(Model  module){
        return "immovables-add";
    }

    @PostMapping("/immovables/add")
    public String immovablesPostAdd(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model){
        Post post = new Post(title,anons,full_text);
        postRepository.save(post);
        return "redirect:/immovables";
    }

}
