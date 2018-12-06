package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccueilController {
    
    @RequestMapping("/")
    public String accueil() {
        
        System.out.println("test");
        
        return "redirect:/swagger-ui.html";
    }
}
