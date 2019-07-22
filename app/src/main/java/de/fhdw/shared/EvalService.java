package de.fhdw.shared;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;
import java.util.Stack;

public class EvalService {
    public static String calculateEquation(ArrayList<String> postfix) {
        String infix = convert(postfix);
        Expression e = new Expression(infix);
        return String.valueOf(e.calculate());
    }

        private static boolean isOperator(String  c) {
            if (c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/") || c.equals("^"))
                return true;
            return false;
        }

        public static String convert(ArrayList<String> postfix) {
            Stack<String> s = new Stack<>();

            for (int i = 0; i < postfix.size(); i++) {
                String c = postfix.get(i);
                if (isOperator(c)) {
                    String b = s.pop();
                    String a = s.pop();
                    s.push("(" + a + c + b + ")");
                } else
                    s.push("" + c);
            }
            return s.pop();
        }


    public static String solveEquation(ArrayList<String> postfix, String target, String interval_lower, String interval_upper) {
        String infix = convert(postfix);
        Expression e = new Expression("solve(" + infix + " ," + target + " ," + interval_lower + " ," +interval_upper+ ")");
        return String.valueOf(e.calculate());
    }
    }