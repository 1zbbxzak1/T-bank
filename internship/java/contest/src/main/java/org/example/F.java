package org.example;

import java.io.PrintStream;
import java.util.*;

// чуть-чуть поправить
public class F {
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = System.out;

    public static void main(String[] args) {
    }

    private void solve() {
        int n = scanner.nextInt();  // Количество процессов
        scanner.nextLine();  // Переход на следующую строку после чтения числа

        // Массивы для хранения времени выполнения, времени завершения, зависимостей и степени входа
        List<Integer>[] dependencies = new ArrayList[n];
        long[] executionTime = new long[n];
        long[] completionTime = new long[n];
        int[] indegree = new int[n];

        // Инициализация массива зависимостей
        for (int i = 0; i < n; i++) {
            dependencies[i] = new ArrayList<>();
        }

        // Чтение данных для каждого процесса
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();  // Читаем строку
            String[] tokens = line.split(" ");  // Разбиваем строку на части
            executionTime[i] = Long.parseLong(tokens[0]);  // Время выполнения процесса

            // Считываем зависимости (если есть)
            for (int j = 1; j < tokens.length; j++) {
                int dep = Integer.parseInt(tokens[j]) - 1;  // Номера зависимостей (индексы с 1, поэтому уменьшаем на 1)
                dependencies[dep].add(i);
                indegree[i]++;  // Увеличиваем степень входа для текущего процесса
            }
        }

        // Очередь для топологической сортировки (для обработки процессов без зависимостей)
        Queue<Integer> queue = new LinkedList<>();

        // Инициализация очереди и времени завершения для процессов без зависимостей
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
                completionTime[i] = executionTime[i];
            }
        }

        // Топологическая сортировка с вычислением времени завершения
        while (!queue.isEmpty()) {
            int process = queue.poll();
            for (int dependent : dependencies[process]) {
                completionTime[dependent] = Math.max(completionTime[dependent], completionTime[process] + executionTime[dependent]);
                indegree[dependent]--;
                if (indegree[dependent] == 0) {
                    queue.add(dependent);
                }
            }
        }

        // Время, за которое завершатся все процессы
        long result = 0;
        for (long time : completionTime) {
            result = Math.max(result, time);  // Получаем максимальное время завершения всех процессов
        }

        out.println(result);  // Выводим результат
    }
}
