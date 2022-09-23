package com.profebot.resolutors;

import com.profebot.enums.EquationOptionType;
import com.profebot.model.EquationOption;
import com.profebot.model.Step;
import com.profebot.parser.Parser;
import com.profebot.service.ExpressionsService;
import com.profebot.service.JustificationsService;
import com.profebot.service.SimplifyService;
import com.profebot.structures.parserStructures.Tree;
import com.profebot.structures.parserStructures.TreeNode;
import com.profebot.structures.resolutorStructures.EquationStatus;
import com.profebot.structures.resolutorStructures.NodeStatus;
import com.profebot.utils.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.profebot.enums.ContextOfResolution.*;

public class EquationResolutor implements IResolutor{
    SimplifyService simplifyService;
    JustificationsService justificationsService;
    ExpressionsService expressionsService;

    @Autowired
    public EquationResolutor(SimplifyService simplifyService, JustificationsService justificationsService, ExpressionsService expressionsService) {
        this.simplifyService = simplifyService;
        this.justificationsService = justificationsService;
        this.expressionsService = expressionsService;
    }

    public List<Step> SolveExercise(String exercise) {
        var tree = getTree(exercise);

        List<EquationStatus> steps = stepThrough(tree, false);

        List<Step> result = new ArrayList<>();

        for(EquationStatus e: steps){
            // Te da la ecuacion del paso actual
            String newEquationBase = e.getNewEquation().toExpression();

            List<EquationOption> equationOptions = new ArrayList<>();
            equationOptions.add(new EquationOption(newEquationBase, EquationOptionType.LATEX));

            String comparatorOperator = ExpressionsService.getRootOfEquation(newEquationBase);

            Map<String, String> justifications = JustificationsService.getCorrectJustificationsFrom(e.getChangeType(), comparatorOperator);

            // Justificación general
            String option =  justifications.get("option");

            // Justificacion más detallada
            String correctOptionJustification = justifications.get("correctOptionJustification");

            // Justificación resumida
            String summary = justifications.get("summary");

            Step multipleChoiceStep = new Step(option, equationOptions, correctOptionJustification);
            result.add(multipleChoiceStep);
        }

        //Conclusion
        String lastEquation = steps.get(steps.size() - 1).getNewEquation().toExpression();
        String comparatorOperator = ExpressionsService.getRootOfEquation(lastEquation);
        String[] members = lastEquation.split(comparatorOperator);
        String contextOfResolution = "";

        // Equation
        if(!"I".equals(members[0]) && comparatorOperator.equals("=")){
            if(members[0].equals(members[1])){
                contextOfResolution = CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_INFINITE_SOLUTIONS;
            }else if(members[0].toUpperCase().equals("X")){
                contextOfResolution = CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_FINITE_SOLUTIONS;
            }else{
                try{
                    Double.parseDouble(members[0]);
                    Double.parseDouble(members[1]);
                    contextOfResolution = CONTEXT_OF_RESOLUTION_IS_EQUATION_WITHOUT_SOLUTIONS;
                }catch (Exception e){
                    try{
                        Double.parseDouble(members[1]);
                        if(members[0].toUpperCase().contains("X")){
                            contextOfResolution = CONTEXT_OF_RESOLUTION_IS_EQUATION_WITHOUT_SOLUTIONS;
                        }
                    }catch (Exception e2){
                        // Raíces múltiples
                        contextOfResolution = CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_FINITE_SOLUTIONS;
                    }
                }
            }
        }else{ // Inequation
            if(members[0].equals(members[1])){
                if(lastEquation.contains("=")){
                    // Ejemplo: 2 >= 2. En este caso, no hay inecuación (el > nunca se cumple)
                    contextOfResolution = CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_INFINITE_SOLUTIONS;
                }else{
                    // Ejemplo: 2 > 2. En este caso, no hay inecuación (el > nunca se cumple). Se podría decir que es una ecuación con 0 soluciones
                    contextOfResolution = CONTEXT_OF_RESOLUTION_IS_EQUATION_WITHOUT_SOLUTIONS;
                }
            }else if(members[1].equals("VACIO")){
                contextOfResolution = CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITHOUT_SOLUTIONS;
            }else{
                contextOfResolution = CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITH_INTERVAL_SOLUTIONS;
            }
        }

        Map<String, String> contextOfResolutionTexts = JustificationsService.getContextOfResolutionTexts(contextOfResolution);

        comparatorOperator = ExpressionsService.getRootOfEquation(lastEquation);
        switch (contextOfResolution){
            case CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_FINITE_SOLUTIONS:
                contextOfResolutionTexts = JustificationsService.fixResolutionTextsForRoots(contextOfResolutionTexts, lastEquation);
                break;
            case CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITH_INTERVAL_SOLUTIONS:
                members = lastEquation.split(comparatorOperator);
                try{
                    Double valDer = Double.parseDouble(members[1]);
                    StringBuilder interval = new StringBuilder("");

                    try{
                        // Si existe valor izquierdo, es una inecuación del estilo 2>1
                        Double valIzq = Double.parseDouble(members[0]);
                        if(valIzq.equals(valDer)){
                            if(comparatorOperator.contains("=")){
                                interval.append("{ ");
                                interval.append(valDer);
                                interval.append(" }");
                            }
                        }else{
                            if(comparatorOperator.contains(">")){
                                if(valIzq >= valDer){
                                    interval.append("(-∞, ∞)");
                                }else{
                                    contextOfResolutionTexts = JustificationsService.getContextOfResolutionTexts(CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITHOUT_SOLUTIONS);
                                }
                            }else if(comparatorOperator.contains("<")){
                                if(valIzq <= valDer){
                                    interval.append("(-∞, ∞)");
                                }else{
                                    contextOfResolutionTexts = JustificationsService.getContextOfResolutionTexts(CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITHOUT_SOLUTIONS);
                                }
                            }
                        }
                        contextOfResolutionTexts = JustificationsService.replacePatterns(contextOfResolutionTexts, "second", "/intervalos/", interval.toString());
                    }catch (Exception e2){
                        // En este caso, el miembro izquierdo no es numérico

                        // Cuando el intervalo es un número. Ejemplo: x^2 <= 0 --> solo el 0 cumple
                        if("=".equals(comparatorOperator)){
                            interval.append("{ ");
                            interval.append(valDer);
                            interval.append(" }");
                        }else{
                            // Generar el intervalo solución cuando se tiene una cuadrática sin raíces
                            interval.append(ExpressionsService.getIntervalFrom(lastEquation));
                        }
                        if(interval.toString().isEmpty()){
                            contextOfResolutionTexts = JustificationsService.getContextOfResolutionTexts(CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITHOUT_SOLUTIONS);
                        }
                        contextOfResolutionTexts = JustificationsService.replacePatterns(contextOfResolutionTexts, "second", "/intervalos/", interval.toString());
                    }
                }catch (Exception e){
                    // En este caos, tengo un intervalo solución, ejemplo: I=(3, +INF)
                    String interval = members[1].replace("INF", "∞");
                    contextOfResolutionTexts = JustificationsService.replacePatterns(contextOfResolutionTexts, "second", "/intervalos/", interval);
                }
                break;
            case CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITHOUT_SOLUTIONS:
                contextOfResolutionTexts = JustificationsService.replacePatterns(contextOfResolutionTexts, "second", "/intervalos/", "");
                break;
        }

        List<EquationOption> equationOptions = new ArrayList<>();
        equationOptions.add(new EquationOption(contextOfResolutionTexts.get("first"), EquationOptionType.TEXT));
        EquationStatus originalEquation = steps.get(0);
        equationOptions.add(new EquationOption(originalEquation.getOldEquation().toExpression(), EquationOptionType.LATEX));
        equationOptions.add(new EquationOption(contextOfResolutionTexts.get("second"), EquationOptionType.TEXT));
        Step conclusion = new Step("Conclusión:", equationOptions, null);
        result.add(conclusion);

        return result;
    }

    private Tree getTree(String exercise) {
        Tree tree = new Tree();

        try {
            tree = new Parser().parseExpression(exercise);
        } catch(Exception e){

        }

        return tree;
    }

    protected List<EquationStatus> stepThrough(Tree equation, Boolean debug){

        List<EquationStatus> steps = new ArrayList<>();

        if (debug) {
            // eslint-disable-next-line
            System.out.println("\n\nResolviendo: " + equation.toExpression());
        }

        if (!equation.hasAnySymbol()) {
            return solveConstantEquation(equation, debug);
        }

        EquationStatus equationStatus;

        String originalEquationStr = equation.toExpression();
        final Integer MAX_STEP_COUNT = 20;
        int iters = 0;

        // Remove unnecessary parentheses here, before doing the find roots check
        // If we have roots, we return early and do not go through simplification,
        // so we can't rely on that flow for parentheses removal
        // e.g. x^(2) = 0 -> x^2 = 0
        equation.setLeftNode(TreeUtils.removeUnnecessaryParens(equation.getLeftNode()));
        equation.setRightNode(TreeUtils.removeUnnecessaryParens(equation.getRightNode()));

        // Checks if there are roots in the original equation before we
        // do any simplification.
        // E.g. (33 + 89) (x - 99) = 0
        if (TreeUtils.canFindRoots(equation)) {
            steps.add(getRootsStatus(equation));
            return steps;
        }

        // Step through the math equation until nothing changes
        do {
            steps = addSimplificationSteps(steps, equation, debug);

            if (steps.size() > 0) {
                EquationStatus lastStep = steps.get(steps.size() - 1);
                equation = lastStep.getNewEquation().clone();
            }

            equation.setLeftNode(TreeUtils.flattenOperands(equation.getLeftNode()));
            equation.setRightNode(TreeUtils.flattenOperands(equation.getRightNode()));

            // at this point, the symbols might have cancelled out.
            if (!equation.hasAnySymbol()) {
                return solveConstantEquation(equation, debug, steps);
            }

            // The left side of the equation is either factored or simplified.
            // If it is factor and we can find roots, return them.
            // e.g. x^2 + 3x + 2 = 0 -> (x + 1) (x + 2) = 0 -> x = -1
            if (TreeUtils.canFindRoots(equation)) {
                steps.add(getRootsStatus(equation));
                return steps;
            }

            try {
                equationStatus = step(equation);
            }
            catch (Exception e) {
                // This error happens for some math that we don't support
                if (e.getMessage().startsWith("No term with symbol: ")) {
                    // eslint-disable-next-line
                    System.out.println("Math error: " + e.getMessage() + ", returning no steps");
                    return new ArrayList<>();
                }
                else {
                    throw e; // bubble up
                }
            }

            if (equationStatus.hasChanged()) {
                if (equationStatus.getNewEquation().toExpression().length() > 300) {
                    // eslint-disable-next-line
                    throw new Error("Math error: Potential infinite loop for equation " +
                            originalEquationStr +  ". It reached over 300 characters " +
                            " long, so returning no steps");
                }
                if (debug) {
                    logSteps(equationStatus);
                }
                steps.add(equationStatus);
            }

            equation = EquationStatus.resetChangeGroups(equationStatus.getNewEquation());
            if (iters++ == MAX_STEP_COUNT) {
                // eslint-disable-next-line
                System.out.println("Math error: Potential infinite loop for equation: " +
                        originalEquationStr + ", returning no steps");
                return new ArrayList<>();
            }
        } while (equationStatus.hasChanged());


        return steps;
    }

    private List<EquationStatus> solveConstantEquation(Tree equation, Boolean debug) {
        return solveConstantEquation(equation, debug, new ArrayList<EquationStatus>());
    }

    // Given an equation of constants, will simplify both sides, returning
    // the steps and the result of the equation e.g. 'True' or 'False'
    protected List<EquationStatus> solveConstantEquation(Tree equation, Boolean debug, List<EquationStatus> steps) {

        steps = addSimplificationSteps(steps, equation, debug);
        if (steps.size() > 0) {
            EquationStatus lastStep = steps.get(steps.size() - 1);
            equation = lastStep.getNewEquation().clone();
        }

        // If the left or right side didn't have any steps, unnecessary parens
        // might not have been removed, so do that now.
        equation.setLeftNode(TreeUtils.removeUnnecessaryParens(equation.getLeftNode()));
        equation.setRightNode(TreeUtils.removeUnnecessaryParens(equation.getRightNode()));

        if (!TreeUtils.isConstantOrConstantFraction(equation.getLeftNode(), true) ||
                !TreeUtils.isConstantOrConstantFraction(equation.getRightNode(), true)) {
            throw new Error("Expected both nodes to be constants, instead got: " +
                    equation.toExpression());
        }

        Integer leftValue = equation.getLeftNode().getIntegerValue();
        Integer rightValue = equation.getRightNode().getIntegerValue();
        NodeStatus.ChangeTypes changeType;
        if (compareFunction(leftValue, rightValue, equation.getRootNode().getValue())) {
            changeType = NodeStatus.ChangeTypes.STATEMENT_IS_TRUE;
        } else {
            changeType = NodeStatus.ChangeTypes.STATEMENT_IS_FALSE;
        }

        // there's no oldEquation or change groups because nothing actually changes
        // here, it's just a final step that states the solution
        EquationStatus equationStatus = new EquationStatus(changeType, equation, equation);
        if (debug) {
            logSteps(equationStatus);
        }
        steps.add(equationStatus);
        return steps;
    }

    private boolean compareFunction(Integer leftValue, Integer rightValue, String operator) {

        if ("=".equals(operator)){
            return (leftValue.equals(rightValue));
        } else if (">".equals(operator)){
            return (leftValue > rightValue);
        } else if (">=".equals(operator)){
            return (leftValue >= rightValue);
        } else if ("<".equals(operator)){
            return (leftValue < rightValue);
        } else if ("<=".equals(operator)){
            return (leftValue <= rightValue);
        }else{
            throw new Error("Operador inesperado: " + operator);

        }
    }


    // Given a symbol (in this case, always X) and an equation, performs a single step to
    // solve for the symbol. Returns an Status object.
    protected EquationStatus step(Tree equation) {

        // ensure the symbol is always on the left node
        EquationStatus equationStatus = ensureSymbolInLeftNode(equation);
        if (equationStatus.hasChanged()) {
            return equationStatus;
        }

        // get rid of denominators that have the symbol
        equationStatus = removeSymbolFromDenominator(equation);
        if (equationStatus.hasChanged()) {
            return equationStatus;
        }

        // remove the symbol from the right side
        equationStatus = removeSymbolFromRightSide(equation);
        if (equationStatus.hasChanged()) {
            return equationStatus;
        }

        // isolate the symbol on the left side
        equationStatus = isolateSymbolOnLeftSide(equation);
        if (equationStatus.hasChanged()) {
            return equationStatus;
        }

        return EquationStatus.noChange(equation);

    }

    private void logSteps(EquationStatus equationStatus) {

    }

    // Simplifies the equation and returns the simplification steps
    protected List<EquationStatus> addSimplificationSteps(List<EquationStatus> steps, Tree equation, Boolean debug) {

        Tree oldEquation = equation.clone();

        /*
            1. On the left side, we should first simplify,
            and add all those simplify substeps to a list of leftSteps.
            2. Then we should factor the simplified equation, and add all
            those factoring substeps to the leftSteps list.
            3. On the right side, there should be no need to factor,
            because we always move everything to the left side first
            e.g. x^2 + 3x + 2 = 0 <- factor the left side
            e.g. x + 4 + 2 = 0 <- simplify the left side
            e.g. 0 = x^2 + 3x + 2 -> x^2 + 3x + 2 = 0 <- swap to the left side
         */
        List<NodeStatus> leftSimplifySteps = simplifyService.stepThrough(equation.getLeftNode());
        TreeNode simplifiedLeftNode = !leftSimplifySteps.isEmpty()
                ? (leftSimplifySteps.get(leftSimplifySteps.size() -1)).getNewNode()
                : equation.getLeftNode();

        simplifiedLeftNode = TreeUtils.removeUnnecessaryParens(simplifiedLeftNode, true);

        List<NodeStatus> leftFactorSteps = new ArrayList<>();

        // Solamente va a factorizar si a la derecha ya no quedan terminos
        if ("0".equals(equation.getRightNode().toExpression())){
            leftFactorSteps = factorStepThrough(simplifiedLeftNode, false);
        }

        List<EquationStatus> leftSubSteps = new ArrayList<>();

        for (NodeStatus step:  leftSimplifySteps) {
            leftSubSteps.add(EquationStatus.addLeftStep(equation, step));
        }

        for (NodeStatus step:  leftFactorSteps) {
            leftSubSteps.add(EquationStatus.addLeftStep(equation, step));
        }


        if (leftSubSteps.size() == 1) {
            EquationStatus step = leftSubSteps.get(0);
            if (debug) {
                logSteps(step);
            }
            steps.add(step);

        }  else if (leftSubSteps.size() > 1) {
          /*  EquationStatus lastStep = leftSubSteps.get(leftSubSteps.size()-1);
            Tree finalEquation = EquationStatus.resetChangeGroups(lastStep.getNewEquation());
                    // no change groups are set here - too much is changing for it to be useful
            EquationStatus simplifyStatus = new EquationStatus(
                    NodeStatus.ChangeTypes.SIMPLIFY_LEFT_SIDE,
                    oldEquation, finalEquation, leftSubSteps);
            if (debug) {
                logSteps(simplifyStatus);
            }
            steps.add(simplifyStatus);
            */
            steps.addAll(leftSubSteps);
        }

        // update `equation` to have the new simplified left node
        if (steps.size() > 0) {
            equation = EquationStatus.resetChangeGroups(
                    (steps.get(steps.size()-1)).getNewEquation());
        }

        // the updated equation from simplifing the left side is the old equation
        // (ie the "before" of the before and after) for simplifying the right side.
        oldEquation = equation.clone();

        List<NodeStatus> rightSteps = simplifyService.stepThrough(equation.getRightNode());
        List<EquationStatus> rightSubSteps = new ArrayList<>();

        for (NodeStatus step:  rightSteps) {
            rightSubSteps.add(EquationStatus.addRightStep(equation, step));
        }

        if (rightSubSteps.size() == 1) {
            EquationStatus step = rightSubSteps.get(0);
            if (debug) {
                logSteps(step);
            }
            steps.add(step);
        }
        else if (rightSubSteps.size() > 1) {
            /*EquationStatus lastStep = rightSubSteps.get(rightSubSteps.size() - 1);
            Tree finalEquation = EquationStatus.resetChangeGroups(lastStep.getNewEquation());
            // no change groups are set here - too much is changing for it to be useful
            EquationStatus simplifyStatus = new EquationStatus(
                    NodeStatus.ChangeTypes.SIMPLIFY_RIGHT_SIDE,
                    oldEquation, finalEquation, rightSubSteps);
            if (debug) {
                logSteps(simplifyStatus);
            }
            steps.add(simplifyStatus);*/
            steps.addAll(rightSubSteps);
        }

        return steps;
    }

    /*
      Helper function to return the roots to a factor of an equation
      e.g. (x + 2) (x - 2) = 0 -> getRootsStatus (x + 2 = 0) will return
      a new EquationStatus with x = -2 as the roots
      Similarly getRootsStatus(x - 2 = 0) will return x = 2
    */
    private EquationStatus getRootsStatus(Tree equation) {
        List<TreeNode> solutions = new ArrayList<>();
        TreeNode symbol = getSolutionsAndSymbol(equation, solutions);

        TreeNode allSolutions;

        if (solutions.size() > 1) {
            List<TreeNode> flattenSolutionsList = new ArrayList<>();
            for(TreeNode s: solutions){
                /*if (s.getLeftNode() !=null || s.getRightNode()!=null){
                    for(TreeNode child: s.getArgs()){
                        if (child!= null){
                            flattenSolutionsList.add(child);
                        }
                    }
                }else{*/
                flattenSolutionsList.add(s);
                //}
            }
            allSolutions =  TreeNode.createList(flattenSolutionsList);
        }
        else if (solutions.size() == 1) {
            allSolutions = TreeNode.createList(TreeUtils.singletonList(solutions.get(0)));
        }
        else {
            allSolutions = TreeNode.createList(new ArrayList<TreeNode>());
        }

        if(!"=".equals(equation.getRootNode().getValue()) && solutions.size() == 2){
            // Calculo los intervalos de la inecuacion cuadratica
            return findQuadraticInecuationInterval(equation, equation.getRootNode().getValue(), solutions);
        }else{
            // Raices normales
            Tree roots = new Tree(symbol, allSolutions, equation.getRootNode().getValue());
            return new EquationStatus(NodeStatus.ChangeTypes.FIND_ROOTS, equation, roots);
        }
    }

    /*
      Helper function that returns the roots and symbol of an input equation
      that has roots to be found. (We check if the equation has roots first)
      For every factor on the left hand side, solve a new equation that is factor = 0
      for the symbol.
    */
    private TreeNode getSolutionsAndSymbol(Tree equation, List<TreeNode> solutions){

        // Por las dudas flattenizo
        TreeNode leftNode = TreeUtils.flattenOperands(equation.getLeftNode());
        List<TreeNode> factorsWithSymbols = new ArrayList<>();
        List<EquationStatus> steps;
        TreeNode symbol = null;

        // If left hand side is a power node and it does not resolve to a constant
        // then it is a factor. (e.g. 2^7 resolves to a constant so it is not a factor, but
        // x^2 would have factors x = 0)
        // If left hand side is a multiplication node, return a list of all the valid factors.
        if (leftNode.esPotencia() && !TreeUtils.resolvesToConstant(leftNode)){
            factorsWithSymbols = TreeUtils.singletonList(leftNode);
        }
        else if (TreeUtils.isSymbol(leftNode)){
            factorsWithSymbols = TreeUtils.singletonList(leftNode);
        }
        else {
            for(TreeNode child: equation.getLeftNode().getArgs()){
                if (!TreeUtils.resolvesToConstant(child)){
                    factorsWithSymbols.add(child);
                }
            }
        }

        /*
            For each factor, solve the equation factor = 0 for the symbol
            If the factor is a power node, solve the equation base = 0 for the symbol
            Show duplicate roots when factor is a power node
            e.g. (x + 1)^2 -> x = [-1, -1]
        */

        for (TreeNode factor :factorsWithSymbols) {

            Integer exponent = 1;

            if (factor.esPotencia()) {
                exponent = factor.getChild(1).getIntegerValue();
                factor = factor.getChild(0);
            } else if (TreeUtils.isSymbol(factor)){
                exponent = factor.getExponent();
                factor =  TreeNode.createPolynomialTerm("X", 1, factor.getCoefficient());
            }

            leftNode = factor.isParenthesis()
                    ? factor.getChild(0)
                    : factor;

            steps = stepThrough(new Tree(leftNode, equation.getRightNode(), "="), false);

            if (steps.isEmpty()  && TreeUtils.isSymbol(leftNode, false)) {
                // e.g. x = 0
                symbol = leftNode;

                // Push the solution multiple times when we have duplicate roots
                // e.g. (x + 1)^2 -> x = [-1, -1]
                for (int i=0; i < exponent; i++){
                    solutions.add(equation.getRightNode());
                }
            }
            else if (!steps.isEmpty()) {
                // Solving for the variable on one side may sometimes
                // result in more than one step
                // e.g. x - 2 = 0
                EquationStatus lastStep = steps.get(steps.size()-1);

                // Append to a list of roots
                if (TreeUtils.isSymbol(lastStep.getNewEquation().getLeftNode())) {
                    symbol = lastStep.getNewEquation().getLeftNode();
                    for (int i=0; i < exponent; i++){
                        solutions.add(lastStep.getNewEquation().getRightNode());
                    }
                }
            }
        }

        return symbol;
    }

    private EquationStatus ensureSymbolInLeftNode(Tree equation) {

        String symbolName = "X";
        TreeNode leftSideSymbolTerm = TreeUtils.getLastSymbolTerm(
                equation.getLeftNode(), symbolName);
        Boolean containsSymbol = equation.getLeftNode().toExpression().contains(symbolName);

        TreeNode rightSideSymbolTerm = TreeUtils.getLastSymbolTerm(
                equation.getRightNode(), symbolName);

        if (leftSideSymbolTerm == null && !containsSymbol) {
            if (rightSideSymbolTerm != null) {
                String comparator = TreeUtils.inverseComparator(equation.getRootNode().getValue());
                Tree oldEquation = equation;
                Tree newEquation = new Tree(equation.getRightNode(), equation.getLeftNode(), comparator);
                // no change groups are set for this step because everything changes, so
                // they wouldn't be pedagogically helpful.
                return new EquationStatus(
                        NodeStatus.ChangeTypes.SWAP_SIDES, oldEquation, newEquation);
            }
            else {
                throw new Error("No term with symbol: " + symbolName);
            }
        }
        return EquationStatus.noChange(equation);
    }

    // Ensures that a symbol is not in the denominator by multiplying
    // both sides by the denominator if there is a symbol present.
    private EquationStatus removeSymbolFromDenominator(Tree equation) {
        // Can't multiply a symbol across non-equal comparators
        // because you don't know if it's negative and need to flip the sign
        if (!"=".equals(equation.getComparator())) {
            return EquationStatus.noChange(equation);
        }
        TreeNode leftNode = equation.getLeftNode();
        TreeNode denominator = TreeUtils.getLastDenominatorWithSymbolTerm(leftNode);
        if (denominator != null) {
            return performTermOperationOnEquation(
                    equation, "*", denominator, NodeStatus.ChangeTypes.MULTIPLY_TO_BOTH_SIDES);
        }
        return EquationStatus.noChange(equation);
    }

    // Modifies the left and right sides of an equation by `op`-ing `term`
    // to both sides. Returns an Status object.
    private EquationStatus performTermOperationOnEquation(Tree equation, String op, TreeNode term, NodeStatus.ChangeTypes changeType) {

        Tree oldEquation = equation.clone();

        TreeNode leftTerm = term.cloneDeep();
        TreeNode rightTerm = term.cloneDeep();
        TreeNode leftNode = performTermOperationOnExpression(
                equation.getLeftNode(), op, leftTerm);
        TreeNode rightNode = performTermOperationOnExpression(
                equation.getRightNode(), op, rightTerm);

        String comparator = equation.getComparator();
        if (TreeUtils.isNegative(term) && ("*".equals(op) || "/".equals(op))) {
            comparator = TreeUtils.inverseComparator(comparator);
        }

        // Saco los parentesis proque complejizan innecesariamente
        leftNode = TreeUtils.removeUnnecessaryParens(leftNode);
        rightNode = TreeUtils.removeUnnecessaryParens(rightNode);

        Tree newEquation = new Tree(leftNode, rightNode, comparator);
        return new EquationStatus(changeType, oldEquation, newEquation);
    }

    // Performs an operation of a term on an entire given expression
    private TreeNode performTermOperationOnExpression(TreeNode expression, String op, TreeNode term) {
        TreeNode node = (expression.esOperador() ?
                TreeNode.createParenthesis(expression) : expression);

        term.setChangeGroup(1);
        TreeNode newNode = TreeNode.createOperator(op, node, term);

        return newNode;
    }

    // Removes the given symbolName from the right side by adding or subtracting
    // it from both sides as appropriate.
    // e.g. 2x = 3x + 5 --> 2x - 3x = 5
    // There are actually no cases where we'd remove symbols from the right side
    // by multiplying or dividing by a symbol term.
    // TODO: support inverting functions e.g. sqrt, ^, log etc.
    private EquationStatus removeSymbolFromRightSide(Tree equation) {
        TreeNode rightNode = equation.getRightNode();
        TreeNode symbolTerm = TreeUtils.getLastSymbolTerm(rightNode, "X");

        String inverseOp;
        NodeStatus.ChangeTypes changeType;
        TreeNode inverseTerm;

        if (symbolTerm == null){
            return EquationStatus.noChange(equation);
        }

        // Clone it so that any operations on it don't affect the node already
        // in the equation
        symbolTerm = symbolTerm.cloneDeep();

        if (TreeUtils.isPolynomialTerm(rightNode)) {
            if (TreeUtils.isNegative(symbolTerm)) {
                inverseOp = "+";
                changeType = NodeStatus.ChangeTypes.ADD_TO_BOTH_SIDES;
                inverseTerm = TreeUtils.negate(symbolTerm);
            }
            else {
                inverseOp = "-";
                changeType = NodeStatus.ChangeTypes.SUBTRACT_FROM_BOTH_SIDES;
                inverseTerm = symbolTerm;
            }
        }else if (TreeUtils.hasDenominatorSymbol(rightNode)){
            inverseOp = "*";
            changeType = NodeStatus.ChangeTypes.MULTIPLY_TO_BOTH_SIDES;
            inverseTerm = symbolTerm.getRightNode();
        }
        else if (rightNode.esOperador()) {
            if (rightNode.esSuma()) {
                if (TreeUtils.isNegative(symbolTerm)) {
                    inverseOp = "+";
                    changeType = NodeStatus.ChangeTypes.ADD_TO_BOTH_SIDES;
                    inverseTerm = TreeUtils.negate(symbolTerm);
                }
                else {
                    inverseOp = "-";
                    changeType = NodeStatus.ChangeTypes.SUBTRACT_FROM_BOTH_SIDES;
                    inverseTerm = symbolTerm;
                }
            }
            else {
                // Note that operator '-' won't show up here because subtraction is
                // flattened into adding the negative. See 'TRICKY catch' in the README
                // for more details.
                throw new Error("Unsupported operation: " + symbolTerm.getValue());
            }
        }
        else if (rightNode.isUnaryMinus()) {
            inverseOp = "+";
            changeType = NodeStatus.ChangeTypes.ADD_TO_BOTH_SIDES;
            inverseTerm = symbolTerm.getChild(0);
        }
        else {
            throw new Error("Unsupported node type: " + rightNode.toExpression());
        }
        return performTermOperationOnEquation(
                equation, inverseOp, inverseTerm, changeType);
    }

    // Isolates the given symbolName to the left side by adding, multiplying, subtracting
    // or dividing all other symbols and constants from both sides appropriately
    // TODO: support inverting functions e.g. sqrt, ^, log etc.
    private EquationStatus isolateSymbolOnLeftSide(Tree equation) {
        TreeNode leftNode = equation.getLeftNode();
        TreeNode nonSymbolTerm = TreeUtils.getLastNonSymbolTerm(leftNode);

        String inverseOp;
        NodeStatus.ChangeTypes changeType;
        TreeNode inverseTerm;
        if (nonSymbolTerm == null) {
            return EquationStatus.noChange(equation);
        }

        // Clone it so that any operations on it don't affect the node already
        // in the equation
        nonSymbolTerm = nonSymbolTerm.cloneDeep();

        if (leftNode.esOperador()) {
            if (leftNode.esSuma()) {
                if (TreeUtils.isNegative(nonSymbolTerm)) {
                    inverseOp = "+";
                    changeType = NodeStatus.ChangeTypes.ADD_TO_BOTH_SIDES;
                    inverseTerm = TreeUtils.negate(nonSymbolTerm);
                }
                else {
                    inverseOp = "-";
                    changeType = NodeStatus.ChangeTypes.SUBTRACT_FROM_BOTH_SIDES;
                    inverseTerm = nonSymbolTerm;
                }
            }
            else if (leftNode.esProducto()) {
                if (TreeUtils.isConstantFraction(nonSymbolTerm)) {
                    inverseOp = "*";
                    changeType = NodeStatus.ChangeTypes.MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION;
                    inverseTerm = TreeNode.createOperator (
                            "/", nonSymbolTerm.getChild(1), nonSymbolTerm.getChild(0));
                }
                else {
                    inverseOp = "/";
                    changeType = NodeStatus.ChangeTypes.DIVIDE_FROM_BOTH_SIDES;
                    inverseTerm = nonSymbolTerm;
                }
            }
            else if (leftNode.esDivision()) {
                // The non symbol term is always a fraction because it's the
                // coefficient of our symbol term.
                // If the numerator is 1, we multiply both sides by the denominator,
                // otherwise we multiply by the inverse
                if ("1".equals(nonSymbolTerm.getChild(0).getValue()) || "-1".equals(nonSymbolTerm.getChild(0).getValue())) {
                    inverseOp = "*";
                    changeType = NodeStatus.ChangeTypes.MULTIPLY_TO_BOTH_SIDES;
                    inverseTerm = nonSymbolTerm.getChild(1);
                } else {
                    inverseOp = "*";
                    changeType = NodeStatus.ChangeTypes.MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION;
                    inverseTerm = TreeNode.createOperator(
                            "/", nonSymbolTerm.getChild(1), nonSymbolTerm.getChild(0));
                }
            }
            else if (leftNode.esPotencia()) {
                // TODO: support roots
                return EquationStatus.noChange(equation);
            }
            else {
                throw new Error("Unsupported operation: " + leftNode.toExpression());
            }
        }
        else if (TreeUtils.isSymbol(leftNode) && leftNode.getCoefficient() != 1) {
            // Pasa diviiendo el coeficiente
            inverseOp = "/";
            changeType = NodeStatus.ChangeTypes.DIVIDE_FROM_BOTH_SIDES;
            inverseTerm = nonSymbolTerm;
        }
        else if (leftNode.isUnaryMinus()) {
            inverseOp = "*";
            changeType = NodeStatus.ChangeTypes.MULTIPLY_BOTH_SIDES_BY_NEGATIVE_ONE;
            inverseTerm = TreeNode.createConstant(-1);
        }
        else {
            throw new Error("Unsupported node type: " + leftNode.toExpression());
        }

        return performTermOperationOnEquation(
                equation, inverseOp, inverseTerm, changeType);
    }

    // Given a mathjs expression node, steps through factoring the expression.
    // Currently only supports factoring quadratics.
    // Returns a list of details about each step.
    protected List<NodeStatus> factorStepThrough(TreeNode originalNode, boolean debug) {

        TreeNode node =originalNode.cloneDeep();
        if (debug) {
            // eslint-disable-next-line
            System.out.println("\n\nFactoring: " + node.toExpression());
        }

        NodeStatus nodeStatus;
        List<NodeStatus> steps = new ArrayList<>();

        node = TreeUtils.flattenOperands(node);
        node = TreeUtils.removeUnnecessaryParens(node, true);
        if (TreeUtils.isQuadratic(node)) {
            nodeStatus = factorQuadratic(node);
            if (nodeStatus.hasChanged()) {
                steps.add(nodeStatus);
            }
        }
        // Add factoring higher order polynomials...

        return steps;
    }

    // Given a node, will check if it's in the form of a quadratic equation
    // `ax^2 + bx + c`, and
    // if it is, will factor it using one of the following rules:
    //    - Factor out the symbol e.g. x^2 + 2x -> x(x + 2)
    //    - Difference of squares e.g. x^2 - 4 -> (x+2)(x-2)
    //    - Perfect square e.g. x^2 + 2x + 1 -> (x+1)^2
    //    - Sum/product rule e.g. x^2 + 3x + 2 -> (x+1)(x+2)
    //    - TODO: quadratic formula
    //        requires us simplify the following only within the parens:
    //        a(x - (-b + sqrt(b^2 - 4ac)) / 2a)(x - (-b - sqrt(b^2 - 4ac)) / 2a)
    protected NodeStatus factorQuadratic(TreeNode node) {
        // get a, b and c
        String symbol = "X";
        Integer aValue = 0;
        Integer bValue = 0;
        Integer cValue = 0;

        for (TreeNode term : node.getArgs()) {
            if (TreeUtils.isConstant(term)) {
                cValue = term.getIntegerValue();
            }
            else if (TreeUtils.isPolynomialTerm(term)) {
                Integer exponent = term.getExponent();
                if (exponent.equals(2)) {
                    symbol = "X";
                    aValue = term.getCoefficient();
                }
                else if (exponent.equals(1)) {
                    bValue = term.getCoefficient();
                }
                else {
                    return NodeStatus.noChange(node);
                }
            }
            else {
                return NodeStatus.noChange(node);
            }
        }

        if (symbol == null || aValue.equals(0)) {
            return NodeStatus.noChange(node);
        }

        Boolean negate = false;
        if (aValue < 0) {
            negate = true;
            aValue = aValue * (-1);
            bValue = bValue * (-1);
            cValue = cValue * (-1);
        }

        // factor just the symbol e.g. x^2 + 2x -> x(x + 2)
        NodeStatus nodeStatus = factorSymbol(node, symbol, aValue, bValue, cValue, negate);
        if (nodeStatus.hasChanged()){
            return nodeStatus;
        }

        // factor difference of squares e.g. x^2 - 4
        nodeStatus = factorDifferenceOfSquares(node, symbol, aValue, bValue, cValue, negate);
        if (nodeStatus.hasChanged()){
            return nodeStatus;
        }

        // factor perfect square e.g. x^2 + 2x + 1
        nodeStatus = factorPerfectSquare(node, symbol, aValue, bValue, cValue, negate);
        if (nodeStatus.hasChanged()){
            return nodeStatus;
        }

        // factor sum product rule e.g. x^2 + 3x + 2
        nodeStatus = factorSumProductRule(node, symbol, aValue, bValue, cValue, negate);
        if (nodeStatus.hasChanged()){
            return nodeStatus;
        }

        return NodeStatus.noChange(node);
    }

    // Will factor the node if it's in the form of ax^2 + bx
    private NodeStatus factorSymbol(TreeNode node, String symbol, Integer aValue, Integer bValue, Integer cValue, Boolean negate) {
        if (bValue.equals(0) || !cValue.equals(0)) {
            return NodeStatus.noChange(node);
        }

        Integer gcd = TreeUtils.calculateGCD(aValue, bValue);
        TreeNode gcdNode = TreeNode.createConstant(gcd);
        TreeNode aNode = TreeNode.createConstant(aValue/gcd);
        TreeNode bNode = TreeNode.createConstant(bValue/gcd);

        TreeNode factoredNode = TreeNode.createPolynomialTerm(symbol, 1, gcd);
        TreeNode polyTerm = TreeNode.createPolynomialTerm(symbol, 1, aNode.getIntegerValue());
        TreeNode paren = TreeNode.createParenthesis(
                TreeNode.createOperator("+", polyTerm, bNode));

        TreeNode newNode = TreeNode.createOperator("*", factoredNode, paren);
        if (negate) {
            newNode = TreeUtils.negate(newNode);
        }

        return NodeStatus.nodeChanged(NodeStatus.ChangeTypes.FACTOR_SYMBOL, node, newNode);
    }

    // Will factor the node if it's in the form of ax^2 - c, and the aValue
    // and cValue are perfect squares
    // e.g. 4x^2 - 4 -> (2x + 2)(2x - 2)
    private NodeStatus factorDifferenceOfSquares(TreeNode node, String symbol, Integer aValue, Integer bValue, Integer cValue, Boolean negate) {

        // check if difference of squares:
        //    (i) abs(a) and abs(c) are squares,
        //    (ii) b = 0,
        //    (iii) c is negative
        if (!bValue.equals(0) || cValue.equals(0)) {
            return NodeStatus.noChange(node);
        }

        // we factor out the gcd first, providing us with a modified expression to
        // factor with new a and c values
        Integer gcd = TreeUtils.calculateGCD(aValue, cValue);
        aValue = aValue/gcd;
        cValue = cValue/gcd;
        double aRootValue = Math.sqrt(Math.abs(aValue));
        double cRootValue = Math.sqrt(Math.abs(cValue));

        // must be a difference of squares
        if (TreeUtils.isDoubleInteger(aRootValue) &&
                TreeUtils.isDoubleInteger(cRootValue) &&
                cValue < 0) {

            TreeNode aRootNode = TreeNode.createConstant((int)aRootValue);
            TreeNode cRootNode = TreeNode.createConstant((int)cRootValue);

            TreeNode polyTerm = TreeNode.createPolynomialTerm(symbol, 1, aRootNode.getIntegerValue());
            TreeNode firstParen = TreeNode.createParenthesis(
                    TreeNode.createOperator("+", polyTerm, cRootNode));
            TreeNode secondParen = TreeNode.createParenthesis(
                    TreeNode.createOperator("-", polyTerm, cRootNode));

            // create node in difference of squares form
            TreeNode newNode = TreeNode.createOperator("*", firstParen, secondParen);
            if (!gcd.equals(1)) {
                TreeNode gcdNode = TreeNode.createConstant(gcd);
                newNode = TreeNode.createOperator("*", gcdNode, newNode);
            }
            if (negate) {
                newNode = TreeUtils.negate(newNode);
            }

            return NodeStatus.nodeChanged(
                    NodeStatus.ChangeTypes.FACTOR_DIFFERENCE_OF_SQUARES, node, newNode);
        }

        return NodeStatus.noChange(node);
    }

    // Will factor the node if it's in the form of ax^2 + bx + c, where a and c
    // are perfect squares and b = 2*sqrt(a)*sqrt(c)
    // e.g. x^2 + 2x + 1 -> (x + 1)^2
    private NodeStatus factorPerfectSquare(TreeNode node, String symbol, Integer aValue, Integer bValue, Integer cValue, Boolean negate) {
        // check if perfect square: (i) a and c squares, (ii) b = 2*sqrt(a)*sqrt(c)
        if (bValue.equals(0) || cValue.equals(0)) {
            return NodeStatus.noChange(node);
        }

        // we factor out the gcd first, providing us with a modified expression to
        // factor with new a and c values
        Integer gcd = TreeUtils.calculateGCD(aValue, bValue);
        gcd = TreeUtils.calculateGCD(gcd, cValue);

        aValue = aValue/gcd;
        cValue = cValue/gcd;
        double aRootValue = Math.sqrt(Math.abs(aValue));
        double cRootValue = Math.sqrt(Math.abs(cValue));

        // if the second term is negative, then the constant in the parens is
        // subtracted: e.g. x^2 - 2x + 1 -> (x - 1)^2
        if (bValue < 0) {
            cRootValue = cRootValue * -1;
        }

        // apply the perfect square test
        double perfectProduct = 2 * aRootValue * cRootValue;
        if (TreeUtils.isDoubleInteger(aRootValue) &&
                TreeUtils.isDoubleInteger(cRootValue) &&
                (bValue/gcd) == perfectProduct) {
            TreeNode aRootNode = TreeNode.createConstant((int)aRootValue);
            TreeNode cRootNode = TreeNode.createConstant((int)cRootValue);

            TreeNode polyTerm = TreeNode.createPolynomialTerm(symbol, 1, aRootNode.getIntegerValue());
            TreeNode paren = TreeNode.createParenthesis(
                    TreeNode.createOperator("+", polyTerm, cRootNode));
            TreeNode exponent = TreeNode.createConstant(2);

            // create node in perfect square form
            TreeNode newNode =TreeNode.createOperator("^", paren, exponent);
            if (!gcd.equals(1)) {
                TreeNode gcdNode = TreeNode.createConstant(gcd);
                newNode = TreeNode.createOperator("*", gcdNode, newNode);
            }
            if (negate) {
                newNode = TreeUtils.negate(newNode);
            }

            return NodeStatus.nodeChanged(
                    NodeStatus.ChangeTypes.FACTOR_PERFECT_SQUARE, node, newNode);
        }

        return NodeStatus.noChange(node);
    }

    // Will factor the node if it's in the form of ax^2 + bx + c, by
    // applying the sum product rule: finding factors of a*c that add up to b.
    // e.g. x^2 + 3x + 2 -> (x + 1)(x + 2) or
    // or   2x^2 + 5x + 3 -> (2x - 1)(x + 3)
    private NodeStatus factorSumProductRule(TreeNode node, String symbol, Integer aValue, Integer bValue, Integer cValue, Boolean negate) {

        TreeNode newNode;

        if (!bValue.equals(0) && !cValue.equals(0)) {
            // we factor out the gcd first, providing us with a modified expression to
            // factor with new a, b and c values
            Integer gcd = TreeUtils.calculateGCD(aValue, bValue);
            gcd = TreeUtils.calculateGCD(gcd, cValue);

            TreeNode gcdNode = TreeNode.createConstant(gcd);

            aValue = aValue/gcd;
            bValue = bValue/gcd;
            cValue = cValue/gcd;

            // try sum/product rule: find a factor pair of a*c that adds up to b
            Integer product = aValue * cValue;
            List<Integer[]> factorPairs = getFactorPairs(product, true);
            for (Integer[] pair:factorPairs) {
                if (pair[0] + pair[1] == bValue) {
                    // To factor, we go through some transformations
                    // 1. Break apart the middle term into two terms using our factor pair
                    //    (p and q): e.g. ax^2 + bx + c -> ax^2 + px + qx + c
                    // 2. Consider the first two terms together and the second two terms
                    //    together (this doesn't require any actual change to the expression)
                    //    e.g. first group: [ax^2 + px] and second group: [qx + c]
                    // 3. Factor both groups separately
                    //    e.g first group: [ux(rx + s)] and second group [v(rx + s)]
                    // 4. Finish factoring by combining the factored terms through grouping:
                    //    e.g. (ux + v)(rx + s)
                    List<NodeStatus> substeps = new ArrayList<>();
                    NodeStatus status;

                    TreeNode a = TreeNode.createConstant(aValue);
                    TreeNode b = TreeNode.createConstant(bValue);
                    TreeNode c = TreeNode.createConstant(cValue);
                    TreeNode ax2 = TreeNode.createPolynomialTerm(symbol, 2, a.getIntegerValue());
                    TreeNode bx = TreeNode.createPolynomialTerm(symbol, 1, b.getIntegerValue());

                    // OPTIONAL SUBSTEP (this happens iff a is negative)
                    // ax^2 + bx + c -> -(-ax^2 - bx - c)
                    if (negate) {
                        newNode = TreeNode.createOperator("+", ax2, bx, c);
                        newNode = TreeUtils.negate(newNode);
                        status = NodeStatus.nodeChanged(
                                NodeStatus.ChangeTypes.REARRANGE_COEFF, node, newNode);
                        substeps.add(status);
                        newNode = NodeStatus.resetChangeGroups(status.getNewNode());
                    }

                    // SUBSTEP 1: ax^2 + bx + c -> ax^2 + px + qx + c
                    Integer pValue = pair[0];
                    Integer qValue = pair[1];
                    TreeNode p = TreeNode.createConstant(pValue);
                    TreeNode q = TreeNode.createConstant(qValue);
                    TreeNode px = TreeNode.createPolynomialTerm(symbol, 1, p.getIntegerValue());
                    TreeNode qx = TreeNode.createPolynomialTerm(symbol, 1, q.getIntegerValue());

                    newNode = TreeNode.createOperator("+", ax2, px, qx);
                    newNode.addChild(c);
                    if (negate) {
                        newNode = TreeUtils.negate(newNode);
                    }
                    status = NodeStatus.nodeChanged(
                            NodeStatus.ChangeTypes.BREAK_UP_TERM, node, newNode);
                    substeps.add(status);
                    newNode = NodeStatus.resetChangeGroups(status.getNewNode());

                    // STEP 2: ax^2 + px + qx + c -> (ax^2 + px) + (qx + c)
                    TreeNode firstTerm = TreeNode.createParenthesis(
                            TreeNode.createOperator("+", ax2, px));
                    TreeNode secondTerm = TreeNode.createParenthesis(
                            TreeNode.createOperator("+", qx, c));

                    newNode = TreeNode.createOperator("+", firstTerm, secondTerm);
                    if (negate) {
                        newNode = TreeUtils.negate(newNode);
                    }
                    status = NodeStatus.nodeChanged(
                            NodeStatus.ChangeTypes.COLLECT_LIKE_TERMS, node, newNode);
                    substeps.add(status);
                    newNode = NodeStatus.resetChangeGroups(status.getNewNode());

                    // SUBSTEP 3A: (ax^2 + px) + (qx + c) -> ux(rx + s) + (qx + c)
                    Integer u = TreeUtils.calculateGCD(aValue, pValue);
                    TreeNode r = TreeNode.createConstant(aValue/u);
                    TreeNode s = TreeNode.createConstant(pValue/u);
                    TreeNode ux = TreeNode.createPolynomialTerm(symbol, 1, u);

                    // create the first group's part that's in parentheses: (rx + s)
                    TreeNode rx = TreeNode.createPolynomialTerm(symbol, 1, r.getIntegerValue());
                    TreeNode firstParen = TreeNode.createParenthesis(
                            TreeNode.createOperator("+", rx, s));

                    TreeNode firstFactoredGroup = TreeNode.createOperator("*", ux, firstParen);
                    newNode = TreeNode.createOperator("+", firstFactoredGroup, secondTerm);
                    if (negate) {
                        newNode = TreeUtils.negate(newNode);
                    }
                    status = NodeStatus.nodeChanged(
                            NodeStatus.ChangeTypes.FACTOR_SYMBOL, node, newNode);
                    substeps.add(status);
                    newNode = NodeStatus.resetChangeGroups(status.getNewNode());

                    // STEP 3B: ux(rx + s) + (qx + c) -> ux(rx + s) + v(rx + s)
                    Integer vValue = TreeUtils.calculateGCD(cValue, qValue);
                    if (qValue < 0) {
                        vValue = vValue * -1;
                    }
                    TreeNode v = TreeNode.createConstant(vValue);

                    // create the second parenthesis
                    TreeNode secondParen = TreeNode.createParenthesis(
                            TreeNode.createOperator("+", ux, v));

                    TreeNode secondFactoredGroup = TreeNode.createOperator("*", v, firstParen);
                    newNode = TreeNode.createOperator("+", firstFactoredGroup, secondFactoredGroup);
                    if (negate) {
                        newNode = TreeUtils.negate(newNode);
                    }
                    status = NodeStatus.nodeChanged(
                            NodeStatus.ChangeTypes.FACTOR_SYMBOL, node, newNode);
                    substeps.add(status);
                    newNode = NodeStatus.resetChangeGroups(status.getNewNode());

                    // STEP 4: ux(rx + s) + v(rx + s) -> (ux + v)(rx + s)
                    if (gcd.equals(1)) {
                        newNode = TreeNode.createOperator(
                                "*", firstParen, secondParen);
                    }
                    else {
                        newNode = TreeNode.createOperator(
                                "*", gcdNode, firstParen, secondParen);
                    }

                    if (negate) {
                        newNode = TreeUtils.negate(newNode);
                    }

                    status = NodeStatus.nodeChanged(
                            NodeStatus.ChangeTypes.FACTOR_SUM_PRODUCT_RULE, node, newNode);
                    substeps.add(status);
                    newNode = NodeStatus.resetChangeGroups(status.getNewNode());

                    return NodeStatus.nodeChanged(
                            NodeStatus.ChangeTypes.FACTOR_SUM_PRODUCT_RULE, node, newNode, true, substeps);
                }
            }
        }

        return NodeStatus.noChange(node);
    }

    // Given a number, will return all the factor pairs for that number as a list
    // of 2-item lists
    protected List<Integer[]> getFactorPairs(Integer number, boolean b) {

        List<Integer[]> factors = new ArrayList<>();

        Integer bound = (int )Math.floor(Math.sqrt(Math.abs(number)));
        for (Integer divisor = -bound; divisor <= bound; divisor++) {
            if (divisor == 0) {
                continue;
            }
            if (number % divisor == 0) {
                Integer quotient = number / divisor;
                factors.add(new Integer[]{divisor, quotient});
            }
        }

        return factors;
    }

    private EquationStatus findQuadraticInecuationInterval(Tree oldEquation, String operator, List<TreeNode> solutions) {

        // Las soluciones son las raices, luego voy a tener algo de la forma:
        // (X - R1)*(X - R2) Operador(>, >=, <, <=) 0
        Integer R1 = solutions.get(0).getIntegerValue();
        Integer R2 = solutions.get(1).getIntegerValue();

        Integer max = Math.max(R1, R2);
        Integer min = Math.min(R1, R2);

        String interval;

        // Dependiendo si es mayor o menor a 0, el producto debe dar positivo o negativo
        if (operator.contains(">")){
            // el producto es mayor a 0 (salvo que las raices sean iguales)

            // Si es la misma raiz, la solucion es unica
            if (R1.equals(R2)){
                // Si es por >=, son todos los reales
                if (operator.contains("=")){
                    interval = "(-INF,INF)";
                }else{
                    // Si es estrictamente mayor, serán todos los reales menos ese numero:
                    // I=(-INF, R)U(R, INF)
                    interval = "(-INF," + R1 + ")U(" + R1 + ",INF)";
                }
            }else{
                // a) Los dos tienen que ser mayor a 0
                // (X-R1)*(X-R2) => X > R1 y X > R2.
                // Ej: Con R1=-5 y R2=3 => (X+5)*(X-3) => X>-5 y X>3 => I=(MAX(R1,R2),INF)


                // b) Los dos tienen que ser menor a 0
                // (X-R1)*(X-R2) => X < R1 y X < R2.
                // Ej: Con R1=-5 y R2=3 => (X+5)*(X-3) => X<-5 y X<3 => I=(-INF,MIN(R1,R2))

                // Son raices distintas, el intervalo sera:
                if (operator.contains("=")) {
                    // I=(-INF, MIN)U(MAX, INF)
                    interval = "(-INF," + min + "]U[" + max + ",INF)";
                }else{
                    // I=(-INF, MIN)U(MAX, INF)
                    interval = "(-INF," + min + ")U(" + max + ",INF)";
                }
            }

        }else{
            // el producto es menor a 0
            // Uno es mayor a 0 y el otro menor a 0 (salvo que la raiz sea unica)

            // Si la raiz es unica
            if (R1.equals(R2)){
                // Por el <=, la solucióm es unica, X = R
                if (operator.contains("=")) {
                    interval = R1.toString();
                }else{
                    interval = "VACIO";
                }
            }else{
                // Son raices distintas, el intervalo sera:
                if (operator.contains("=")) {
                    // I=[MIN,MAX]
                    interval = "[" + min + "," + max + "]";
                }else{
                    // I=(MIN,MAX)
                    interval = "(" + min + "," + max + ")";
                }
            }
        }

        Tree intervalSolution = new Tree(new TreeNode("I"),
                new TreeNode(interval),
                "=");

        return new EquationStatus(NodeStatus.ChangeTypes.FIND_ROOTS, oldEquation, intervalSolution);
    }
}
