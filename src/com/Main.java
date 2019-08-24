package com;

public class Main {

    public static void main(String[] args) {
        String expression = "5 * 2 + 10 * 2 + 12.2";
        String expression2 = "1 + 1 + 1 + 0.1";
        String expression3 = "0.2 * 1 + 0.2 / 2";

        System.out.println( PriorityCalculator.calculate(expression) );
        System.out.println( PriorityCalculator.calculate(expression2) );
        System.out.println( PriorityCalculator.calculate(expression3) );

    }
}
