package com.example.maks.calculator;

import android.util.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Stack;

class Counter {
    private String expression;

    public Counter(String expression) {
        this.expression = expression;
    }

    private String translate(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> characterStack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char character = expression.charAt(i);
            if (isOperator(character)) {
                if ((i == 0 && character == '-') || (!characterStack.empty() && expression.charAt(i - 1) == '(' && character == '-')) {
                    output.append('0').append(" ");
                }
                while (!characterStack.empty() && priority(characterStack.peek()) >= priority(character)) {
                    output.append(characterStack.pop()).append(" ");
                }
                characterStack.push(character);
            }
            else if (character == '(') {
                characterStack.push(character);
            }
            else if (character == ')') {
                while (!characterStack.peek().equals('(')){
                    output.append(characterStack.pop()).append(" ");
                }
                characterStack.pop();
            } else {
                if ((Character.isDigit(character) || character == '.') && (i + 1) < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    output.append(character);
                }
                else if (Character.isDigit(character) || character == '.') {
                    output.append(character).append(" ");
                }
                else {
                    output.append(character);
                }
            }
        }
        while (!characterStack.empty()) {
            output.append(characterStack.pop()).append(" ");
        }
        return output.toString();
    }

    private int priority(char operator)
    {
        if (operator == '+' || operator == '-')
            return 1;
        else if (operator == '*' || operator == '/')
            return 2;
        else
            return 0;
    }

    private boolean isOperator(char element)
    {
        return  (element == '*' || element == '-' || element == '/' || element == '+');
    }

    private String findResult(String expression) {
        BigDecimal buf1, buf2, res;
        BigDecimal[] array = new BigDecimal[256];
        int j = 0;
        boolean flag = false;
        int power = -1;
        for (int i = 0; i < expression.length(); i++)
        {
            if (expression.charAt(i) == ' ') {
                flag = false;
                power = -1;
                continue;
            }
            if (expression.charAt(i) == '+') {
                j--;
                buf1 = array[j];
                j--;
                buf2 = array[j];
                res = buf2.add(buf1);
                array[j] = res;
                j++;
            } else  if (expression.charAt(i) == '-') {
                j--;
                buf1 = array[j];
                j--;
                buf2 = array[j];
                res = buf2.subtract(buf1);
                array[j] = res;
                j++;
            } else  if (expression.charAt(i) == '*') {
                j--;
                buf1 = array[j];
                j--;
                buf2 = array[j];
                res = buf1.multiply(buf2);
                array[j] = res;
                j++;
            } else  if (expression.charAt(i) == '/') {
                j--;
                buf1 = array[j];
                j--;
                buf2 = array[j];
                res = buf2.divide(buf1, 5, BigDecimal.ROUND_HALF_UP);
                array[j] = res;
                j++;
            } else if (expression.charAt(i) == '.') {
                flag = true;
            } else {
                if (i != 0) {
                    if (expression.charAt(i - 1) == ' ') {
                        array[j] = new BigDecimal(String.valueOf(expression.charAt(i)));
                        if (i < expression.length() && expression.charAt(i + 1) == ' ') {
                            j++;
                        }
                    } else {
                        if (flag) {
                            array[j] = array[j].add(new BigDecimal(String.valueOf(expression.charAt(i))).multiply(new BigDecimal(Math.pow(10, power))));
                            power--;
                            if (i < expression.length() && expression.charAt(i + 1) == ' ') {
                                j++;
                            }
                        } else {
                            array[j] = array[j].multiply(new BigDecimal(10));
                            array[j] = array[j].add(new BigDecimal(String.valueOf(expression.charAt(i))));
                            if (i < expression.length() && expression.charAt(i + 1) == ' ') {
                                j++;
                            }
                        }
                    }
                } else {
                    array[j] = new BigDecimal(String.valueOf(expression.charAt(i)));
                    if (i < expression.length() && expression.charAt(i + 1) == ' ') {
                        j++;
                    }
                }
            }
        }
        return String.valueOf(array[j - 1].setScale(5, BigDecimal.ROUND_HALF_UP));
    }

    private Fraction findResultForRational(String expression) {
        Fraction buf1, buf2, res;
        Fraction[] array = new Fraction[256];
        int j = 0;
        boolean flag = false;
        int power = 1;
        for (int i = 0; i < expression.length(); i++)
        {
            if (expression.charAt(i) == ' ') {
                flag = false;
                power = 1;
                continue;
            }
            if (expression.charAt(i) == '+') {
                j--;
                buf1 = array[j];
                j--;
                buf2 = array[j];
                res = buf2.sum(buf1);
                array[j] = res;
                j++;
            } else  if (expression.charAt(i) == '-') {
                j--;
                buf1 = array[j];
                j--;
                buf2 = array[j];
                res = buf2.sub(buf1);
                array[j] = res;
                j++;
            } else  if (expression.charAt(i) == '*') {
                j--;
                buf1 = array[j];
                j--;
                buf2 = array[j];
                res = buf1.multiply(buf2);
                array[j] = res;
                j++;
            } else  if (expression.charAt(i) == '/') {
                j--;
                buf1 = array[j];
                j--;
                buf2 = array[j];
                res = buf2.div(buf1);
                array[j] = res;
                j++;
            } else if (expression.charAt(i) == '.') {
                flag = true;
            } else {
                if (i != 0) {
                    if (expression.charAt(i - 1) == ' ') {
                        array[j] = new Fraction(new BigInteger(String.valueOf(expression.charAt(i))), new BigInteger("1"));
                        if (i < expression.length() && expression.charAt(i + 1) == ' ') {
                            j++;
                        }
                    } else {
                        if (flag) {
                            array[j] = array[j].multiply(new Fraction(new BigInteger("10"), new BigInteger("1")));
                            array[j] = array[j].sum(new Fraction(new BigInteger(String.valueOf(expression.charAt(i))), new BigInteger("1")));
                            array[j].setN(array[j].getN().multiply(new BigInteger("10")));
                            power++;
                            if (i < expression.length() && expression.charAt(i + 1) == ' ') {
                                j++;
                            }
                        } else {
                            array[j] = array[j].multiply(new Fraction(new BigInteger("10"), new BigInteger("1")));
                            array[j] = array[j].sum(new Fraction(new BigInteger(String.valueOf(expression.charAt(i))), new BigInteger("1")));
                            if (i < expression.length() && expression.charAt(i + 1) == ' ') {
                                j++;
                            }
                        }
                    }
                } else {
                    array[j] = new Fraction(new BigInteger(String.valueOf(expression.charAt(i))), new BigInteger("1"));
                    if (i < expression.length() && expression.charAt(i + 1) == ' ') {
                        j++;
                    }
                }
            }
        }
        array[j - 1].cut();
        return array[j - 1];
    }

    public String getResult() {
        if (expression.indexOf('?') == -1) {
            return findResult(translate(this.expression));
        } else {
            return parseString();
        }
    }

    public String getResultForRational() {
        if (expression.indexOf('?') == -1) {
            return findResultForRational(translate(this.expression)).toString();
        } else {
            return parseStringRational();
        }
    }

    private String parseString() {
        StringBuilder leftExpression = new StringBuilder();
        StringBuilder rightExpression = new StringBuilder();
        StringBuilder trueResult = new StringBuilder();
        StringBuilder falseResult = new StringBuilder();
        boolean condition = false;
        boolean question = false;
        boolean colon = false;
        char compare = '=';
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '?') {
                question = true;
                continue;
            } else if (expression.charAt(i) == ':') {
                colon = true;
                continue;
            } else if (expression.charAt(i) == '<' || expression.charAt(i) == '>' ) {
                condition = true;
                compare = expression.charAt(i);
                continue;
            }
            if (!condition && !question && !colon) {
                leftExpression.append(expression.charAt(i));
            } else if (!question && !colon && condition) {
                rightExpression.append(expression.charAt(i));
            } else if (!colon && question && condition) {
                trueResult.append(expression.charAt(i));
            } else if (colon && question && condition){
                falseResult.append(expression.charAt(i));
            } else {
                return "Ошибка записи выражения!";
            }
        }
        BigDecimal left = new BigDecimal(findResult(translate(String.valueOf(leftExpression))));
        BigDecimal right = new BigDecimal(findResult(translate(String.valueOf(rightExpression))));
        BigDecimal trueRes = new BigDecimal(findResult(translate(String.valueOf(trueResult))));
        BigDecimal falseRes = new BigDecimal(findResult(translate(String.valueOf(falseResult))));
        Fraction leftRational = findResultForRational(translate(String.valueOf(leftExpression)));
        Fraction rightRational = findResultForRational(translate(String.valueOf(rightExpression)));
        Fraction trueRational = findResultForRational(translate(String.valueOf(trueResult)));
        Fraction falseRational = findResultForRational(translate(String.valueOf(falseResult)));
        if (compare == '>') {
            if (left.compareTo(right) > 0) {
                return String.valueOf(trueRes.setScale(5, BigDecimal.ROUND_HALF_UP));
            } else {
                return String.valueOf(falseRes.setScale(5, BigDecimal.ROUND_HALF_UP));
            }
        } else if (compare == '<') {
            if (left.compareTo(right) > 0) {
                return String.valueOf(falseRes.setScale(5, BigDecimal.ROUND_HALF_UP));
            } else {
                return String.valueOf(trueRes.setScale(5, BigDecimal.ROUND_HALF_UP));
            }
        } else {
            return "Ошибка записи выражения!";
        }
    }

    private String parseStringRational() {
        StringBuilder leftExpression = new StringBuilder();
        StringBuilder rightExpression = new StringBuilder();
        StringBuilder trueResult = new StringBuilder();
        StringBuilder falseResult = new StringBuilder();
        boolean condition = false;
        boolean question = false;
        boolean colon = false;
        char compare = '=';
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '?') {
                question = true;
                continue;
            } else if (expression.charAt(i) == ':') {
                colon = true;
                continue;
            } else if (expression.charAt(i) == '<' || expression.charAt(i) == '>' ) {
                condition = true;
                compare = expression.charAt(i);
                continue;
            }
            if (!condition && !question && !colon) {
                leftExpression.append(expression.charAt(i));
            } else if (!question && !colon && condition) {
                rightExpression.append(expression.charAt(i));
            } else if (!colon && question && condition) {
                trueResult.append(expression.charAt(i));
            } else if (colon && question && condition){
                falseResult.append(expression.charAt(i));
            } else {
                return "Ошибка записи выражения!";
            }
        }
        Fraction leftRational = findResultForRational(translate(String.valueOf(leftExpression)));
        Fraction rightRational = findResultForRational(translate(String.valueOf(rightExpression)));
        Fraction trueRational = findResultForRational(translate(String.valueOf(trueResult)));
        Fraction falseRational = findResultForRational(translate(String.valueOf(falseResult)));
        if (compare == '>') {
            if (leftRational.more(rightRational)) {
                return trueRational.toString();
            } else {
                return falseRational.toString();
            }
        } else if (compare == '<') {
            if (leftRational.less(rightRational)) {
                return trueRational.toString();
            } else {
                return falseRational.toString();
            }
        } else {
            return "Ошибка записи выражения!";
        }
    }
}
