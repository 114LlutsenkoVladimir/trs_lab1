package com.example.trs_lab1_1.test;

import com.example.trs_lab1_1.function.Function;
import com.example.trs_lab1_1.function.IntegralCalculator;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;


public class IntegralCalculatorTest {
    @Test
    public void constantFunction() throws ExecutionException, InterruptedException, ExecutionException {
        IntegralCalculator calc = new IntegralCalculator();
        Function f = x -> 1.0;
        double a = 2.0, b = 5.0;
        int n = 10_000;
        double expected = b - a;
        double actual = calc.calculateIntegralThreads(a, b, n, f);
        assertEquals(expected, actual, 1e-10);
    }

    // интеграл f(x)=x  на [0,1] = 1/2
    @Test
    public void linearFunction() throws ExecutionException, InterruptedException {
        IntegralCalculator calc = new IntegralCalculator();
        Function f = x -> x;
        double a = 0.0, b = 1.0;
        int n = 10_000;
        double expected = 0.5;
        double actual = calc.calculateIntegralThreads(a, b, n, f);
        assertEquals(expected, actual, 1e-8);
    }

    // интеграл f(x)=x^2  на [0,1] = 1/3
    @Test
    public void quadraticFunction() throws ExecutionException, InterruptedException {
        IntegralCalculator calc = new IntegralCalculator();
        Function f = x -> x * x;
        double a = 0.0, b = 1.0;
        int n = 10_000;
        double expected = 1.0 / 3.0;
        double actual = calc.calculateIntegralThreads(a, b, n, f);
        assertEquals(expected, actual, 1e-7);
    }

    // интеграл f(x)=sin(x)  на [0,π] = 2
    @Test
    public void sineFunction() throws ExecutionException, InterruptedException {
        IntegralCalculator calc = new IntegralCalculator();
        Function f = Math::sin;
        double a = 0.0, b = Math.PI;
        int n = 20_000;
        double expected = 2.0;
        double actual = calc.calculateIntegralThreads(a, b, n, f);
        assertEquals(expected, actual, 1e-6);
    }

    // интеграл f(x)=cos(x)  на [0,π/2] = 1
    @Test
    public void cosineFunction() throws ExecutionException, InterruptedException {
        IntegralCalculator calc = new IntegralCalculator();
        Function f = Math::cos;
        double a = 0.0, b = Math.PI / 2;
        int n = 20_000;
        double expected = 1.0;
        double actual = calc.calculateIntegralThreads(a, b, n, f);
        assertEquals(expected, actual, 1e-6);
    }

    // интеграл f(x)=exp(x)  на [0,1] = e - 1
    @Test
    public void exponentialFunction() throws ExecutionException, InterruptedException {
        IntegralCalculator calc = new IntegralCalculator();
        Function f = Math::exp;
        double a = 0.0, b = 1.0;
        int n = 20_000;
        double expected = Math.E - 1.0;
        double actual = calc.calculateIntegralThreads(a, b, n, f);
        assertEquals(expected, actual, 1e-6);
    }

    // интеграл f(x)=1/(1+x^2)  на [0,1] = arctan(1)-arctan(0)=π/4
    @Test
    public void rationalFunction() throws ExecutionException, InterruptedException {
        IntegralCalculator calc = new IntegralCalculator();
        Function f = x -> 1.0 / (1.0 + x * x);
        double a = 0.0, b = 1.0;
        int n = 20_000;
        double expected = Math.PI / 4.0;
        double actual = calc.calculateIntegralThreads(a, b, n, f);
        assertEquals(expected, actual, 1e-6);
    }
}
