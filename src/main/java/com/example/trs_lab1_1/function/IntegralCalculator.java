package com.example.trs_lab1_1.function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IntegralCalculator {
    public double calculateIntegral(double a, double b, int n, Function f) {
        double h = (b-a)/n;

        double sum1 = 0;
        for (int i = 1; i < n; ++i) {
            double x = a + h * i;
            sum1 += f.calculate(x);
        }

        double sum2 = 0;
        double prevX = a;
        for (int i = 1; i <= n; ++i) {
            double x = a + h * i;
            sum2 += f.calculate( (prevX + x) / 2 );
            prevX = x;
        }

        return h/3 * (0.5 * f.calculate(a) + sum1 + 2 * sum2 + 0.5 * f.calculate(a + n * h));
    }

    public double calculateIntegralThreads(double a, double b, int n, Function f)
            throws ExecutionException, InterruptedException {

        double h = (b - a) / n;

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        List<Future<Double>> futuresSum1 = new ArrayList<>();
        List<Future<Double>> futuresSum2 = new ArrayList<>();

        List<int[]> ranges1 = splitInclusive(1, n - 1, cores);
        List<int[]> ranges2 = splitInclusive(1, n, cores);

        for (int[] rg : ranges1)
            futuresSum1.add(pool.submit(new Sum1Task(rg[0], rg[1], h, a, f)));

        for (int[] rg : ranges2)
            futuresSum2.add(pool.submit(new Sum2Task(rg[0], rg[1], h, a, f)));

        double sum1 = 0.0;
        for (Future<Double> fut : futuresSum1)
            sum1 += fut.get();

        double sum2 = 0.0;
        for (Future<Double> fut : futuresSum2)
            sum2 += fut.get();

        pool.shutdown();

        double fa = f.calculate(a);
        double fb = f.calculate(a + n * h);
        return h / 3 * (0.5 * fa + sum1 + 2 * sum2 + 0.5 * fb);
    }

    static List<int[]> splitInclusive(int start, int end, int parts) {
        int N = end - start + 1;
        parts = Math.max(1, Math.min(parts, N));
        int base = N / parts;
        int rem  = N % parts;

        List<int[]> ranges = new ArrayList<>(parts);
        int cur = start;
        for (int k = 0; k < parts; k++) {
            int size = base + (k < rem ? 1 : 0);
            int from = cur;
            int to   = cur + size - 1;
            ranges.add(new int[]{from, to});
            cur = to + 1;
        }
        return ranges;
    }

}
