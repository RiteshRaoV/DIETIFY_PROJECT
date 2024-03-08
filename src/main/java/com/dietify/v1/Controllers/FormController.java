package com.dietify.v1.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dietify.v1.DTO.Formdata;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class FormController {

@GetMapping("/landing")
public String landingpage() {
    return "landing";
}

    

    @GetMapping("/formpage")
    public String formpage(Model model) {
        model.addAttribute("formdata", new Formdata());
        return "surveyform";
    } 
}