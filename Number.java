package CalculatorProstDr;

public interface Number { //устанавливаем публичные методы и константы, то что нужно реализовать
    double sum(Number number); //сумма
    double sub(Number number); //вычитание
    double multy(Number number); //умножение
    double divide(Number number); //деление
    double getValue(); //значение дроби
}
