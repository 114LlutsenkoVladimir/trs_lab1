package com.example.trs_lab1_1.function;

import java.util.ArrayList;
import java.util.List;



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

    public double calculateIntegralThreads(double a, double b, int n, Function f, int threads)
            throws InterruptedException {

        double h = (b - a) / n;

        List<Sum1Task> sum1RunnableObjects = new ArrayList<>();
        List<Sum2Task> sum2RunnableObjects = new ArrayList<>();

        List<int[]> ranges1 = splitInclusive(1, n - 1, threads);
        List<int[]> ranges2 = splitInclusive(1, n, threads);

        ranges1.forEach(r -> sum1RunnableObjects.add(new Sum1Task(r[0], r[1], h, a, f)));
        ranges2.forEach(r -> sum2RunnableObjects.add(new Sum2Task(r[0], r[1], h, a, f)));

        List<Thread> sum1Threads = sum1RunnableObjects.stream().map(rn -> new Thread(rn)).toList();
        List<Thread> sum2Threads = sum2RunnableObjects.stream().map(rn -> new Thread(rn)).toList();

        sum1Threads.forEach(Thread::start);
        sum2Threads.forEach(Thread::start);

        for (Thread t : sum1Threads) t.join();
        for (Thread t : sum2Threads) t.join();

        double sum1 = sum1RunnableObjects.stream().mapToDouble(Sum1Task::getResult).sum();
        double sum2 = sum2RunnableObjects.stream().mapToDouble(Sum2Task::getResult).sum();

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
