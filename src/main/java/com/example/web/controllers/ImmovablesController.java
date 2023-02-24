package com.example.web.controllers;

import com.example.web.models.Post;
import com.example.web.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/immovables/{id}")
    public String immovablesDetails(
            @PathVariable(value = "id") long id,
            Model  model){

        if (!postRepository.existsById(id))
            return "redirect:/immovables";

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res =new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "immovables-details";
    }

}
