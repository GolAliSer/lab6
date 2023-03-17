package CalculatorProstDr;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {  //цикл ввода выражений
            try {
                System.out.println("Введите выражение или выход для завершения работы: ");
                String operation = in.nextLine();
                if(operation.equals("выход")) {
                    break;
                }
                System.out.println(Calculyator.calculate(operation));
            } catch (Exception ex) {
                System.out.println("Что-то не так: " + ex.getMessage());
            }
        }
        System.out.println("Работа завершена");
        in.close();
    }
}
