package com.fakereddit.demo.controller;

import com.fakereddit.demo.dto.RegisterRequest;
import com.fakereddit.demo.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
