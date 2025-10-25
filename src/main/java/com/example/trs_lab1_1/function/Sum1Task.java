package com.example.trs_lab1_1.function;

public class Sum1Task implements Runnable {
    int start;
    int end;
    double h;
    double a;
    double result = 0;
    Function function;

    public Sum1Task(int start, int end, double h, double a, Function function) {
        this.start = start;
        this.end = end;
        this.h = h;
        this.a = a;
        this.function = function;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; ++i) {
            result += function.calculate(a + h * i);
        }
    }

    public double getResult() {
        return result;
    }
}
