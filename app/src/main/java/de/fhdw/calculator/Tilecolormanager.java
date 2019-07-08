package de.fhdw.calculator;


public class Tilecolormanager {

    public static int returncolor(String usage){
        boolean numeric = true;

        /**
        *https://www.programiz.com/java-programming/examples/check-string-numeric
         */
        try {
            Double num = Double.parseDouble(usage);
        } catch (NumberFormatException e) {
            numeric = false;
        }

        if (usage.equals("/")||
                usage.equals("*")||
                usage.equals("-")||
                usage.equals("+")||
                usage.equals("=")||
                usage.equals("sqr")||
                usage.equals("vector")||
                usage.equals("prime")||
                usage.equals("ln")||
                usage.equals("xsqr")||
                usage.equals("sin")||
                usage.equals("cos")||
                usage.equals("tan")||

                usage.equals("log")||
                usage.equals("log10")||
                usage.equals("Summe")||
                usage.equals("seq()")||
                usage.equals("summenprodukt")||
                usage.equals("bin")
        ){
            return R.color.operator;
        }
        if (numeric == true ||
                usage.equals(",") ||
                usage.equals("^") ||
                usage.equals("(")||
                usage.equals(")")||
                usage.equals("i")||
                usage.equals("e")||
                usage.equals("c")||
                usage.equals("pi")||
                usage.equals("y")||
                usage.equals("x")||
                usage.equals("electronm")||
                usage.equals("electronc")||
                usage.equals("")) {
            return R.color.constant;
        }
        if (usage.equals("view")){
            return R.color.view;
        }
        if(usage.equals("AC")||usage.equals("+/-")||usage.equals("%")){
           return R.color.specialoperator;
        }
        else {
            return R.color.error;
        }


    }
}
