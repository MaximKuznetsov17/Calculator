package com.example.maks.calculator;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public StringBuilder expression;
    public Boolean answer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.expression = new StringBuilder();

        TextView one = findViewById(R.id.one);
        TextView two = findViewById(R.id.two);
        TextView three = findViewById(R.id.three);
        TextView four = findViewById(R.id.four);
        TextView five = findViewById(R.id.five);
        TextView six = findViewById(R.id.six);
        TextView seven = findViewById(R.id.seven);
        TextView eight = findViewById(R.id.eight);
        TextView nine = findViewById(R.id.nine);
        TextView zero = findViewById(R.id.zero);
        TextView clear = findViewById(R.id.clear);
        TextView backspace = findViewById(R.id.backspace);
        TextView plus = findViewById(R.id.plus);
        TextView minus = findViewById(R.id.minus);
        TextView multiply = findViewById(R.id.multiply);
        TextView div = findViewById(R.id.div);
        TextView dot = findViewById(R.id.dot);
        TextView equals = findViewById(R.id.equals);
        TextView left_bracket = findViewById(R.id.left_bracket);
        TextView right_bracket = findViewById(R.id.right_bracket);
        TextView question = findViewById(R.id.question);
        TextView more = findViewById(R.id.more);
        TextView less = findViewById(R.id.less);
        TextView colon = findViewById(R.id.colon);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        div.setOnClickListener(this);
        dot.setOnClickListener(this);
        equals.setOnClickListener(this);
        more.setOnClickListener(this);
        less.setOnClickListener(this);
        colon.setOnClickListener(this);
        question.setOnClickListener(this);
        left_bracket.setOnClickListener(this);
        right_bracket.setOnClickListener(this);
        clear.setOnClickListener(this);
        backspace.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.one):
                this.expression.append(1);
                break;
            case (R.id.two):
                this.expression.append(2);
                break;
            case (R.id.three):
                this.expression.append(3);
                break;
            case (R.id.four):
                this.expression.append(4);
                break;
            case (R.id.five):
                this.expression.append(5);
                break;
            case (R.id.six):
                this.expression.append(6);
                break;
            case (R.id.seven):
                this.expression.append(7);
                break;
            case (R.id.eight):
                this.expression.append(8);
                break;
            case (R.id.nine):
                this.expression.append(9);
                break;
            case (R.id.zero):
                this.expression.append(0);
                break;
            case (R.id.minus):
                this.expression.append('-');
                break;
            case (R.id.plus):
                this.expression.append('+');
                break;
            case (R.id.multiply):
                this.expression.append('*');
                break;
            case (R.id.div):
                this.expression.append('/');
                break;
            case (R.id.left_bracket):
                this.expression.append('(');
                break;
            case (R.id.right_bracket):
                this.expression.append(')');
                break;
            case (R.id.clear):
                this.expression = new StringBuilder();
                break;
            case (R.id.backspace):
                if (expression.length() != 0)
                    expression.deleteCharAt(expression.length() - 1);
                break;
            case (R.id.more):
                this.expression.append('>');
                break;
            case (R.id.less):
                this.expression.append('<');
                break;
            case (R.id.question):
                this.expression.append('?');
                break;
            case (R.id.colon):
                this.expression.append(':');
                break;
            case (R.id.dot):
                this.expression.append('.');
                break;
            case (R.id.equals):
                try {
                    Counter res = new Counter(expression.toString());
                    TextView outputDecimal = findViewById(R.id.outputDecimal);
                    TextView outputRational = findViewById(R.id.outputRational);
                    outputDecimal.setText(res.getResult());
                    outputRational.setText(res.getResultForRational());
                } catch (Exception e) {
                    answer = true;
                    TextView outputDecimal = findViewById(R.id.outputDecimal);
                    TextView outputRational = findViewById(R.id.outputRational);
                    outputDecimal.setText(R.string.error);
                    outputRational.setText(R.string.error);
                }
                break;
        }
        TextView input = findViewById(R.id.input);
        input.setText(expression);
    }
}
