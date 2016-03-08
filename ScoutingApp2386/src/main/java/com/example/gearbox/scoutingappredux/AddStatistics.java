package com.example.gearbox.scoutingappredux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.NumberPicker;

public class AddStatistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker npPicker = (NumberPicker) findViewById(R.id.npPicker);
        npPicker.setMinValue(0);
        npPicker.setMaxValue(10);
    }

}
