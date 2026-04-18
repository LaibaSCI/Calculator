package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private double firstValue = Double.NaN;
    private double secondValue;
    private char ACTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Padding setup
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        display = findViewById(R.id.display);

        // Buttons Setup
        setNumberListeners();
        setOperatorListeners();
    }

    private void setNumberListeners() {
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            if (display.getText().toString().equals("0")) {
                display.setText(b.getText().toString());
            } else {
                display.append(b.getText().toString());
            }
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorListeners() {
        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            firstValue = Double.NaN;
            display.setText("0");
        });

        // Basic Math
        findViewById(R.id.btnPlus).setOnClickListener(v -> prepareCalc('+'));
        findViewById(R.id.btnSub).setOnClickListener(v -> prepareCalc('-'));
        findViewById(R.id.btnMul).setOnClickListener(v -> prepareCalc('*'));
        findViewById(R.id.btnDiv).setOnClickListener(v -> prepareCalc('/'));

        // Scientific
        findViewById(R.id.btnSin).setOnClickListener(v -> {
            double val = Double.parseDouble(display.getText().toString());
            display.setText(String.valueOf(Math.sin(Math.toRadians(val))));
        });

        // Equal
        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            compute();
            display.setText(String.valueOf(firstValue));
            firstValue = Double.NaN;
            ACTION = '0';
        });
    }

    private void prepareCalc(char action) {
        compute();
        ACTION = action;
        display.setText(null);
    }

    private void compute() {
        if (!Double.isNaN(firstValue)) {
            try {
                secondValue = Double.parseDouble(display.getText().toString());
                switch (ACTION) {
                    case '+': firstValue += secondValue; break;
                    case '-': firstValue -= secondValue; break;
                    case '*': firstValue *= secondValue; break;
                    case '/': firstValue /= secondValue; break;
                }
            } catch (Exception e) {
            }
        } else {
            try {
                firstValue = Double.parseDouble(display.getText().toString());
            } catch (Exception e) {
                firstValue = 0;
            }
        }
    }
}