package com.example.billsplitter;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public EditText billTotal;
    public Double total;
    public Integer people;
    public EditText numPeople;
    public Switch tipSwitch;
    public Spinner spinner;
    public EditText tipValue;
    public Double tipNumber;
    public Double percent;
    public Double costPer;
    public TextView cost;

    public TextWatcher billWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
           try {
               total = Double.parseDouble(editable.toString());
               calculateTip();
               calculateCost();
           } catch(Exception e) {
               Log.e("logtag", "Exception " + e.toString());
           }
        }
    };

    public TextWatcher peopleWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                people = Integer.parseInt(editable.toString());
                calculateCost();
            } catch(Exception e) {
                Log.e("logtag", "Exception " + e.toString());
            }
        }
    };

    public TextWatcher tipWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            getTip();
            calculateCost();
        }
    };

    public View.OnClickListener tipListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (tipSwitch.isChecked()) {
                tipSwitch.setText(tipSwitch.getTextOn());
            }
            else {
                tipSwitch.setText(tipSwitch.getTextOff());
            }
            calculateCost();
        }
    };

    public AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 4) {
                tipValue.setInputType(2002);
                tipValue.setText("");
            }
            else {
                tipValue.setInputType(0);
                if (i == 0) {
                    percent = .15;
                }
                else if (i ==1) {
                    percent = .20;
                }
                else if (i == 2) {
                    percent = .25;
                }
                else if (i == 3) {
                    percent = .30;
                }
                calculateTip();
                calculateCost();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            tipValue.setInputType(0);
        }
    };

    public void calculateTip() {
        try {
            tipNumber = total * percent;
            tipNumber = Math.round(tipNumber * 100.0) / 100.0;
            tipValue.setText(tipNumber.toString());
        }
        catch(Exception e) {
            Log.e("logtag", "Exception " + e.toString());
        }
    }

    public Double getTip() {
        try {
            return Double.parseDouble(tipValue.getText().toString());
        }
        catch(Exception e) {
            Log.e("logtag", "Exception " + e.toString());
        }
        return 0.0;
    }

    public void calculateCost() {
        try {
            if (tipSwitch.isChecked()) {
                Double tip = getTip();
                costPer = (total + tip)/ people ;
            }
            else {
                costPer = total / people;
            }
            costPer = Math.round(costPer * 100.0) / 100.0;
            cost.setText(costPer.toString());
        }
        catch(Exception e) {
            Log.e("logtag", "Exception " + e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billTotal = (EditText) findViewById(R.id.editTextNumberDecimal);
        billTotal.addTextChangedListener(billWatcher);

        numPeople = (EditText) findViewById(R.id.editTextNumber);
        numPeople.addTextChangedListener(peopleWatcher);

        tipSwitch = (Switch) findViewById(R.id.switch1);
        tipSwitch.setOnClickListener(tipListener);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(spinnerListener);

        tipValue = (EditText) findViewById(R.id.tip_value);
        tipValue.addTextChangedListener(tipWatcher);

        cost = (TextView) findViewById(R.id.cost_per);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}