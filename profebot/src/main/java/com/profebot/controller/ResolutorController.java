package com.profebot.controller;

import com.profebot.model.Exercise;
import com.profebot.model.MultipleChoiceStep;
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
    public List<MultipleChoiceStep> ExerciseResolution(@RequestBody Exercise excercise){
        List<MultipleChoiceStep> stepList = new ArrayList<>();

        //Mock step
        MultipleChoiceStep step = new MultipleChoiceStep(
                "Igualar ecuacion a dos",
                "x + 1 = 2",
                "Igualar ecuacion a dos",
                "x + 1 = 2",
                "Igualar ecuacion a dos",
                "x + 1 = 2",
                0
                );

        stepList.add(step);

        return stepList;
    }
}
