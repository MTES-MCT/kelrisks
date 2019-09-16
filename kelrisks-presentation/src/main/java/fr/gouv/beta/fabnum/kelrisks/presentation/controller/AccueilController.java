package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccueilController {
    
    @RequestMapping("/")
    public String accueil() {
        
        return "redirect:/swagger-ui.html";
    }
    
    @RequestMapping("/sanity_test")
    @ResponseBody
    public String greeting() {
        
        return "Hello World";
    }
}
