package org.example;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.TreeSet;

public class A {
    private static final String COMMA_DELIMITER = ",";
    private static final String DASH_DELIMITER = "-";
    private static final String SPACE_DELIMITER = " ";
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = System.out;

    public static void main(String[] args) {
        new A().solve();
    }

    public void solve() {
        String input = scanner.nextLine();
        scanner.close();

        TreeSet<Integer> set = new TreeSet<>();
        String[] tokens = input.split(COMMA_DELIMITER);

        for (String token : tokens) {
            if (token.contains(DASH_DELIMITER)) {
                String[] subTokens = token.split(DASH_DELIMITER);
                int start = Integer.parseInt(subTokens[0]);
                int end = Integer.parseInt(subTokens[1]);

                for (int i = start; i <= end; i++) {
                    set.add(i);
                }
            } else {
                set.add(Integer.parseInt(token));
            }
        }

        StringJoiner joiner = new StringJoiner(SPACE_DELIMITER);
        set.forEach(num -> joiner.add(num.toString()));

        out.print(joiner);
    }
}
