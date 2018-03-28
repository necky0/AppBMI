package com.example.necky0.bmiapp;

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

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String MASS = "MASS";
    public static final String HEIGHT = "HEIGHT";
    public static final String UNITS = "UNITS";
    public static final String FORMAT = "%.2f";

    private Button button_count;
    private Switch switch_unit;
    private EditText mass_box;
    private EditText height_box;
    private TextView mass_unit;
    private TextView height_unit;

    private double mass;
    private double height;
    private boolean isEnglishUnit;

    private SharedPreferences sp;
    private SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFindViews();
        initOnClickListeners();
        initSharedPreferences();

        initUI();
    }

    private void initSharedPreferences() {
        sp = getSharedPreferences("DATA", MODE_PRIVATE);
        spe = sp.edit();

        loadData();

        spe.apply();
    }

    private void loadData(){
        mass = (double) sp.getFloat(MASS, 0);
        height = (double) sp.getFloat(HEIGHT, 0);
        isEnglishUnit = sp.getBoolean(UNITS, false);
    }

    private void saveData(){
        spe.putFloat(MASS, (float) mass);
        spe.putFloat(HEIGHT, (float) height);
        spe.putBoolean(UNITS, isEnglishUnit);

        spe.apply();
    }
    
    private void initFindViews(){
        button_count = findViewById(R.id.button_count);

        mass_box = findViewById(R.id.mass_box);
        height_box = findViewById(R.id.height_box);

        mass_unit = findViewById(R.id.mass_unit);
        height_unit = findViewById(R.id.height_unit);

        switch_unit = findViewById(R.id.switch_unit);
    }    
    
    private void initOnClickListeners(){
        button_count.setOnClickListener(countButtonHandler);
        switch_unit.setOnClickListener(unitsSwitchHandler);
    }

    private void initUI() {
        if (mass != 0) mass_box.setText(String.format(Locale.US, FORMAT, mass));
        if (height != 0) height_box.setText(String.format(Locale.US, FORMAT, height));

        getInputUnit();
        setUnitUI();
    }

    public void setUnitUI() {
        if (isEnglishUnit) {
            mass_unit.setText(R.string.mass_lb);
            height_unit.setText(R.string.height_in);
        } else {
            mass_unit.setText(R.string.mass);
            height_unit.setText(R.string.height);
        }
    }

    private void getInputs(){
        String massTemp = mass_box.getText().toString();
        String heightTemp = height_box.getText().toString();

        if ("".equals(massTemp)) mass = 0;
        else mass = Double.parseDouble(massTemp);

        if ("".equals(heightTemp)) height = 0;
        else height = Double.parseDouble(heightTemp);

        getInputUnit();
    }

    private void getInputUnit(){
        isEnglishUnit = switch_unit.isChecked();
    }

    View.OnClickListener countButtonHandler = new View.OnClickListener() {
        public void onClick(View v) {
            getInputs();

            double result = BMICount.count(mass, height, isEnglishUnit);
            
            saveData();

            ShowBMI.start(MainActivity.this, result);
        }
    };

    View.OnClickListener unitsSwitchHandler = new View.OnClickListener() {
        public void onClick(View v) {
            clearInputs();
            getInputUnit();
            setUnitUI();
        }
    };
    
    public void clearInputs() {
        mass_box.setText("");
        height_box.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_me:
                AboutMe.start(MainActivity.this);
                return true;
            case R.id.save:
                getInputs();
                saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
