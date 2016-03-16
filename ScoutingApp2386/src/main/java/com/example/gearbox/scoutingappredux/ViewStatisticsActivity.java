package com.example.gearbox.scoutingappredux;

import android.content.Intent;
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
        final int matches = tsds.getCount(teamNum);
        int i;
        List<String> list = new ArrayList<>();
        List<Statistics> sList = new ArrayList<>();
        sList = tsds.getTeam(teamNum);

        for (i = 0; i < sList.size(); i++) {
            list.add(Integer.toString(sList.get(i).getMatchID()));
        }

        if (matches > 1) {
            list.add("Average");
        }

        Spinner spinner = (Spinner) findViewById(R.id.spnrMatchSelector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemText = (String) parent.getItemAtPosition(position);
                PopulateUI(itemText, teamNum, matches);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void PopulateUI(String itemText, int teamNum, int matches) {
        TeamStatsDataSource tsds = new TeamStatsDataSource(this);

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

        float Accuracy = 0f;
        float Misses = 0f;
        String Autonomous = null;
        String EndGame = null;
        String Comments = "";
        float Shots = 0;
        float LowGoals = 0;
        float HighGoals = 0;
        float ChivalDeFrise = 0;
        float Moat = 0;
        float Ramparts = 0;
        float LowBar = 0;
        float SallyPort = 0;
        float PortCullis = 0;
        float RockWall = 0;
        float RoughTerrain = 0;
        float DrawBridge = 0;
        String AccuracyAsString = "";

        int AccuracyInteger;
        int MissesInteger;
        int ShotsInteger;
        int LowGoalsInteger;
        int HighGoalsInteger;
        int ChivalDeFriseInteger;
        int MoatInteger;
        int RampartsInteger;
        int LowBarInteger;
        int SallyPortInteger;
        int PortCullisInteger;
        int RockWallInteger;
        int RoughTerrainInteger;
        int DrawBridgeInteger;


        if (itemText.equals("Average")) {

            List<Statistics> statList = new ArrayList<>();
            statList = tsds.getTeam(teamNum);

            int counter;
            for (counter = 0; counter < statList.size(); counter++) {
                Statistics teamStats = statList.get(counter);

                Shots = Shots + teamStats.getTotalShots();
                LowGoals = LowGoals + teamStats.getLowGoals();
                HighGoals = HighGoals + teamStats.getHighGoals();
                ChivalDeFrise = ChivalDeFrise + teamStats.getChivalDeFriseCrosses();
                Moat = Moat + teamStats.getMoatCrosses();
                Ramparts = Ramparts + teamStats.getRampartsCrosses();
                LowBar = LowBar + teamStats.getLowBarCrosses();
                SallyPort = SallyPort + teamStats.getSallyPortCrosses();
                PortCullis = PortCullis + teamStats.getPortCullisCrosses();
                RockWall = RockWall + teamStats.getRockWallCrosses();
                RoughTerrain = RoughTerrain + teamStats.getRoughTerrainCrosses();
                DrawBridge = DrawBridge + teamStats.getDrawBridgeCrosses();
                Comments = Comments + teamStats.getmComments() + "\n";

                if (teamStats.getTotalShots() != 0) {
                    Accuracy = Accuracy + ((((float) teamStats.getLowGoals() + (float) teamStats.getHighGoals()) / (float) teamStats.getTotalShots()) * 100);
                }

                Misses = Misses + (teamStats.getTotalShots() - (teamStats.getLowGoals() + teamStats.getHighGoals()));
            }


            Shots = Shots / matches;
            LowGoals = LowGoals / matches;
            HighGoals = HighGoals / matches;
            ChivalDeFrise = ChivalDeFrise / matches;
            Moat = Moat / matches;
            Ramparts = Ramparts / matches;
            LowBar = LowBar / matches;
            SallyPort = SallyPort / matches;
            PortCullis = PortCullis / matches;
            RockWall = RockWall / matches;
            RoughTerrain = RoughTerrain / matches;
            DrawBridge = DrawBridge / matches;


            if (Accuracy == 0f) {
                AccuracyAsString = "NONE";
            } else {
                Accuracy = Accuracy / matches;
                AccuracyInteger = Math.round(Accuracy);
                AccuracyAsString = Integer.toString(AccuracyInteger) + "%";
            }

            Misses = Misses / matches;

            MissesInteger = Math.round(Misses);
            LowGoalsInteger = Math.round(LowGoals);
            HighGoalsInteger = Math.round(HighGoals);
            ShotsInteger = Math.round(Shots);
            ChivalDeFriseInteger = Math.round(ChivalDeFrise);
            MoatInteger = Math.round(Moat);
            RampartsInteger = Math.round(Ramparts);
            LowBarInteger = Math.round(LowBar);
            SallyPortInteger = Math.round(SallyPort);
            PortCullisInteger = Math.round(PortCullis);
            RockWallInteger = Math.round(RockWall);
            RoughTerrainInteger = Math.round(RoughTerrain);
            DrawBridgeInteger = Math.round(DrawBridge);

            tvAccuracy.setText("Accuracy: " + AccuracyAsString + "  Misses: " + Integer.toString(MissesInteger));
            tvAutoAndEnd.setText("Autonomous: NA" + "  EndGame: NA");
            tvShotsDisplay.setText("Shots: " + Integer.toString(ShotsInteger));
            tvLowHighDisplay.setText("Low Goals: " + LowGoalsInteger + "  High Goals: " + HighGoalsInteger);
            tvChivalMoatDisplay.setText("Chival De Frise: " + ChivalDeFriseInteger + "  Moat: " + MoatInteger);
            tvRampsartsLowBarDisplay.setText("Ramparts: " + RampartsInteger + "  LowBar: " + LowBarInteger);
            tvSallyPortAndPortCullisDisplay.setText("Sally Port: " + SallyPortInteger + "  Port Cullis: " + PortCullisInteger);
            tvRockRough.setText("Rock Wall: " + RockWallInteger + "  Rough Terrain: " + RoughTerrainInteger);
            tvDraw.setText("DrawBridge: " + DrawBridgeInteger);
            tvComments.setText("Comments: " + Comments);

        } else {
            Statistics teamStats = tsds.getTeam(this.teamNum, Integer.parseInt(itemText));


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

            MissesInteger = Math.round(Misses);
            LowGoalsInteger = Math.round(LowGoals);
            HighGoalsInteger = Math.round(HighGoals);
            ShotsInteger = Math.round(Shots);
            ChivalDeFriseInteger = Math.round(ChivalDeFrise);
            MoatInteger = Math.round(Moat);
            RampartsInteger = Math.round(Ramparts);
            LowBarInteger = Math.round(LowBar);
            SallyPortInteger = Math.round(SallyPort);
            PortCullisInteger = Math.round(PortCullis);
            RockWallInteger = Math.round(RockWall);
            RoughTerrainInteger = Math.round(RoughTerrain);
            DrawBridgeInteger = Math.round(DrawBridge);

            tvAccuracy.setText("Accuracy: " + AccuracyAsString + "  Misses: " + Integer.toString(MissesInteger));
            tvAutoAndEnd.setText("Autonomous: " + Autonomous + "  EndGame: " + EndGame);
            tvShotsDisplay.setText("Shots: " + Integer.toString(ShotsInteger));
            tvLowHighDisplay.setText("Low Goals: " + LowGoalsInteger + "  High Goals: " + HighGoalsInteger);
            tvChivalMoatDisplay.setText("Chival De Frise: " + ChivalDeFriseInteger + "  Moat: " + MoatInteger);
            tvRampsartsLowBarDisplay.setText("Ramparts: " + RampartsInteger + "  LowBar: " + LowBarInteger);
            tvSallyPortAndPortCullisDisplay.setText("Sally Port: " + SallyPortInteger + "  Port Cullis: " + PortCullisInteger);
            tvRockRough.setText("Rock Wall: " + RockWallInteger + "  Rough Terrain: " + RoughTerrainInteger);
            tvDraw.setText("DrawBridge: " + DrawBridgeInteger);
            tvComments.setText("Comments: " + Comments);

        }
    }

    public void LaunchPitInfo(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("action", "View");
        bundle.putInt("teamNum", teamNum);

        Intent i = new Intent(this, MainActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

}
