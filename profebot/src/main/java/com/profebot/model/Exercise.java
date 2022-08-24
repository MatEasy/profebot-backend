package com.profebot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.profebot.enums.ExcerciseTag;

public class Exercise {
    public String exercise;
    public ExcerciseTag exerciseTag;

    @JsonCreator
    public Exercise(@JsonProperty("exercise") String exercise, @JsonProperty("exerciseTag") ExcerciseTag exerciseTag) {
        this.exercise = exercise;
        this.exerciseTag = exerciseTag;
    }

    public String getExercise() {
        return exercise;
    }

    public ExcerciseTag getExerciseTag() {
        return exerciseTag;
    }
}
