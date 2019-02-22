package com.example.maks.calculator;

import java.math.BigInteger;

public class Fraction {
    private BigInteger m;
    private BigInteger n;

    // Конструкторы класса

    public Fraction(BigInteger m, BigInteger n) {
        this.m = m;
        this.n = n;
        this.cut();
    }

    public Fraction(Fraction rational){
        this.m = rational.m;
        this.n = rational.n;
        this.cut();
    }

    //Сокращение дробей

    public void cut() {
        BigInteger maxDenominator = maxDenominator(this.m.abs(), this.n.abs());
        this.m = this.m.divide(maxDenominator);
        this.n = this.n.divide(maxDenominator);
        if (this.n.compareTo(new BigInteger("0")) < 0 && this.m.compareTo(new BigInteger("0")) < 0) {
            this.m = this.m.abs();
            this.n = this.n.abs();
        } else if (this.n.compareTo(new BigInteger("0")) < 0 && this.m.compareTo(new BigInteger("0")) > 0) {
            this.m = this.m.multiply(new BigInteger("-1"));
            this.n = this.n.abs();
        }
    }

    private BigInteger maxDenominator(BigInteger a, BigInteger b) {
        return b.equals(new BigInteger("0")) ? a : maxDenominator(b, a.mod(b));
    }

    //Геттеры-сеттеры

    public BigInteger getM() {
        return m;
    }

    public BigInteger getN() {
        return n;
    }

    public void setM(BigInteger m) {
        this.m = m;
        this.cut();
    }

    public void setN(BigInteger n) {
        this.n = n;
        this.cut();
    }

    //Суммирование дробей

    public Fraction sum(Fraction rational) {
        BigInteger a = m.multiply(rational.getN()).add(rational.getM().multiply(n));
        BigInteger b = n.multiply(rational.getN());
        Fraction sum = new Fraction(a ,b);
        sum.cut();
        return sum;
    }

    //Перемножнение дробей

    public Fraction multiply(Fraction rational) {
        BigInteger newM = this.m.multiply(rational.m);
        BigInteger newN = this.n.multiply(rational.n);
        return new Fraction(newM, newN);
    }

    //Вычитание дробей

    public Fraction sub(Fraction rational) {
        return this.sum(rational.multiply(new Fraction(new BigInteger("-1"), new BigInteger("1"))));
    }

    //Деление дробей

    public Fraction div(Fraction rational) {
        if (rational.equals(new Fraction(new BigInteger("0"), new BigInteger("1")))) {
            return null;
        }
        return this.multiply(new Fraction(rational.n, rational.m));
    }

    //Максимальная/минимальная дробь

    public Fraction maximum(Fraction rational) {
        if (rational.m.divide(rational.n).compareTo(this.m.divide(this.n)) > 0) {
            return rational;
        } else return this;
    }

    public Fraction minimum(Fraction rational) {
        if (rational.m.divide(rational.n).compareTo(this.m.divide(this.n)) > 0) {
            return this;
        } else return rational;
    }

    //Сравнение дробей

    public boolean less(Fraction rational) {
        return this.m.multiply(rational.n).compareTo(this.n.multiply(rational.m)) < 1;
    }

    public boolean more(Fraction rational) {
        return this.m.multiply(rational.n).compareTo(this.n.multiply(rational.m)) > 1;
    }

    @Override
    public String toString() {
        return m + "/" + n;
    }

    //Переписываем метод equals "под себя"

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Fraction buf = (Fraction) object;
        return m == buf.m && n == buf.n;
    }
}
