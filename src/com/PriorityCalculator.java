package com;

import java.util.*;
import java.util.function.BinaryOperator;

/*
 *  Использовал Обратную Польскую Запись.
 *  Теперь калькулятор считает с учётом приоритетов операций,
 *  считает с плохой точностью, насколько позволяет Double.
 *  Пока не успел добавить поддержку скобок.
 */

public class PriorityCalculator {
    
    private static BinaryOperator<Double> operation(String op) {
        switch (op) {
            case "+": return (left, right) -> { return left + right; };
            case "-": return (left, right) -> { return left - right; };
            case "*": return (left, right) -> { return left * right; };
            case "/": return (left, right) -> { return left / right; };
            default: return (left, right) -> { return 0d; };
        }
    }

    private static Deque<String> inDeque = new ArrayDeque<>();
    private static Deque<String> outDeque = new ArrayDeque<>();
    private static Stack<String> operationsStack = new Stack<>();

    private static final List<String> operations = List.of("+", "-", "*", "/");  /* (, ) */

    // Метод для сравнения приоритета операций
    private static int compareOperations(String op1, String op2) {
        if(op1 == op2) {
            return 0;
        }
        if(List.of("*", "/").contains(op1) && List.of("+", "-").contains(op2) ) {
            return 1;
        } else {
            return -1;
        }

    }

    public static Double calculate(String expression) {
        Double l = 0d;
        Double r = 0d;
        String[] exArray = expression.split(" ");

        // Формирование очереди вывода и стека операций
        for(String next : exArray) {
            if(operations.contains(next)) {
                if(operationsStack.size() == 0) {
                    operationsStack.push(next);
                }
                else {
                    if(compareOperations(operationsStack.peek(), next) >= 0) {
                        inDeque.offerLast(operationsStack.pop() );
                    }
                    operationsStack.push(next);
                }
            } else {
                try {
                    inDeque.offerLast(next);
                } catch(NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                    break;
                }
            }
        }
        while(operationsStack.size() > 0) {
            inDeque.offerLast(operationsStack.pop() );
        }

        // Рассчет выражения в обратном порядке, проходя по очереди вывода
        while(inDeque.size() > 0) {
            String next = inDeque.removeFirst();

            if(operations.contains(next)) {
                r = Double.valueOf(outDeque.removeLast());
                l = Double.valueOf(outDeque.removeLast());
                outDeque.offerLast(operation(next).apply(l, r).toString() );
            } else {
                outDeque.offerLast(next);
            }
        }

        if(outDeque.size() == 1) {
            return Double.valueOf(outDeque.pop());
        }

        return 0d;
    }



}
