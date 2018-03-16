package com.example.necky0.bmiapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Result";

    Button button_count;
    EditText mass_box;
    EditText height_box;
    TextView result_box;
    Switch aSwitch;

    TextView mass_unit;
    TextView height_unite;

    String mass;
    String height;
    double result;

    boolean isEnglishUnit;

    SharedPreferences sp;
    Save save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_count = (Button) findViewById(R.id.button_count);
        button_count.setOnClickListener(countButtonHandler);

        mass_box = findViewById(R.id.mass);
        height_box = findViewById(R.id.height);
        result_box = findViewById(R.id.result);
        
        mass_unit = findViewById(R.id.mass_unit);
        height_unite = findViewById(R.id.height_unit);

        aSwitch = findViewById(R.id.switch_unit);
        aSwitch.setOnClickListener(unitsSwitchHandler);

        sp = getSharedPreferences("Dane BMI", MODE_PRIVATE);
        save = new Save(sp);

        setVariables();
        init();
    }

    private void setVariables() {
        mass = save.getMass();
        height = save.getHeight();
        isEnglishUnit = save.getUnits();
    }

    private void getVariables() {
        mass = mass_box.getText().toString();
        height = height_box.getText().toString();
        isEnglishUnit = aSwitch.isChecked();
    }

    private void init() {
        if (toDouble(mass) != 0) mass_box.setText(save.getMass());
        if (toDouble(height) != 0) height_box.setText(save.getHeight());
        aSwitch.setChecked(save.getUnits());
    }

    private double toDouble(String str){
        return Double.parseDouble(str);
    }

    View.OnClickListener countButtonHandler = new View.OnClickListener() {
        public void onClick(View v) {
            getVariables();

            if ("".equals(mass) || "".equals(height)) {
                result = 0;
            } else {
                if (isEnglishUnit) result = BMICount.englishUnits(toDouble(mass), toDouble(height));
                else result = BMICount.polishUnits(toDouble(mass), toDouble(height));
            }

            save.save(mass, height, isEnglishUnit);

            Intent intent = new Intent(MainActivity.this, ShowBMI.class);
            intent.putExtra(EXTRA_MESSAGE, result);
            startActivity(intent);
        }
    };

    View.OnClickListener unitsSwitchHandler = new View.OnClickListener() {
        public void onClick(View v) {
            mass_box.setText("");
            height_box.setText("");

            isEnglishUnit = aSwitch.isChecked();

            if (isEnglishUnit) {
                mass_unit.setText(R.string.mass_lb);
                height_unite.setText(R.string.height_in);
            } else {
                mass_unit.setText(R.string.mass);
                height_unite.setText(R.string.height);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        getVariables();

        switch (item.getItemId()) {
            case R.id.about_me:
                Intent intent = new Intent(MainActivity.this, AboutMe.class);
                startActivity(intent);
                save.save(mass, height, isEnglishUnit);
                return true;
            case R.id.save:
                save.save(mass, height, isEnglishUnit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
