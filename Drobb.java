package CalculatorProstDr;

public class Drobb implements Number { // класс дробей с использованием интерфейса от класса чисел
    private final int chislitel;
    private final int znamenatel;

    public Drobb(int chislitel, int znamenatel) throws IllegalArgumentException{ // собираем дробь
        this.chislitel = chislitel;
        this.znamenatel = znamenatel;
    }
    public static Drobb parse(String dec) throws IllegalArgumentException { //разделяем строку на элементы, выискивая дроби
        String[] nums = dec.split("/");
        try {
            int denominator = Integer.parseInt(nums[1]);
            if (denominator == 0) throw new IllegalArgumentException("Знаменатель не должен быть нулем");
            int nominator = Integer.parseInt(nums[0]);
            return new Drobb(nominator, denominator);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Некорректная запись.");
        }
    }
    public static boolean tryParse(String dec) {
        String[] nums = dec.split("/");
        return nums.length == 2;
    }

    //сложение: устанавливаем метод и применяем его
    public double sum(Number number) throws IllegalArgumentException {
        if (number == null) throw new IllegalArgumentException("Некорректная запись.");
        if (!(number instanceof Drobb)) throw new IllegalArgumentException("Некорректная запись.");
        return this.getValue() + number.getValue();
    }
    public static Drobb add(Drobb number, Drobb number2) throws IllegalArgumentException {
        if (number == null || number2 == null)
            throw new IllegalArgumentException("Некорректная запись.");

        if (number.znamenatel != number2.znamenatel) {
            return new Drobb(number.chislitel * number2.znamenatel
                    + number2.chislitel * number.znamenatel, number.znamenatel * number2.znamenatel);
        } else {
            return new Drobb(number.chislitel + number2.chislitel, number.znamenatel);
        }
    }
//вычитание: устанавливаем метод и применяем его
    public double sub(Number number) {
        if (number == null) throw new IllegalArgumentException("Некорректная запись.");
        if (!(number instanceof Drobb)) throw new IllegalArgumentException("Некорректная запись.");
        return getValue() - number.getValue();
    }
    public static Drobb subtract(Drobb number, Drobb number2) throws IllegalArgumentException {
        if (number == null || number2 == null)
            throw new IllegalArgumentException("Некорректная запись.");

        if (number.znamenatel != number2.znamenatel) {
            return new Drobb(number.chislitel * number2.znamenatel
                    - number2.chislitel * number.znamenatel, number.znamenatel * number2.znamenatel);
        } else {
            return new Drobb(number.chislitel - number2.chislitel, number.znamenatel);
        }
    }


//умножение: устанавливаем метод и применяем его
    public double multy(Number number) throws IllegalArgumentException{
        if (number == null) throw new IllegalArgumentException("Некорректная запись.");
        if (!(number instanceof Drobb)) throw new IllegalArgumentException("Некорректная запись.");
        return getValue() * number.getValue();
    }
    public static Drobb multiply(Drobb number, Drobb number2) throws IllegalArgumentException{
        if (number == null || number2 == null)
            throw new IllegalArgumentException("Некорректная запись.");
        return new Drobb(number.chislitel * number2.chislitel, number.znamenatel * number2.znamenatel);
    }

//деление: устанавливаем метод и применяем его
    public double divide(Number number) {
        if (number == null) throw new IllegalArgumentException("Некорректная запись.");
        if (!(number instanceof Drobb)) throw new IllegalArgumentException("Некорректная запись.");
        if(number.getValue() == 0) throw new ArithmeticException("Деление на 0");
        return getValue() / number.getValue();
    }
    public static Drobb division(Drobb number, Drobb number2) throws IllegalArgumentException{
        if (number == null || number2 == null)
            throw new IllegalArgumentException("Некорректная запись.");
        if(number2.getValue() == 0) throw new ArithmeticException("Деление на 0");
        return new Drobb(number.chislitel * number2.znamenatel, number.znamenatel * number2.chislitel);
    }

    public static Drobb reduce(Drobb decimal) { //сокращаем/уменьшаем дробь
        int chislitel = decimal.chislitel;
        int znamenatel = decimal.znamenatel;
        for(int i = 2; i <= Math.abs(znamenatel); i++) {
            if(znamenatel % i == 0 && chislitel % i == 0) {
                chislitel /= i;
                znamenatel /= i;
                i = 1;
            }
        }
        if(chislitel < 0 && znamenatel < 0) return new Drobb(-chislitel, -znamenatel);
        if(chislitel < 0 || znamenatel < 0) return new Drobb(-Math.abs(chislitel), Math.abs(znamenatel));
        return new Drobb(chislitel, znamenatel);
    }
    public double getValue() throws ArithmeticException { // получаем значение дроби - деление
        return (double) chislitel / (double) znamenatel;
    }
    public String toString() { //преобразуем числитель и знаменатель в ввид дроби
        return chislitel + "/" + znamenatel;
    }
}