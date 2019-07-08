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

        if (usage == "/" ||
                usage =="*"||
                usage =="-"||
                usage.equals("+")||
                usage =="="||
                usage =="sqr"||
                usage =="vector"||
                usage =="prime"||
                usage =="ln"||
                usage =="xsqr"||
                usage =="sin"||
                usage =="cos"||
                usage =="tan"||

                usage =="log"||
                usage =="log10"||
                usage =="Summe"||
                usage =="seq()"||
                usage =="summenprodukt"||
                usage =="bin"
        ){
            return R.color.operator;
        }
        if (numeric == true ||
                usage == ","||
                usage == "^"||
                usage == "("||
                usage == ")"||
                usage == "i"||
                usage == "e"||
                usage == "c"||
                usage == "pi"||
                usage == "y"||
                usage == "x"||
                usage == "electronm"||
                usage == "electronc"||
                usage == "") {
            return R.color.constant;
        }
        if (usage =="view"){
            return R.color.view;
        }
        if(usage =="AC"||usage=="+/-"||usage=="%"){
           return R.color.specialoperator;
        }
        else {
            return R.color.error;
        }


    }
}
