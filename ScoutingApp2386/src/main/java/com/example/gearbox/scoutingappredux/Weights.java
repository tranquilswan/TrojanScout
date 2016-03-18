package com.example.gearbox.scoutingappredux;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearbox.scoutingappredux.db.TeamDataSource;
import com.example.gearbox.scoutingappredux.db.TeamStatsDataSource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Weights extends AppCompatActivity {

    int balance;

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    Log.w("setNumberPickerTextColr", e);
                } catch (IllegalAccessException e) {
                    Log.w("setNumberPickerTextColr", e);
                } catch (IllegalArgumentException e) {
                    Log.w("setNumberPickerTextColr", e);
                }
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        InitializeBalance();

        InitiateUILogic();
    }

    public void InitializeBalance() {
        balance = 100;
        SharedPreferences prefs = getSharedPreferences("Weights", MODE_PRIVATE);
        int ChallengeWeight = prefs.getInt("ChallengeWeight", 0);
        int AutonomousWeight = prefs.getInt("AutonomousWeight", 0);
        int ScaleWeight = prefs.getInt("ScaleWeight", 0);
        int LowGoalWeight = prefs.getInt("LowGoalWeight", 0);
        int HighGoalWeight = prefs.getInt("HighGoalWeight", 0);
        int ChivalDeFriseWeight = prefs.getInt("ChivalDeFriseWeight", 0);
        int MoatWeight = prefs.getInt("MoatWeight", 0);
        int RampartsWeight = prefs.getInt("RampartsWeight", 0);
        int LowBarWeight = prefs.getInt("LowBarWeight", 0);
        int SallyPortWeight = prefs.getInt("SallyPortWeight", 0);
        int PortCullisWeight = prefs.getInt("PortCullisWeight", 0);
        int RockWallWeight = prefs.getInt("RockWallWeight", 0);
        int RoughTerrainWeight = prefs.getInt("RoughTerrainWeight", 0);
        int DrawBridgeWeight = prefs.getInt("DrawBridgeWeight", 0);

        final TextView tvLowGoalsWeight = (TextView) findViewById(R.id.tvLowGoalsWeight);
        final TextView tvHighGoalWeight = (TextView) findViewById(R.id.tvHighGoalWeight);
        final TextView tvRampartWeight = (TextView) findViewById(R.id.tvRampartWeight);
        final TextView tvDrawBridgeWeight = (TextView) findViewById(R.id.tvDrawBridgeWeight);
        final TextView tvChivalDeFriseWeight = (TextView) findViewById(R.id.tvChivalDeFriseWeight);
        final TextView tvLowBarWeight = (TextView) findViewById(R.id.tvLowBarWeight);
        final TextView tvRockWallWeight = (TextView) findViewById(R.id.tvRockWallWeight);
        final TextView tvSallyportWeight = (TextView) findViewById(R.id.tvSallyportWeight);
        final TextView tvMoatWeight = (TextView) findViewById(R.id.tvMoatWeight);
        final TextView tvRoughTerrainWeight = (TextView) findViewById(R.id.tvRoughTerrainWeight);
        final TextView tvPortCullisWeight = (TextView) findViewById(R.id.tvPortCullisWeight);
        final TextView tvAutonomousWeight = (TextView) findViewById(R.id.tvAutonomousWeight);
        final TextView tvChallengeWeight = (TextView) findViewById(R.id.tvChallengeWeight);
        final TextView tvScaleWeight = (TextView) findViewById(R.id.tvScaleWeight);

        tvLowGoalsWeight.setText(Integer.toString(AutonomousWeight));
        tvHighGoalWeight.setText(Integer.toString(HighGoalWeight));
        tvRampartWeight.setText(Integer.toString(RampartsWeight));
        tvDrawBridgeWeight.setText(Integer.toString(DrawBridgeWeight));
        tvChivalDeFriseWeight.setText(Integer.toString(ChivalDeFriseWeight));
        tvLowBarWeight.setText(Integer.toString(LowBarWeight));
        tvRockWallWeight.setText(Integer.toString(RockWallWeight));
        tvSallyportWeight.setText(Integer.toString(SallyPortWeight));
        tvMoatWeight.setText(Integer.toString(MoatWeight));
        tvRoughTerrainWeight.setText(Integer.toString(RoughTerrainWeight));
        tvPortCullisWeight.setText(Integer.toString(PortCullisWeight));
        tvAutonomousWeight.setText(Integer.toString(AutonomousWeight));
        tvChallengeWeight.setText(Integer.toString(ChallengeWeight));
        tvScaleWeight.setText(Integer.toString(ScaleWeight));

        balance = 100 - (ChallengeWeight + AutonomousWeight + ScaleWeight + LowGoalWeight + HighGoalWeight +
                ChivalDeFriseWeight + MoatWeight + RampartsWeight + LowBarWeight + SallyPortWeight + PortCullisWeight +
                RockWallWeight + RoughTerrainWeight + DrawBridgeWeight);

        TextView tvBalanceWeights = (TextView) findViewById(R.id.tvBalanceWeights);
        tvBalanceWeights.setText("Balance : " + Integer.toString(balance));
    }

    public void CalculateScores(View view) {

        if (balance == 0) {

            int AutonomousWeight;
            int ChallengeWeight;
            int ScaleWeight;
            int LowGoalWeight;
            int HighGoalWeight;
            int ChivalDeFriseWeight;
            int MoatWeight;
            int RampartsWeight;
            int LowBarWeight;
            int SallyPortWeight;
            int PortCullisWeight;
            int RockWallWeight;
            int RoughTerrainWeight;
            int DrawBridgeWeight;

            int AutonomousValue;
            int ChallengeValue;
            int ScaleValue;
            int LowGoalValue;
            int HighGoalValue;
            int ChivalDeFriseValue;
            int MoatValue;
            int RampartsValue;
            int LowBarValue;
            int SallyPortValue;
            int PortCullisValue;
            int RockWallValue;
            int RoughTerrainValue;
            int DrawBridgeValue;

            final TextView tvLowGoalsWeight = (TextView) findViewById(R.id.tvLowGoalsWeight);
            final TextView tvHighGoalWeight = (TextView) findViewById(R.id.tvHighGoalWeight);
            final TextView tvRampartWeight = (TextView) findViewById(R.id.tvRampartWeight);
            final TextView tvDrawBridgeWeight = (TextView) findViewById(R.id.tvDrawBridgeWeight);
            final TextView tvChivalDeFriseWeight = (TextView) findViewById(R.id.tvChivalDeFriseWeight);
            final TextView tvLowBarWeight = (TextView) findViewById(R.id.tvLowBarWeight);
            final TextView tvRockWallWeight = (TextView) findViewById(R.id.tvRockWallWeight);
            final TextView tvSallyportWeight = (TextView) findViewById(R.id.tvSallyportWeight);
            final TextView tvMoatWeight = (TextView) findViewById(R.id.tvMoatWeight);
            final TextView tvRoughTerrainWeight = (TextView) findViewById(R.id.tvRoughTerrainWeight);
            final TextView tvPortCullisWeight = (TextView) findViewById(R.id.tvPortCullisWeight);
            final TextView tvAutonomousWeight = (TextView) findViewById(R.id.tvAutonomousWeight);
            final TextView tvChallengeWeight = (TextView) findViewById(R.id.tvChallengeWeight);
            final TextView tvScaleWeight = (TextView) findViewById(R.id.tvScaleWeight);

            //Getting All The Weights
            ChallengeWeight = Integer.parseInt(tvChallengeWeight.getText().toString());
            ScaleWeight = Integer.parseInt(tvScaleWeight.getText().toString());
            LowGoalWeight = Integer.parseInt(tvLowGoalsWeight.getText().toString());
            HighGoalWeight = Integer.parseInt(tvHighGoalWeight.getText().toString());
            ChivalDeFriseWeight = Integer.parseInt(tvChivalDeFriseWeight.getText().toString());
            MoatWeight = Integer.parseInt(tvMoatWeight.getText().toString());
            RampartsWeight = Integer.parseInt(tvRampartWeight.getText().toString());
            LowBarWeight = Integer.parseInt(tvLowBarWeight.getText().toString());
            SallyPortWeight = Integer.parseInt(tvSallyportWeight.getText().toString());
            PortCullisWeight = Integer.parseInt(tvPortCullisWeight.getText().toString());
            RockWallWeight = Integer.parseInt(tvRockWallWeight.getText().toString());
            RoughTerrainWeight = Integer.parseInt(tvRoughTerrainWeight.getText().toString());
            DrawBridgeWeight = Integer.parseInt(tvDrawBridgeWeight.getText().toString());
            AutonomousWeight = Integer.parseInt(tvAutonomousWeight.getText().toString());

            SharedPreferences.Editor editor = getSharedPreferences("Weights", MODE_PRIVATE).edit();
            editor.putInt("ChallengeWeight", ChallengeWeight);
            editor.putInt("ScaleWeight", ScaleWeight);
            editor.putInt("LowGoalWeight", LowGoalWeight);
            editor.putInt("HighGoalWeight", HighGoalWeight);
            editor.putInt("ChivalDeFriseWeight", ChivalDeFriseWeight);
            editor.putInt("MoatWeight", MoatWeight);
            editor.putInt("RampartsWeight", RampartsWeight);
            editor.putInt("LowBarWeight", LowBarWeight);
            editor.putInt("SallyPortWeight", SallyPortWeight);
            editor.putInt("PortCullisWeight", PortCullisWeight);
            editor.putInt("RockWallWeight", RockWallWeight);
            editor.putInt("RoughTerrainWeight", RoughTerrainWeight);
            editor.putInt("DrawBridgeWeight", DrawBridgeWeight);
            editor.putInt("AutonomousWeight", AutonomousWeight);
            editor.apply();

            //Getting All TeamNumbrs
            TeamDataSource tds = new TeamDataSource(this);
            List<Team> teams = tds.getTeams();
            List<Integer> teamnums = new ArrayList<>();
            int i;

            for (i = 0; i < teams.size(); i++) {
                teamnums.add(teams.get(i).getmTeamNum());
            }

            TeamStatsDataSource tsds = new TeamStatsDataSource(this);
            int x;

            for (x = 0; x < teamnums.size(); x++) {

                int teamnum = teamnums.get(x);
                int q;

                for (q = 1; q <= tsds.getCount(teamnum); q++) {

                    Statistics statistics = tsds.getTeam(teamnum, q);
                    int matchID = statistics.getMatchID();
                    int SCORE;

                    LowGoalValue = statistics.getLowGoals();
                    HighGoalValue = statistics.getHighGoals();
                    ChivalDeFriseValue = statistics.getChivalDeFriseCrosses();
                    MoatValue = statistics.getMoatCrosses();
                    RampartsValue = statistics.getRampartsCrosses();
                    LowBarValue = statistics.getLowBarCrosses();
                    SallyPortValue = statistics.getSallyPortCrosses();
                    PortCullisValue = statistics.getPortCullisCrosses();
                    RockWallValue = statistics.getRockWallCrosses();
                    RoughTerrainValue = statistics.getRoughTerrainCrosses();
                    DrawBridgeValue = statistics.getDrawBridgeCrosses();
                    AutonomousValue = statistics.getAutonomousUsage();

                    if (statistics.getEndGameType() == 1) {
                        ChallengeValue = statistics.getEndGameType();
                        ScaleValue = 0;
                    } else if (statistics.getEndGameType() == 2) {
                        ScaleValue = statistics.getEndGameType();
                        ChallengeValue = 0;
                    } else {
                        ScaleValue = 0;
                        ChallengeValue = 0;
                    }

                    SCORE = ((LowGoalValue * LowGoalWeight) + (HighGoalValue * HighGoalWeight)
                            + (AutonomousValue * AutonomousWeight) + (ChallengeValue * ChallengeWeight)
                            + (ScaleValue * ScaleWeight)
                            + (ChivalDeFriseValue * ChivalDeFriseWeight) + (MoatValue * MoatWeight)
                            + (RampartsValue * RampartsWeight)
                            + (LowBarValue * LowBarWeight) + (SallyPortValue * SallyPortWeight)
                            + (PortCullisValue * PortCullisWeight)
                            + ((RockWallValue * RockWallWeight) + (RoughTerrainValue * RoughTerrainWeight)
                            + (DrawBridgeValue * DrawBridgeWeight)));

//                    Toast.makeText(getApplicationContext(), "Score for team: " + teamnum + " MatchId: " + matchID +
//                            " is " + Integer.toString(SCORE), Toast.LENGTH_SHORT).show();
                    tsds.updateScore(teamnum, q, SCORE);

                }
            }

            Button bGenerateCSV = (Button) findViewById(R.id.bExportCSV);
            bGenerateCSV.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Scores Calculated and Saved in DB", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Balance is not 0", Toast.LENGTH_SHORT).show();
        }
    }

    public void InitiateUILogic() {
        final TextView tvLowGoalsWeight = (TextView) findViewById(R.id.tvLowGoalsWeight);
        final TextView tvHighGoalWeight = (TextView) findViewById(R.id.tvHighGoalWeight);
        final TextView tvRampartWeight = (TextView) findViewById(R.id.tvRampartWeight);
        final TextView tvDrawBridgeWeight = (TextView) findViewById(R.id.tvDrawBridgeWeight);
        final TextView tvChivalDeFriseWeight = (TextView) findViewById(R.id.tvChivalDeFriseWeight);
        final TextView tvLowBarWeight = (TextView) findViewById(R.id.tvLowBarWeight);
        final TextView tvRockWallWeight = (TextView) findViewById(R.id.tvRockWallWeight);
        final TextView tvSallyportWeight = (TextView) findViewById(R.id.tvSallyportWeight);
        final TextView tvMoatWeight = (TextView) findViewById(R.id.tvMoatWeight);
        final TextView tvRoughTerrainWeight = (TextView) findViewById(R.id.tvRoughTerrainWeight);
        final TextView tvPortCullisWeight = (TextView) findViewById(R.id.tvPortCullisWeight);
        final TextView tvAutonomousWeight = (TextView) findViewById(R.id.tvAutonomousWeight);
        final TextView tvChallengeWeight = (TextView) findViewById(R.id.tvChallengeWeight);
        final TextView tvScaleWeight = (TextView) findViewById(R.id.tvScaleWeight);


        Button bLowGoalsWeight = (Button) findViewById(R.id.bLowGoalsWeight);
        final Button bHighGoalWeight = (Button) findViewById(R.id.bHighGoalWeight);
        Button bLowBarWeight = (Button) findViewById(R.id.bLowBarWeight);
        Button bChivalDeFriseWeight = (Button) findViewById(R.id.bChivalDeFriseWeight);
        Button bDrawBridgeWeight = (Button) findViewById(R.id.bDrawBridgeWeight);
        final Button bMoatWeight = (Button) findViewById(R.id.bMoatWeight);
        Button bRampartsWeight = (Button) findViewById(R.id.bRampartsWeight);
        Button bRockWallWeight = (Button) findViewById(R.id.bRockWallWeight);
        Button bRoughTerrainWeight = (Button) findViewById(R.id.bRoughTerrainWeight);
        Button bPortCullisWeight = (Button) findViewById(R.id.bPortCullisWeight);
        Button bSallyPortWeight = (Button) findViewById(R.id.bSallyPortWeight);
        Button bAutonomousWeight = (Button) findViewById(R.id.bAutonomousWeight);
        Button bChallengeWeight = (Button) findViewById(R.id.bChallengeWeight);
        Button bScaleWeight = (Button) findViewById(R.id.bScaleWeight);

        bAutonomousWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvAutonomousWeight.getText().toString()) + balance, Integer.parseInt(tvAutonomousWeight.getText().toString()), tvAutonomousWeight);
                return true;
            }
        });

        bChallengeWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvChallengeWeight.getText().toString()) + balance, Integer.parseInt(tvChallengeWeight.getText().toString()), tvChallengeWeight);
                return true;
            }
        });

        bScaleWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvScaleWeight.getText().toString()) + balance, Integer.parseInt(tvScaleWeight.getText().toString()), tvScaleWeight);
                return true;
            }
        });


        bLowGoalsWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvLowGoalsWeight.getText().toString()) + balance, Integer.parseInt(tvLowGoalsWeight.getText().toString()), tvLowGoalsWeight);
                return true;
            }
        });
        bHighGoalWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvHighGoalWeight.getText().toString()) + balance, Integer.parseInt(tvHighGoalWeight.getText().toString()), tvHighGoalWeight);
                return true;
            }
        });
        bChivalDeFriseWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvChivalDeFriseWeight.getText().toString()) + balance, Integer.parseInt(tvChivalDeFriseWeight.getText().toString()), tvChivalDeFriseWeight);
                return true;
            }
        });
        bDrawBridgeWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvDrawBridgeWeight.getText().toString()) + balance, Integer.parseInt(tvDrawBridgeWeight.getText().toString()), tvDrawBridgeWeight);
                return true;
            }
        });
        bLowBarWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvLowBarWeight.getText().toString()) + balance, Integer.parseInt(tvLowBarWeight.getText().toString()), tvLowBarWeight);
                return true;
            }
        });
        bMoatWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvMoatWeight.getText().toString()) + balance, Integer.parseInt(tvMoatWeight.getText().toString()), tvMoatWeight);
                return true;
            }
        });
        bPortCullisWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvPortCullisWeight.getText().toString()) + balance, Integer.parseInt(tvPortCullisWeight.getText().toString()), tvPortCullisWeight);
                return true;
            }
        });
        bRampartsWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvRampartWeight.getText().toString()) + balance, Integer.parseInt(tvRampartWeight.getText().toString()), tvRampartWeight);
                return true;
            }
        });
        bRockWallWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvRockWallWeight.getText().toString()) + balance, Integer.parseInt(tvRockWallWeight.getText().toString()), tvRockWallWeight);
                return true;
            }
        });
        bRoughTerrainWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvRoughTerrainWeight.getText().toString()) + balance, Integer.parseInt(tvRoughTerrainWeight.getText().toString()), tvRoughTerrainWeight);
                return true;
            }
        });
        bSallyPortWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvSallyportWeight.getText().toString()) + balance, Integer.parseInt(tvSallyportWeight.getText().toString()), tvSallyportWeight);
                return true;
            }
        });


        bAutonomousWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvAutonomousWeight);
            }
        });

        bChallengeWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvChallengeWeight);
            }
        });

        bScaleWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvScaleWeight);
            }
        });

        bLowGoalsWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvLowGoalsWeight);
            }
        });
        bHighGoalWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvHighGoalWeight);
            }
        });
        bChivalDeFriseWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvChivalDeFriseWeight);
            }
        });
        bDrawBridgeWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvDrawBridgeWeight);
            }
        });
        bLowBarWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvLowBarWeight);
            }
        });
        bMoatWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvMoatWeight);
            }
        });

        bPortCullisWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvPortCullisWeight);
            }
        });
        bRampartsWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvRampartWeight);
            }
        });
        bRockWallWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvRockWallWeight);
            }
        });
        bRoughTerrainWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvRoughTerrainWeight);
            }
        });
        bSallyPortWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvSallyportWeight);
            }
        });
    }

    public void IncrementTextViewValue(TextView view) {
        int i = Integer.parseInt(view.getText().toString());
        TextView tvBalanceWeights = (TextView) findViewById(R.id.tvBalanceWeights);
        if (balance >= 5) {
            i = i + 5;
            balance = balance - 5;
            tvBalanceWeights.setText("Balance : " + Integer.toString(balance));
            view.setText(Integer.toString(i));
            Button bGenerateCSV = (Button) findViewById(R.id.bExportCSV);
            bGenerateCSV.setEnabled(false);
        } else if (balance == 0) {
            Toast.makeText(getApplicationContext(), "No more balance left", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Use NumberPicker", Toast.LENGTH_SHORT).show();

        }
    }

    public void GenerateCSV(View view) {

        TeamStatsDataSource teamStatsDataSource = new TeamStatsDataSource(this);
        teamStatsDataSource.GenerateCSV();
        Toast.makeText(getApplicationContext(), "File Generated", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void LaunchNumberPicker(int min, int max, int setVal, final TextView view) {

        android.widget.NumberPicker myNumberPicker = new android.widget.NumberPicker(getApplicationContext());
        myNumberPicker.setMaxValue(max);
        myNumberPicker.setMinValue(min);
        myNumberPicker.setValue(setVal);
        setNumberPickerTextColor(myNumberPicker, Color.BLACK);


        android.widget.NumberPicker.OnValueChangeListener myValChangedListener = new android.widget.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {

                final TextView tvBalanceWeights = (TextView) findViewById(R.id.tvBalanceWeights);
                view.setText(Integer.toString(newVal));
                balance = balance - (newVal - oldVal);
                Button bGenerateCSV = (Button) findViewById(R.id.bExportCSV);
                bGenerateCSV.setEnabled(false);
                tvBalanceWeights.setText("Balance : " + Integer.toString(balance));
            }

        };

        myNumberPicker.setOnValueChangedListener(myValChangedListener);

        new AlertDialog.Builder(Weights.this).
                setView(myNumberPicker)
                .setTitle("Change Value")
                .show();
    }

}
