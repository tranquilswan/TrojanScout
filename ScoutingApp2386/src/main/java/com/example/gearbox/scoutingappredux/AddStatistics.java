package com.example.gearbox.scoutingappredux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.NumberPicker;
import android.widget.TextView;

public class AddStatistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker npTotalShots = (NumberPicker) findViewById(R.id.npTotalShots);
        NumberPicker npLowGoals = (NumberPicker) findViewById(R.id.npLowGoals);
        NumberPicker npHighGoal = (NumberPicker) findViewById(R.id.npHighGoals);
        NumberPicker npChivalDeFrise = (NumberPicker) findViewById(R.id.npChivalDeFrise);
        NumberPicker npDrawBridge = (NumberPicker) findViewById(R.id.npDrawBridge);
        NumberPicker npLowBar = (NumberPicker) findViewById(R.id.npLowBar);
        NumberPicker npMoat = (NumberPicker) findViewById(R.id.npMoat);
        NumberPicker npPortCullis = (NumberPicker) findViewById(R.id.npPortCullis);
        NumberPicker npRamparts = (NumberPicker) findViewById(R.id.npRamparts);
        NumberPicker npRockWall = (NumberPicker) findViewById(R.id.npRockWall);
        NumberPicker npSallyPort = (NumberPicker) findViewById(R.id.npSallyPort);
        NumberPicker npRoughTerrain = (NumberPicker) findViewById(R.id.npRoughTerrain);

        TextView tvStatsTeamNum = (TextView) findViewById(R.id.tvStatsTeamNum);
        TextView tvStatsTeamName = (TextView) findViewById(R.id.tvStatsTeamName);

        npTotalShots.setMinValue(0);
        npTotalShots.setMaxValue(10);
        npLowGoals.setMinValue(0);
        npLowGoals.setMaxValue(10);
        npHighGoal.setMinValue(0);
        npHighGoal.setMaxValue(10);
        npChivalDeFrise.setMinValue(0);
        npChivalDeFrise.setMaxValue(10);
        npDrawBridge.setMinValue(0);
        npDrawBridge.setMaxValue(10);
        npLowBar.setMinValue(0);
        npLowBar.setMaxValue(10);
        npMoat.setMinValue(0);
        npMoat.setMaxValue(10);
        npPortCullis.setMinValue(0);
        npPortCullis.setMaxValue(10);
        npRamparts.setMinValue(0);
        npRamparts.setMaxValue(10);
        npRockWall.setMinValue(0);
        npRockWall.setMaxValue(10);
        npSallyPort.setMinValue(0);
        npSallyPort.setMaxValue(10);
        npRoughTerrain.setMinValue(0);
        npRoughTerrain.setMaxValue(10);

        String teamName = getIntent().getExtras().getString("teamName");
        int teamNum = getIntent().getExtras().getInt("teamNum");

        tvStatsTeamNum.setText(Integer.toString(teamNum));
        if (teamName != null)
            tvStatsTeamName.setText(teamName);

    }

}
