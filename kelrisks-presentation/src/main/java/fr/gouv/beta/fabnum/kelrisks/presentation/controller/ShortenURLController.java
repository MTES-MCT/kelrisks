package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShortenURLController {
    
    @RequestMapping("/avis/{shortUrl}")
    public String shortUrlRedirect(@PathVariable("shortUrl") String shortUrl) {
        
        String[] params = shortUrl.split("\\|");
        
        return "redirect:";
    }
}
