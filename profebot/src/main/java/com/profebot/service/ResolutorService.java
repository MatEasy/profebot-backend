package com.profebot.service;

import com.profebot.enums.ExcerciseTag;
import com.profebot.exception.InvalidExpressionException;
import com.profebot.model.Exercise;
import com.profebot.model.Step;
import com.profebot.resolutor.EquationResolutor;
import com.profebot.resolutor.FunctionResolutor;
import com.profebot.resolutor.IResolutor;
import com.profebot.resolutor.PolynomialResolutor;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResolutorService {
  HashMap<ExcerciseTag, IResolutor> strategies = new HashMap<>();

  public ResolutorService() {
    strategies.put(ExcerciseTag.Equation, new EquationResolutor(new SimplifyService(), new JustificationsService(), new ExpressionsService()));
    strategies.put(ExcerciseTag.Function, new FunctionResolutor(new SimplifyService()));
    strategies.put(ExcerciseTag.Polynomial, new PolynomialResolutor());
  }

  public List<Step> ExerciseResolution(Exercise excercise) {
    var exerciseTag = excercise.getExerciseTag();

    var resolutor = strategies.get(exerciseTag);

    return resolutor.SolveExercise(excercise.getExercise());
  }
}
