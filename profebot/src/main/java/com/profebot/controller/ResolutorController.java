package com.profebot.controller;

import com.profebot.model.Exercise;
import com.profebot.model.Step;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ResolutorController {

    public ResolutorController() {
    }

    @GetMapping("/")
    public String HelloWorld(){
        return "Hello World!";
    }

    @PostMapping("/api/exercise-resolution")
    public List<Step> ExerciseResolution(@RequestBody Exercise excercise){
        List<Step> stepList = new ArrayList<>();

        //Mock step
        Step step = new Step(
                "Igualar ecuacion a dos",
                "x + 1 = 2"
                );

        stepList.add(step);

        return stepList;
    }
}
