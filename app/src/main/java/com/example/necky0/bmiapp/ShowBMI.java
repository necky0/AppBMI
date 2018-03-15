package com.example.necky0.bmiapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowBMI extends AppCompatActivity {

    public static final String ERROR = "Are you Human?";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bmi);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        double result = intent.getDoubleExtra(MainActivity.EXTRA_MESSAGE, 0);


        TextView result_box = findViewById(R.id.textView);
        if (result != 0) {
            result_box.setText(String.valueOf(result));
        } else {
            result_box.setText(ERROR);
        }

        ConstraintLayout layout = findViewById(R.id.background);

        if (result < 18.5) {
            layout.setBackgroundColor(R.color.one);
        } else if ( result < 25) {
            layout.setBackgroundColor(R.color.two);
        } else {
            layout.setBackgroundColor(R.color.three);
        }

    }
}
