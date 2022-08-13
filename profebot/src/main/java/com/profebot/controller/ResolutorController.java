package com.profebot.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResolutorController {

    public ResolutorController() {
    }

    @GetMapping("/")
    public String helloworld(){
        return "Hello World!";
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
