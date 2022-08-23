package com.profebot.resolutors;

import com.profebot.model.Step;

import java.util.List;

public interface IResolutor {
    List<Step> SolveExercise(String exercise);
}
