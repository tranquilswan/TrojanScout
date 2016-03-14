package com.example.gearbox.scoutingappredux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gearbox.scoutingappredux.db.TeamStatsDataSource;

import java.util.ArrayList;
import java.util.List;

public class ViewStatisticsActivity extends AppCompatActivity {

    String teamName;
    int teamNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //SetTitle
        teamName = getIntent().getExtras().getString("teamName");
        teamNum = getIntent().getExtras().getInt("teamNum");

        TextView tvStatsViewTeamNum = (TextView) findViewById(R.id.tvStatsViewTeamNum);
        TextView tvStatsViewTeamName = (TextView) findViewById(R.id.tvStatsViewTeamName);

        tvStatsViewTeamName.setText(teamName);
        tvStatsViewTeamNum.setText(Integer.toString(teamNum));

        TeamStatsDataSource tsds = new TeamStatsDataSource(this);
        int matches = tsds.getCount(teamNum);
        int i;
        List<String> list = new ArrayList<>();
        for (i = 1; i <= matches; i++) {
            list.add(Integer.toString(i));
        }

//        if (matches>1) {
//            list.add("Cummulative");
//        }

        Spinner spinner = (Spinner) findViewById(R.id.spnrMatchSelector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemText = (String) parent.getItemAtPosition(position);
                PopulateUI(itemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void PopulateUI(String itemText) {
        TeamStatsDataSource tsds = new TeamStatsDataSource(this);
        Statistics teamStats = tsds.getTeam(teamNum, Integer.parseInt(itemText));

        TextView tvAccuracy = (TextView) findViewById(R.id.tvAccuracy);
        TextView tvAutoAndEnd = (TextView) findViewById(R.id.tvAutoAndEnd);
        TextView tvShotsDisplay = (TextView) findViewById(R.id.tvShotsDisplay);
        TextView tvLowHighDisplay = (TextView) findViewById(R.id.tvLowHighDisplay);
        TextView tvChivalMoatDisplay = (TextView) findViewById(R.id.tvChivalMoatDisplay);
        TextView tvRampsartsLowBarDisplay = (TextView) findViewById(R.id.tvRampsartsLowBarDisplay);
        TextView tvSallyPortAndPortCullisDisplay = (TextView) findViewById(R.id.tvSallyPortAndPortCullisDisplay);
        TextView tvRockRough = (TextView) findViewById(R.id.tvRockRough);
        TextView tvDraw = (TextView) findViewById(R.id.tvDraw);
        TextView tvComments = (TextView) findViewById(R.id.tvStatsViewComments);

        float Accuracy;
        float Misses;
        String Autonomous = null;
        String EndGame = null;
        String Comments;
        float Shots;
        float LowGoals;
        float HighGoals;
        int ChivalDeFrise;
        int Moat;
        int Ramparts;
        int LowBar;
        int SallyPort;
        int PortCullis;
        int RockWall;
        int RoughTerrain;
        int DrawBridge;
        String AccuracyAsString;

        Shots = teamStats.getTotalShots();
        LowGoals = teamStats.getLowGoals();
        HighGoals = teamStats.getHighGoals();
        ChivalDeFrise = teamStats.getChivalDeFriseCrosses();
        Moat = teamStats.getMoatCrosses();
        Ramparts = teamStats.getRampartsCrosses();
        LowBar = teamStats.getLowBarCrosses();
        SallyPort = teamStats.getSallyPortCrosses();
        PortCullis = teamStats.getPortCullisCrosses();
        RockWall = teamStats.getRockWallCrosses();
        RoughTerrain = teamStats.getRoughTerrainCrosses();
        DrawBridge = teamStats.getDrawBridgeCrosses();
        Comments = teamStats.getmComments();

        if (Shots != 0) {
            Accuracy = ((LowGoals + HighGoals) / Shots) * 100;
            int i = (int) Accuracy;
            AccuracyAsString = Integer.toString(i) + "%";
        } else {
            AccuracyAsString = "NONE";
        }

        Misses = Shots - (LowGoals + HighGoals);
        int MissesInteger = (int) Misses;

        int i = teamStats.getAutonomousUsage();
        if (i == 1) {
            Autonomous = "YES";
        } else if (i == 0) {
            Autonomous = "NO";
        }


        i = teamStats.getEndGameType();
        if (i == 0) {
            EndGame = "NONE";
        } else if (i == 1) {
            EndGame = "CHALLENGE";
        } else if (i == 2) {
            EndGame = "SCALE";
        }

        int LowGoalsInteger = (int) LowGoals;
        int HighGoalsInteger = (int) HighGoals;
        int ShotsInteger = (int) Shots;

        tvAccuracy.setText("Accuracy: " + AccuracyAsString + "  Misses: " + Integer.toString(MissesInteger));
        tvAutoAndEnd.setText("Autonomous: " + Autonomous + "  EndGame: " + EndGame);
        tvShotsDisplay.setText("Shots: " + Integer.toString(ShotsInteger));
        tvLowHighDisplay.setText("Low Goals: " + LowGoalsInteger + "  High Goals: " + HighGoalsInteger);
        tvChivalMoatDisplay.setText("Chival De Frise: " + ChivalDeFrise + "  Moat: " + Moat);
        tvRampsartsLowBarDisplay.setText("Ramparts: " + Ramparts + "  LowBar: " + LowBar);
        tvSallyPortAndPortCullisDisplay.setText("Sally Port: " + SallyPort + "  Port Cullis: " + PortCullis);
        tvRockRough.setText("Rock Wall: " + RockWall + "  Rough Terrain: " + RoughTerrain);
        tvDraw.setText("DrawBridge: " + DrawBridge);
        tvComments.setText("Comments: " + Comments);

    }

}
