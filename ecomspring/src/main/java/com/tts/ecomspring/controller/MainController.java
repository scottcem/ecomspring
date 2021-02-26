package com.tts.ecomspring.controller;

import com.tts.ecomspring.model.Product;
import com.tts.ecomspring.service.ProductService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.List;

@Data
@Controller
@ControllerAdvice

public class MainController {

    @Autowired
    ProductService productService;

    @GetMapping(value="/")
    public String main(){
        return "main";

    }

    @ModelAttribute("products")
    public List<Product> products(){
        return productService.findAll();
    }

    @ModelAttribute("categories")
    public List<String> categories() {
        return productService.findDistinctCategory();
    }

    @ModelAttribute("brands")
    public List<String> brands(){

        return productService.findDistinctBrands();
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false)
                                     String category, @RequestParam(required = false) String brand,
                         Model model) {
        List<Product> filtered =
                productService.findByBrandAndOrCategory(brand, category);

        model.addAttribute("products", filtered);
        // Overrides the @ModelAttribute above.
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";

    }

}
