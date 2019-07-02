package de.fhdw.shared;

import org.mariuszgromada.math.mxparser.*;

public class EvalService {
    public static String calculateEquation (String equation) {
        Expression e = new Expression(equation);
        return String.valueOf(e.calculate());
    }
    // 0 =  x^2 - 2x -8

}
