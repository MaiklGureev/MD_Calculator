package com.gureev.md_calculator;


import java.io.Serializable;
import java.util.LinkedList;

public class CalculatorStateMachine implements Serializable {

    private enum EnumState {
        s, f,
        st1, st2, st3, st4, st5, st6, st7, st8, st9, st10,
        delA, delB, delSign,
    }

    private LinkedList<String> numbers = new LinkedList<>();
    private LinkedList<String> actions = new LinkedList<>();


    private EnumState state;
    private EnumState lastState;
    private double a = 0, b = 0, res = 0;
    private String expression;
    private String opertor;
    private String result;
    private StringBuilder stringA, stringB, stringBuilderResult;

    public CalculatorStateMachine() {
        state = EnumState.s;
        //lastState = state;

        stringA = new StringBuilder();
        stringB = new StringBuilder();
        stringBuilderResult = new StringBuilder();

        stringA.setLength(0);
        stringB.setLength(0);
        stringBuilderResult.setLength(0);

        opertor = "";
        result = "";
        expression = "";

        stringA.append("");
        stringB.append("");

        numbers.add("0");
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
        numbers.add("7");
        numbers.add("8");
        numbers.add("9");

        actions.add("+");
        actions.add("-");
        actions.add("*");
        actions.add("/");
    }

    public void resetAll() {
        state = EnumState.s;
        stringA.delete(0, stringA.length());
        stringB.delete(0, stringB.length());
        stringBuilderResult.delete(0, stringBuilderResult.length());

        stringA.setLength(0);
        stringB.setLength(0);
        stringBuilderResult.setLength(0);

        opertor = "";
        result = "";
        expression = "";
        a = 0;
        b = 0;
        res = 0;
    }

    public String getExpr() {

        String expr = "";
        expr = stringA.toString() +" "+ opertor + " " + stringB.toString();
        return expr;
    }

    public String getResult() {

        String r = "";
        if (stringA.length() == 0) {
            r = "0.0";
        } else if (stringB.length() == 0 || stringB.toString()=="-") {
            r = stringA.toString();
        }
        else {
            r =stringBuilderResult.toString();
        }
        return r;
    }

    public String getFormattedExpr() {

        expression = "";
        expression = stringA.toString() + "\n" + opertor + " " + stringB.toString();
        return expression;
    }

    public String getFormattedResult() {

        result = "";
        //res = Math.rint(1000.0 * res / 1000.0);
        stringBuilderResult.setLength(0);
        stringBuilderResult.append(res);
        stringBuilderResult.setLength(15);
        //result = String.valueOf(res);

        if (stringA.length() == 0) {
            result = "= 0.0";
        } else if (stringB.length() == 0 || stringB.toString()=="-") {
            result = "= " + stringA.toString();
        }
        else {
            result = "= " + stringBuilderResult.toString();
        }

        return result;
    }

    public String getState() {
        return state.name();
    }

    public void onNumPressed(int a) {

        switch (a) {
            case R.id.home_button_0: {
                StateMachineCalculator("0");
                break;
            }
            case R.id.home_button_1: {
                StateMachineCalculator("1");
                break;
            }
            case R.id.home_button_2: {
                StateMachineCalculator("2");
                break;
            }
            case R.id.home_button_3: {
                StateMachineCalculator("3");
                break;
            }
            case R.id.home_button_4: {
                StateMachineCalculator("4");
                break;
            }
            case R.id.home_button_5: {
                StateMachineCalculator("5");
                break;
            }
            case R.id.home_button_6: {
                StateMachineCalculator("6");
                break;
            }
            case R.id.home_button_7: {
                StateMachineCalculator("7");
                break;
            }
            case R.id.home_button_8: {
                StateMachineCalculator("8");
                break;
            }
            case R.id.home_button_9: {
                StateMachineCalculator("9");
                break;
            }
        }
    }

    public void onActionPressed(int a) {
        switch (a) {
            case R.id.home_button_del: {
                StateMachineCalculator("del");
                break;
            }
            case R.id.home_button_div: {
                StateMachineCalculator("/");
                break;
            }
            case R.id.home_button_dot: {
                StateMachineCalculator(".");
                break;
            }
            case R.id.home_button_minus: {
                StateMachineCalculator("-");
                break;
            }
            case R.id.home_button_mul: {
                StateMachineCalculator("*");
                break;
            }
            case R.id.home_button_plus: {
                StateMachineCalculator("+");
                break;
            }
        }
    }

    public void StateMachineCalculator(String in) {
        switch (state) {

            case s: {
                if (in == "-") {
                    stringA.append("-");
                    state = EnumState.st1;
                }
                if (numbers.contains(in)) {
                    stringA.append(in);
                    state = EnumState.st2;
                }
                break;
            }

            case st1: {
                if (numbers.contains(in)) {
                    stringA.append(in);
                    state = EnumState.st2;
                }
                if (in == "del" && stringA.length() == 0) {
                    state = EnumState.s;
                } else if (in == "del") {
                    lastState = EnumState.st1;
                    //state = EnumState.delA;
                    delCharA();
                }
                break;
            }

            case st2: {
                if (numbers.contains(in) && (stringA.length() < 7)) {
                    stringA.append(in);
                    state = EnumState.st2;
                }
                if (in == "." && (stringA.length() < 7)) {
                    stringA.append(in);
                    state = EnumState.st3;
                }
                if (actions.contains(in)) {
                    opertor = in;
                    state = EnumState.st4;
                }
                if (in == "del" && stringA.length() != 0) {
                    lastState = EnumState.st2;
                    delCharA();
                }
                break;
            }

            case st3: {
                if (numbers.contains(in) && (stringA.length() < 7)) {
                    stringA.append(in);
                    state = EnumState.st3;
                }
                if (actions.contains(in)) {
                    opertor = in;
                    state = EnumState.st4;
                }
                if (in == "del" & stringA.length() != 0) {
                    lastState = EnumState.st3;
                    delCharA();
                }
                break;
            }

            case st4: {

                if (in == "-" && opertor != "") {
                    stringB.append("-");
                    state = EnumState.st5;
                    break;
                }

                if (actions.contains(in)) {
                    opertor = in;
                    state = EnumState.st4;
                    break;
                }


                if (numbers.contains(in)) {
                    stringB.append(in);
                    state = EnumState.st6;
                    CalculateExpression(stringA, stringB, opertor);
                }
                if (in == "del" && stringB.length() != 0) {
                    delCharB();
                    state = EnumState.st4;
                }

                if (in == "del" & stringB.length() == 0) {
                    lastState = EnumState.st3;
                    delCharSign();
                }
                break;
            }

            case st5: {
                if (numbers.contains(in)) {
                    stringB.append(in);
                    state = EnumState.st6;
                    CalculateExpression(stringA, stringB, opertor);
                }
                if (in == "del") {
                    lastState = EnumState.st5;
                    delCharB();
                }
                break;
            }

            case st6: {
                if (numbers.contains(in) && (stringB.length() < 7)) {
                    stringB.append(in);
                    state = EnumState.st6;
                    CalculateExpression(stringA, stringB, opertor);
                }
                if (in == "." && (stringA.length() < 7)) {
                    stringB.append(in);
                    state = EnumState.st7;
                    CalculateExpression(stringA, stringB, opertor);
                }
                if (actions.contains(in)) {
                    opertor = in;
                    stringA.delete(0, stringA.length());
                    stringB.delete(0, stringB.length());
                    stringA.append(res);
                    if (stringA.length() >= 7) {
                        stringA.setLength(7);
                    }
                    state = EnumState.st4;
                }

                if (in == "del" & stringB.length() != 0) {
                    lastState = EnumState.st6;
                    delCharB();
                }

                break;
            }

            case st7: {
                if (numbers.contains(in) & (stringB.length() < 7)) {
                    stringB.append(in);
                    state = EnumState.st7;
                    CalculateExpression(stringA, stringB, opertor);
                }
                if (actions.contains(in)) {
                    opertor = in;
                    stringA.delete(0, stringA.length());
                    stringB.delete(0, stringB.length());
                    stringA.append(res);
                    if (stringA.length() >= 7) {
                        stringA.setLength(7);
                    }
                    state = EnumState.st4;
                }

                if (in == "del" & stringB.length() == 0) {
                    lastState = EnumState.st3;
                    delCharSign();
                }
                if (in == "del" & stringB.length() != 0) {
                    lastState = EnumState.st6;
                    delCharB();
                }
                break;
            }


        }

    }

    private void delCharA() {
        if (stringA.length() != 0) {
            stringA.setLength(stringA.length() - 1);
            state = lastState;
        }
        if (stringA.length() == 0) {
            state = EnumState.s;
        }

    }

    private void delCharB() {
        if (stringB.length() != 0) {
            stringB.setLength(stringB.length() - 1);
            CalculateExpression(stringA, stringB, opertor);
            state = lastState;
        }

        if (stringB.length() == 0) {
            state = EnumState.st4;
        }
    }

    private void delCharSign() {
        opertor = "";
        state = lastState;
    }


    private void CalculateExpression(StringBuilder stringA, StringBuilder stringB, String operator) {
        if (stringB.length() != 0 && operator != "" && stringA.length() != 0 ){
            try {
                a = Double.parseDouble(stringA.toString());
                b = Double.parseDouble(stringB.toString());

                switch (operator) {

                    case ("+"): {
                        res = a + b;
                        break;
                    }
                    case ("-"): {
                        res = a - b;
                        break;
                    }
                    case ("*"): {
                        res = a * b;
                        break;
                    }
                    case ("/"): {
                        res = a / b;
                        break;
                    }
                }
            }
            catch (Throwable e){
                res = a;
            }
        }
    }
}
