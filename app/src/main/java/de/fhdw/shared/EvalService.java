package de.fhdw.shared;

import org.mariuszgromada.math.mxparser.*;

public class EvalService {
    public static String calculateEquation (String equation) {
        Expression e = new Expression(equation);
        mXparser.consolePrintln("Checking Syntax..");
        e.checkSyntax();
        mXparser.consolePrintln(e.getErrorMessage());
        return String.valueOf(e.calculate());
    }


}
