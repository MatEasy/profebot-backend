package com.profebot.controller;

import com.profebot.model.Exercise;
import com.profebot.model.Step;
import com.profebot.service.ResolutorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResolutorController {
    private ResolutorService resolutorService;

    public ResolutorController(ResolutorService resolutorService) {
        this.resolutorService = resolutorService;
    }

    @GetMapping("/")
    public String HelloWorld(){
        return "Hello World!";
    }

    @PostMapping("/api/exercise-resolution")
    public List<Step> ExerciseResolution(@RequestBody Exercise excercise){
        return resolutorService.ExerciseResolution(excercise);
    }
}
