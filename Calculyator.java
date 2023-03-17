package CalculatorProstDr;

import java.util.*;

import static CalculatorProstDr.Drobb.reduce;
import static CalculatorProstDr.Drobb.tryParse;

public class Calculyator {
    private static int getPriority(String operation) { //определяем последовательность операций
        return operation.equals("/") || operation.equals("*") ? 1 : 0;
    }
    private static void sort(List<Map.Entry<Integer, String>> operations) {
        for (int i = 0; i < operations.size() - 1; i++) {
            for (int j = 0; j < operations.size() - i - 1; j++) {
                if (getPriority(operations.get(i).getValue()) < getPriority(operations.get(i + 1).getValue())) {
                    Map.Entry<Integer, String> temp = operations.get(i);
                    operations.set(i, operations.get(i + 1));
                    operations.set(i + 1, temp);
                }
            }
        }
    }
    private static Number calculateOneOperation(Drobb one, String operation, Drobb two) { //воспроизводим операции между дробями
        switch (operation) {
            case "*" -> {
                return Drobb.multiply(one, two);
            }
            case "-" -> {
                return Drobb.subtract(one, two);
            }
            case "+" -> {
                return Drobb.add(one, two);
            }
            case "/" -> {
                return Drobb.division(one, two);
            }
            default -> throw new IllegalArgumentException("Некорректная запись.");
        }
    }
        public static void isCorrectBracket(String operation) { //проверяем положение скобок
            operation = operation.replace("[0-9]*", "");
            Stack<Character> stack = new Stack<>();
            for(int i = 0; i < operation.length(); i++) {
                if(operation.charAt(i) == '(') {
                    stack.push(operation.charAt(i));
                } else if(operation.charAt(i) == ')') {
                    if(stack.isEmpty() || stack.peek() != '(') throw new IllegalArgumentException("Некорректная запись.");
                    stack.pop();
                }
            }
            if(!stack.isEmpty()) throw new IllegalArgumentException("Некорректная запись.");
        }
        public static Drobb calculate(String operation) throws IllegalArgumentException { //выполняется подсчет выражения
            //List<String> decimals = new ArrayList<>();
            Drobb result = null;
            isCorrectBracket(operation);
            while(true) { //цикл обработки выражения и подсчет, с использованием публичных методов
                String[] brackets = operation.split("\\(|\\)");
                if(brackets.length == 0) break;
                for (String bracket : brackets) {
                    if (bracket.isEmpty() || bracket.equals(" ")) continue;
                    List<Map.Entry<Integer, String>> operations = new ArrayList<>();
                    List<String> decimals = new ArrayList<>();
                    decimals.addAll(Arrays.asList(bracket.split(" ")));
                    decimals.removeIf(a-> a.equals("") || a.equals(" "));
                    for (int i = 0; i < decimals.size(); i++) {
                        if (!decimals.get(i).equals("") && !tryParse(decimals.get(i))) {
                            operations.add(Map.entry(i, decimals.get(i)));
                        } else if (i > 0 && tryParse(decimals.get(i - 1))) {
                            decimals.add(i, "*");
                            operations.add(Map.entry(i++, "*"));
                        }
                    }
                    sort(operations);
                    for (Map.Entry<Integer, String> op : operations) {
                        int index = op.getKey();
                        Drobb one = Drobb.parse(decimals.get(index - 1));
                        Drobb two = Drobb.parse(decimals.get(index + 1));
                        result = (Drobb) calculateOneOperation(one, op.getValue(), two);
                        decimals.set(index + 1, result.toString());
                        decimals.set(index - 1, result.toString());
                    }
                    if(brackets.length == 1 && brackets[brackets.length - 1].equals(bracket)) return reduce(result);
                    operation = operation.replace("(" + bracket + ")", result.toString() + " ");
                    operation = operation.replace(bracket, result.toString());
                    break;
                }
            }
            return reduce(result);
        }
    }