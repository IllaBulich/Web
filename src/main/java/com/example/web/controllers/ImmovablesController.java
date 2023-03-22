package com.example.web.controllers;

import com.example.web.models.Immovables;
import com.example.web.models.Post;
import com.example.web.services.ImmovablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ImmovablesController {
    private final ImmovablesService immovablesService;

    @GetMapping("/")
    public String home(@RequestParam(name = "title", required = false) String title, Model model) {
        model.addAttribute("immovables", immovablesService.listImmovables(title));
        return "home";
    }

    @GetMapping("/immovables/add")
    public String immovablesAdd(Model  module){
        return "immovables-add";
    }

    @PostMapping("/immovables/add")
    public String postImmovablesAdd(Immovables immovables ){
        immovablesService.seveImmovables(immovables);
        return "redirect:/";
    }
    @GetMapping("/immovables/{id}")
    public String immovablesDetails(
            @PathVariable(value = "id") long id,
            Model  model){

        model.addAttribute("immovables",immovablesService.getImmovablesById(id));
        return "immovables-details";
    }


    @PostMapping("/immovables/{id}/remove")
    public String postImmovablesDelete(
            @PathVariable(value = "id") long id,
            Model model){
        immovablesService.deleteImmovables(id);
        return "redirect:/";
    }


    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("titel", "Главная");
        return "about";
    }

}