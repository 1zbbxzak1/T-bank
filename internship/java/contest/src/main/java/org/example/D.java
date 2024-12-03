package org.example;

import java.io.PrintStream;
import java.util.Scanner;

public class D {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PrintStream out = System.out;

    public static void main(String[] args) {
        new D().solve();
    }

    // Оптимизированное решето для нахождения всех простых чисел до limit
    private static boolean[] generatePrimeSieve(int limit) {
        boolean[] isPrime = new boolean[limit + 1];

        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }
        for (int p = 2; p * p <= limit; p++) {
            if (isPrime[p]) {
                for (int multiple = p * p; multiple <= limit; multiple += p) {
                    isPrime[multiple] = false;
                }
            }
        }
        return isPrime;
    }

    // Проверка на простоту числа n
    private static boolean isPrime(int n, boolean[] primeSieve) {
        if (n < primeSieve.length) {
            return primeSieve[n];
        }
        if (n < 2)
            return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    // Подсчет количества делителей числа n
    private static int calculateDivisorCount(long n) {
        int divisorCount = 0;
        long sqrt = (long) Math.sqrt(n);
        for (long i = 1; i <= sqrt; i++) {
            if (n % i == 0) {
                divisorCount += (i * i == n) ? 1 : 2;
            }
        }
        return divisorCount;
    }

    private void solve() {
        long l = scanner.nextLong();
        long r = scanner.nextLong();

        int limit = (int) Math.sqrt(r);
        boolean[] primeSieve = generatePrimeSieve(limit);

        int primeDivisorCount = 0;

        for (long number = l; number <= r; number++) {
            if (number <= 1 || isPrime((int) number, primeSieve)) continue;

            int divisorCount = calculateDivisorCount(number);

            if (divisorCount > 1 && isPrime(divisorCount, primeSieve)) {
                primeDivisorCount++;
            }
        }

        out.println(primeDivisorCount);
    }
}