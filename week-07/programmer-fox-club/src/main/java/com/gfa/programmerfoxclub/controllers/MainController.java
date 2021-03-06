package com.gfa.programmerfoxclub.controllers;

import com.gfa.programmerfoxclub.models.Drink;
import com.gfa.programmerfoxclub.models.Food;
import com.gfa.programmerfoxclub.models.Fox;
import com.gfa.programmerfoxclub.models.Trick;
import com.gfa.programmerfoxclub.services.FoxService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    private final FoxService foxService;
    private Fox currentFox;

    public MainController(FoxService foxService) {
        this.foxService = foxService;
    }


    @GetMapping(value = {"/"})
    public String show (Model m){
        if (currentFox==null) return  "redirect:login";
        m.addAttribute("fox",currentFox);
        m.addAttribute("foxList",foxService.foxList());
        return "index";
    }

    @GetMapping(value = {"/login"})
    public String loginForm(@RequestParam(required = false) String name,Model m){
        if (name!=null && foxService.getFoxByName(name)!=null){
            this.currentFox = foxService.getFoxByName(name);
            return "redirect:/";
        }
        m.addAttribute("foxList",foxService.foxList());
        return "login";
    }

    @PostMapping(value = {"/login"})
    public String loginFormHandling(@RequestParam("name") String name, Model m){
        if (foxService.getFoxByName(name)==null){
            foxService.addFoxName(name);
        }
        currentFox=foxService.getFoxByName(name);
        return "redirect:/";
    }

    @GetMapping(value = {"/nutrition-store"})
    public String nutrition(Model m){
        if (currentFox==null) return  "redirect:login";
        m.addAttribute("fox",currentFox);
        m.addAttribute("foodList", foxService.foodList());
        m.addAttribute("drinkList", foxService.drinkList());
        return "nutrition-store";

    }

    @PostMapping(value = {"/nutrition-store"})
    public String nutritionHandled(
            @RequestParam(name = "foodId") int foodId,
            @RequestParam(name = "drinkId") int drinkId,
            @RequestParam(name = "foxId") int foxId,
            Model m
            ){

        if (drinkId!=this.currentFox.getDrink().getId()) {
            this.foxService.chaneFoxDrink(foxId, drinkId);
            this.currentFox.addAction("Drink changed to " + this.currentFox.getDrink().getName()+".");
        }
        if (foodId!=this.currentFox.getFood().getId()) {
            this.foxService.chaneFoxFood(foxId, foodId);
            this.currentFox.addAction("Food changed to " + this.currentFox.getFood().getName()+".");
        }
        return "redirect:/";
    }
    @PostMapping(value = {"/nutrition-store-drink"})
    public String drinkAdded(
            @RequestParam(name = "drinkName") String drinkName,
            Model m
    ){
        this.foxService.drinkList().add(new Drink(drinkName));
        return "redirect:/";
    }
    @PostMapping(value = {"/nutrition-store-food"})
    public String foodAdded(
            @RequestParam(name = "foodName") String foodName,
            Model m
    ){
        this.foxService.foodList().add(new Food(foodName));
        return "redirect:/";
    }

    @GetMapping(value = {"/trick-center"})
    public String tricks(Model m){
        if (currentFox==null) return  "redirect:login";
        m.addAttribute("fox",currentFox);
        m.addAttribute("trickList", foxService.allowedTrickList(currentFox));
        return "trick-center";
    }

    @PostMapping(value = {"/trick-center"})
    public String tricksHandled(
            @RequestParam(name = "trickId") int trickId,
            @RequestParam(name = "foxId") int foxId,
            Model m
    ){
        this.foxService.addTrick(foxId,trickId);
        this.currentFox.addAction("New trick "+this.foxService.getTrickById(trickId).getName()+" added.");

        return "redirect:/";
    }

    @PostMapping(value = {"/trick-center-add"})
    public String tricksAddedHandled(
            @RequestParam(name = "trickName") String trickName,
            Model m
    ){
        this.foxService.trickList().add(new Trick(trickName));
        return "redirect:/";
    }

    @GetMapping(value = {"/action-history"})
    public String actionHistory(Model m){
        if (currentFox==null) return  "redirect:login";
        m.addAttribute("fox",currentFox);
        return "action-history";
    }
}
