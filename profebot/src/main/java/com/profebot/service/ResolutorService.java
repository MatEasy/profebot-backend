package com.profebot.service;

import com.profebot.enums.ExcerciseTag;
import com.profebot.model.Exercise;
import com.profebot.model.Step;
import com.profebot.resolutors.EquationResolutor;
import com.profebot.resolutors.FunctionResolutor;
import com.profebot.resolutors.IResolutor;
import com.profebot.resolutors.PolynomialResolutor;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResolutorService {
  HashMap<ExcerciseTag, IResolutor> strategies = new HashMap<>();

  public ResolutorService() {
    strategies.put(ExcerciseTag.Equation, new EquationResolutor(new SimplifyService()));
    strategies.put(ExcerciseTag.Function, new FunctionResolutor(new SimplifyService()));
    strategies.put(ExcerciseTag.Polynomial, new PolynomialResolutor());
  }

  public List<Step> ExerciseResolution(Exercise excercise) {
    var exerciseTag = excercise.getExerciseTag();

    var resolutor = strategies.get(exerciseTag);

    return resolutor.SolveExercise(excercise.getExercise());
  }
}
