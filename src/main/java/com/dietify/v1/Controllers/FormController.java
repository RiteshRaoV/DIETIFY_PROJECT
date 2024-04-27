package com.dietify.v1.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dietify.v1.DTO.Formdata;

@Controller
public class FormController {

    @GetMapping("/home")
    public String landingpage() {
        return "home";
    }

    @GetMapping("/formpage")
    public String formpage(Model model) {
        model.addAttribute("formdata", new Formdata());
        return "surveyForm";
    }
}