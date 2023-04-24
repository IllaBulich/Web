package com.example.web.controllers;

import com.example.web.models.*;
import com.example.web.repo.DetailsRepository;
import com.example.web.repo.RentalRepository;
import com.example.web.services.ImmovablesService;
import com.example.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;


@Controller
@RequiredArgsConstructor
public class ImmovablesController {
    private final ImmovablesService immovablesService;
    private final UserService userService;

    private  final RentalRepository rentalRepository;


    @GetMapping("/")
    public String home(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "min", required = false) Integer min,
            @RequestParam(name = "max", required = false) Integer max,
            Principal principal,
            Model model
    ) {
        model.addAttribute("immovables", immovablesService.listImmovablesCost(title,min,max));
        model.addAttribute("user",immovablesService.getUserByPrincipal(principal));
        return "home";
    }
    @GetMapping("/immovables/render")
    public String immovablesRender(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "min", required = false) Integer min,
            @RequestParam(name = "max", required = false) Integer max,
            Principal principal,
            Model model
    ){
        model.addAttribute("immovables", immovablesService.listImmovablesCostRender(title,min,max));
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

    @GetMapping("/immovables/{id}/edit")
    public String immovablesEdit(
            @PathVariable(value = "id") long id,
            Principal principal,
            Model  model){
        User user = immovablesService.getUserByPrincipal(principal);

        if(immovablesService.getImmovablesById(id) == null)
            return "redirect:/";
        Immovables immovables = immovablesService.getImmovablesById(id);
        if(immovables.getUser() != user)
            return "redirect:/";
        model.addAttribute("images",immovables.getImages());
        model.addAttribute("immovables",immovablesService.getImmovablesById(id));
        return "immovables-edit";
    }

    @PostMapping("/immovables/{id}/edit")
    public String postPostUpdate(
            @PathVariable(value = "id") long id,
            @PathVariable("files") MultipartFile[] files,
            Immovables immovables,
            Details details,
            Principal principal ) throws IOException {
        immovablesService.editImmovables(id, principal,details, immovables,files);
        return "redirect:/";
    }

    @GetMapping("/immovables/details/{id}")
    public String immovablesDetails(
            @PathVariable(value = "id") long id,
            Principal principal,
            Model  model){
        if(immovablesService.getImmovablesById(id) == null)
            return "redirect:/";
        User user = immovablesService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        Immovables immovables = immovablesService.getImmovablesById(id);
        model.addAttribute("images",immovables.getImages());
        model.addAttribute("immovables",immovablesService.getImmovablesById(id));
        model.addAttribute("details",immovables.getDetails());

        return "immovables-details";
    }


    @PostMapping("/immovables/rent")
    public String rentImmovable(Principal principal,
                                @RequestParam Integer immovable_id,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate rent_start_date,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate rent_end_date) {
        // получение пользователя и недвижимости из БД
        User user = immovablesService.getUserByPrincipal(principal);
        Immovables immovable = immovablesService.getImmovablesById(Long.valueOf(immovable_id));

        // создание объекта аренды
        Rental rental = new Rental();
        rental.setStartDate(rent_start_date);
        rental.setEndDate(rent_end_date);
        rental.setUser(user);
        rental.setImmovable(immovable);

        // сохранение объекта аренды в БД
        rentalRepository.save(rental);

        // возврат успешного ответа
        return "redirect:/";
    }
    @PostMapping("/immovables/{id}/purchase")
    public String postUserPurchaseImmovables(
            @PathVariable(value = "id") long id,
            Principal principal,
            Model model){
        immovablesService.purchaseImmovables(id,principal);
        return "redirect:/";
    }



    @PostMapping("/immovables/{id}/remove")
    public String postImmovablesDelete(
            @PathVariable(value = "id") long id,
            Model model){
        immovablesService.deleteImmovables(id);
        return "redirect:/";
    }




}