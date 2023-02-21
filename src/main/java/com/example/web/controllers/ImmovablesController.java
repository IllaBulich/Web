package com.example.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImmovablesController {

    @GetMapping("/immovables")
    public String immovablesMain(Model  module){
        return "immovables-main";
    }

}
