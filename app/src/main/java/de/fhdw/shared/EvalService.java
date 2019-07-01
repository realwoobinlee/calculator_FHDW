package de.fhdw.shared;

import org.mariuszgromada.math.mxparser.*;



public class EvalService {
    public static String functionname(String parameter) {
        Expression e = new Expression("2+3");
        return String.valueOf(e.calculate());
    }
    // 0 =  x^2 - 2x -8

}
