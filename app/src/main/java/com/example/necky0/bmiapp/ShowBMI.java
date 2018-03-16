package com.example.necky0.bmiapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowBMI extends AppCompatActivity {

    public static final String ERROR = "Are you Human?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bmi);

        Intent intent = getIntent();
        double result = intent.getDoubleExtra(MainActivity.EXTRA_MESSAGE, 0);

        setText(result);
        setBackground(result);
    }

    private void setText(double result) {
        TextView result_box = findViewById(R.id.textView);
        if (result != 0) {
            result_box.setText(BMICount.toString(result));
        } else {
            result_box.setText(ERROR);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void setBackground(double result) {
        ConstraintLayout layout = findViewById(R.id.background);

        if (result < 18.5) {
            layout.setBackgroundResource(R.color.one);
        } else if ( result < 25) {
            layout.setBackgroundResource(R.color.two);
        } else {
            layout.setBackgroundResource(R.color.three);
        }
    }
}
