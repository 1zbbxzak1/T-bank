package org.example;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.StringJoiner;

public class B {
    private static final String SPACE_DELIMITER = " ";

    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = System.out;

    private static boolean isValidJournal(int n, int[] a) {
        // Проверяем, чтобы не было снижения снега в последовательности
        for (int i = 1; i < n; i++) {
            // Если текущий день не равен -1, то он должен быть больше предыдущего
            if (a[i] != -1 && a[i - 1] != -1 && a[i] < a[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private static int[] calculateSnowfall(int n, int[] a) {
        int[] snowfall = new int[n];
        int lastKnown = 0; // Снег, который мы можем подтвердить из предыдущих значений

        for (int i = 0; i < n; i++) {
            // Если значение неизвестно, оно должно быть минимально возможным
            if (a[i] == -1) {
                snowfall[i] = 1; // Наименьший возможный вариант
            } else {
                // Если значение меньше последнего известного снега, то последовательность некорректна
                if (i > 0 && a[i] < lastKnown) {
                    return new int[]{};
                }
                snowfall[i] = (i == 0) ? a[i] : a[i] - lastKnown; // Иначе разница между текущим значением и последним известным
            }
            lastKnown += snowfall[i]; // Обновляем "последний известный" снег
        }

        return snowfall;
    }

    public static void main(String[] args) {
        new B().solve();
    }

    private void solve() {
        int n = scanner.nextInt();
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        if (isValidJournal(n, a)) {
            int[] snowfall = calculateSnowfall(n, a);
            if (snowfall.length == 0) {
                out.print("NO");
            } else {
                out.println("YES");
                StringJoiner joiner = new StringJoiner(SPACE_DELIMITER);
                for (int snow : snowfall) {
                    joiner.add(String.valueOf(snow));
                }
                out.print(joiner);
            }
        } else {
            out.print("NO");
        }
    }
}
