package com.profebot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Exercise {
    @JsonProperty("exercise")
    private String exercise;

    @JsonCreator
    public Exercise(@JsonProperty("exercise") String exercise) {
        this.exercise = exercise;
    }

    @JsonProperty("exercise")
    public String getExercise() {
        return exercise;
    }
}
