package com.example.necky0.bmiapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class ShowBMI extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Result";
    private static final String ERROR = "Are you Human?";
    private static final String FORMAT = "%.2f";

    private TextView result_box;
    private ConstraintLayout layout;

    private double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bmi);

        initIntent();

        initFindViews();

        setText(result);
        setBackground(result);
    }

    public void initIntent(){
        Intent intent = getIntent();
        result = intent.getDoubleExtra(EXTRA_MESSAGE, 0);
    }

    public static void start(Context context, double result) {
        Intent starter = new Intent(context, ShowBMI.class);
        starter.putExtra(EXTRA_MESSAGE, result);
        context.startActivity(starter);
    }

    private void initFindViews(){
        result_box = findViewById(R.id.result_box);
        layout = findViewById(R.id.background);
    }

    private void setText(double result) {
        if (BMICount.validResult(result)) {
            result_box.setText(String.format(Locale.US, FORMAT, result));
        } else {
            result_box.setText(ERROR);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void setBackground(double result) {
        BMIClassification classification = BMICount.getBMIClassification(result);

        if (classification == BMIClassification.TOO_LOW) {
            layout.setBackgroundResource(R.color.light_red);
        } else if (classification == BMIClassification.OPTIMAL) {
            layout.setBackgroundResource(R.color.green);
        } else if (classification == BMIClassification.TOO_HIGH) {
            layout.setBackgroundResource(R.color.dark_red);
        } else {
            layout.setBackgroundResource(R.color.default_color);
        }
    }
}
