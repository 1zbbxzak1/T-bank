package org.example;

public class Employee {

    private final String name;
    private final int age;

    public Employee(String name, int age) {

        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        int sum = 10;
        for (int i = 0; i <= 10; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
