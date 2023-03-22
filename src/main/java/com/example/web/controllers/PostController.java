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
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post")
    public String postMain(Model  module){

        Iterable<Post> posts = postRepository.findAll();
        module.addAttribute("posts",posts);
        return "post/post-main";
    }
    @GetMapping("/post/add")
    public String postAdd(Model  module){
        return "post/post-add";
    }

    @PostMapping("/post/add")
    public String postPostAdd(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model){
        Post post = new Post(title,anons,full_text);
        postRepository.save(post);
        return "redirect:/post";
    }

    @GetMapping("/post/{id}")
    public String postDetails(
            @PathVariable(value = "id") long id,
            Model  model){

        if (!postRepository.existsById(id))
            return "redirect:/post";

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res =new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "post/post-details";
    }
    @GetMapping("/post/{id}/edit")
    public String postEdit(
            @PathVariable(value = "id") long id,
            Model  model){

        if (!postRepository.existsById(id))
            return "redirect:/post";

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res =new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "post/post-edit";
    }

    @PostMapping("/post/{id}/edit")
    public String postPostUpdate(
            @PathVariable(value = "id") long id,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/post";
    }

    @PostMapping("/post/{id}/remove")
    public String postPostDelete(
            @PathVariable(value = "id") long id,
                        Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/post";
    }

}
