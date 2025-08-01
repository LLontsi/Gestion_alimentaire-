package com.univyaounde.foodmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        System.out.println("🔍 Controller home() appelé");
        model.addAttribute("titre", "Système de Gestion des Aliments");
        model.addAttribute("message", "Bienvenue ! Thymeleaf fonctionne !");
        System.out.println("🔍 Retour du template: index");
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        System.out.println("🔍 Controller dashboard() appelé");
        model.addAttribute("titre", "Tableau de Bord");
        System.out.println("🔍 Retour du template: dashboard");
        return "dashboard";
    }
}