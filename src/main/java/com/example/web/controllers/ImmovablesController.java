package com.example.web.controllers;

import com.example.web.models.Details;
import com.example.web.models.Immovables;
import com.example.web.repo.DetailsRepository;
import com.example.web.services.ImmovablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class ImmovablesController {
    private final ImmovablesService immovablesService;
    private final DetailsRepository detailsRepository;


    @GetMapping("/")
    public String home(@RequestParam(name = "title", required = false) String title,Principal principal, Model model) {
        model.addAttribute("immovables", immovablesService.listImmovables(title));
        model.addAttribute("user",immovablesService.getUserByPrincipal(principal));
        return "home";
    }

    @GetMapping("/immovables/add")
    public String immovablesAdd(Model  module){
        return "immovables-add";
    }

    @PostMapping("/immovables/add")
    public String postImmovablesAdd(
            @PathVariable("files") MultipartFile[] files,

            Immovables immovables, Details details,
            Principal principal) throws IOException {
        immovablesService.saveImmovables(principal,details, immovables,files);
        return "redirect:/";
    }
    @GetMapping("/immovables/details/{id}")
    public String immovablesDetails(
            @PathVariable(value = "id") long id,
            Model  model){
        if(immovablesService.getImmovablesById(id) == null)
            return "redirect:/";
        Immovables immovables = immovablesService.getImmovablesById(id);
        model.addAttribute("images",immovables.getImages());
        model.addAttribute("immovables",immovablesService.getImmovablesById(id));
        model.addAttribute("details",immovables.getDetails());

        return "immovables-details";
    }

    @GetMapping("/immovables/{id}/edit")
    public String immovablesEdit(
            @PathVariable(value = "id") long id,
            Model  model){

        if(immovablesService.getImmovablesById(id) == null)
            return "redirect:/";

        model.addAttribute("immovables",immovablesService.getImmovablesById(id));
        return "immovables-edit";
    }

//    @PostMapping("/immovables/{id}/edit")
//    public String postPostUpdate(
//            @PathVariable(value = "id") long id,
//            Immovables immovables ){
//        immovablesService.saveImmovables(immovables);
//        return "redirect:/";
//    }




    @PostMapping("/immovables/{id}/remove")
    public String postImmovablesDelete(
            @PathVariable(value = "id") long id,
            Model model){
        immovablesService.deleteImmovables(id);
        return "redirect:/";
    }




}