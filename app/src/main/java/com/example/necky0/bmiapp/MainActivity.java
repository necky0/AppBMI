package com.example.necky0.bmiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Result";

    Button button_count;
    EditText mass_box;
    EditText height_box;
    TextView result_box;
    Switch aSwitch;

    double mass;
    double height;
    double result;

    boolean isEnglishUnit;

    SharedPreferences sp;
    SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_count = (Button) findViewById(R.id.button_count);
        button_count.setOnClickListener(myhandler);
        mass_box = findViewById(R.id.mass);
        height_box = findViewById(R.id.height);
        result_box = findViewById(R.id.result);
        aSwitch = findViewById(R.id.switch_unit);

        sp = getSharedPreferences("Dane BMI", MODE_PRIVATE);
        spe = sp.edit();

        mass = (double)sp.getFloat("mass", 0);
        height = (double)sp.getFloat("height",0);
        isEnglishUnit = sp.getBoolean("switch", false);

        spe.apply();

        if (mass != 0) mass_box.setText(String.valueOf(mass));
        if (height != 0) height_box.setText(String.valueOf(height));
        if (isEnglishUnit) aSwitch.setChecked(true);
    }

    View.OnClickListener myhandler = new View.OnClickListener() {
        public void onClick(View v) {
            String m = mass_box.getText().toString();
            String h = height_box.getText().toString();

            if ("".equals(m) || "".equals(h)) {
                result = 0.0;
            } else {
                mass = Double.parseDouble(m);
                height = Double.parseDouble(h);

                Switch s = findViewById(R.id.switch_unit);
                if (s.isChecked()) result = count2(mass, height);
                else result = count(mass, height);
            }

            save();

            Intent intent = new Intent(MainActivity.this, ShowBMI.class);
            intent.putExtra(EXTRA_MESSAGE, result);
            startActivity(intent);
        }
    };

    public double count(double mass, double height) {
        double r;
        if (mass > 1 && mass < 500 && height > 0.3 && height <= 3) {
            r = mass / (height * height);
            r = (int)(r*100)/100.0;
        } else {
            r = 0.0;
        }
        return r;
    }

    public double count2(double mass, double height) {
        double r;
        if (mass > 2 && mass < 1000 && height > 4 && height <= 120) {
            r = mass / (height * height);
            r = (int)(r*100*703)/100.0;
        } else {
            r = 0.0;
        }
        return r;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about_me:
                Intent intent = new Intent(MainActivity.this, AboutMe.class);
                startActivity(intent);
                save();
                return true;
            case R.id.save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save() {
        String m = mass_box.getText().toString();
        String h = height_box.getText().toString();
        boolean b = aSwitch.isChecked();

        mass = Double.parseDouble(m);
        height = Double.parseDouble(h);

        spe.putFloat("mass", (float)mass);
        spe.putFloat("height", (float)height);
        spe.putBoolean("switch", b);

        spe.apply();
    }
}
