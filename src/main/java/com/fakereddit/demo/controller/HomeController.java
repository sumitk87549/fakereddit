package com.fakereddit.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    @GetMapping
    public String pageLanding(){
        return "Welcome to the landing zone of fake reddit APIs....\n   Here's a kuala for you   \n\n\t\t@( * O * )@";
    }
}
