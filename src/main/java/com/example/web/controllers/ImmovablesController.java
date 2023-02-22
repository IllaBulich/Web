package com.example.web.controllers;

import com.example.web.models.Post;
import com.example.web.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
