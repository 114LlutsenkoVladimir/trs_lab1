package com.example.trs_lab1_1.function;

public class FunctionV6 implements Function{
    @Override
    public double calculate(double x) {
        double exp = Math.exp(x);
        return (exp - 1) / (exp + 1);
    }
}
