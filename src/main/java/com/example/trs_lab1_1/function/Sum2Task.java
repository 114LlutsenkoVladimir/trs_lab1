package com.example.trs_lab1_1.function;

import java.util.concurrent.Callable;

public class Sum2Task implements Callable<Double> {

    int start;
    int end;
    double h;
    double a;
    Function function;
    public Sum2Task(int start, int end, double h, double a, Function function) {
        this.start = start;
        this.end = end;
        this.h = h;
        this.a = a;
        this.function = function;
    }
    @Override
    public Double call() throws Exception {
        double sum = 0;
        for (int i = start; i <= end; ++i) {
            double mid = a + (i - 0.5) * h;
            sum += function.calculate(mid);
        }
        return sum;
    }
}
