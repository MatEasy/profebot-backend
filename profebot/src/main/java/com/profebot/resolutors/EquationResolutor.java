package com.profebot.resolutors;

import com.profebot.model.Step;

import java.util.ArrayList;
import java.util.List;

public class EquationResolutor implements IResolutor{
    public List<Step> SolveExercise(String exercise){
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
