package com.example.gearbox.scoutingappredux;

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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearbox.scoutingappredux.db.TeamStatsDataSource;

import java.lang.reflect.Field;

public class AddStatistics extends AppCompatActivity {

    String teamName;
    int teamNum;

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
                    Log.w("setNumberPickerTextColor", e);
                } catch (IllegalAccessException e) {
                    Log.w("setNumberPickerTextColor", e);
                } catch (IllegalArgumentException e) {
                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        NumberPicker npTotalShots = (NumberPicker) findViewById(R.id.npTotalShots);
//        NumberPicker npLowGoals = (NumberPicker) findViewById(R.id.npLowGoals);
//        NumberPicker npHighGoal = (NumberPicker) findViewById(R.id.npHighGoals);
//        NumberPicker npChivalDeFrise = (NumberPicker) findViewById(R.id.npChivalDeFrise);
//        NumberPicker npDrawBridge = (NumberPicker) findViewById(R.id.npDrawBridge);
//        NumberPicker npLowBar = (NumberPicker) findViewById(R.id.npLowBar);
//        NumberPicker npMoat = (NumberPicker) findViewById(R.id.npMoat);
//        NumberPicker npPortCullis = (NumberPicker) findViewById(R.id.npPortCullis);
//        NumberPicker npRamparts = (NumberPicker) findViewById(R.id.npRamparts);
//        NumberPicker npRockWall = (NumberPicker) findViewById(R.id.npRockWall);
//        NumberPicker npSallyPort = (NumberPicker) findViewById(R.id.npSallyPort);
//        NumberPicker npRoughTerrain = (NumberPicker) findViewById(R.id.npRoughTerrain);

        TextView tvStatsTeamNum = (TextView) findViewById(R.id.tvStatsTeamNum);
        final TextView tvStatsTeamName = (TextView) findViewById(R.id.tvStatsTeamName);

//        SwipeNumberPicker swipeNumberPicker = (SwipeNumberPicker) findViewById(R.id.number_picker);
//        swipeNumberPicker.setNumberPickerDialogTitle("Chival De Frise");

        Button button = (Button) findViewById(R.id.bShots);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.NumberPicker myNumberPicker = new android.widget.NumberPicker(getApplicationContext());
                myNumberPicker.setMaxValue(10);
                myNumberPicker.setMinValue(0);
                setNumberPickerTextColor(myNumberPicker, Color.BLACK);

                android.widget.NumberPicker.OnValueChangeListener myValChangedListener = new android.widget.NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
                        tvStatsTeamName.setText(Integer.toString(newVal));
                    }
                };

                myNumberPicker.setOnValueChangedListener(myValChangedListener);

                new AlertDialog.Builder(AddStatistics.this).setView(myNumberPicker).show();
            }
        });


//        npTotalShots.setMinValue(0);
//        npTotalShots.setMaxValue(25);
//        npLowGoals.setMinValue(0);
//        npLowGoals.setMaxValue(15);
//        npHighGoal.setMinValue(0);
//        npHighGoal.setMaxValue(15);
//        npChivalDeFrise.setMinValue(0);
//        npChivalDeFrise.setMaxValue(10);
//        npDrawBridge.setMinValue(0);
//        npDrawBridge.setMaxValue(10);
//        npLowBar.setMinValue(0);
//        npLowBar.setMaxValue(10);
//        npMoat.setMinValue(0);
//        npMoat.setMaxValue(10);
//        npPortCullis.setMinValue(0);
//        npPortCullis.setMaxValue(10);
//        npRamparts.setMinValue(0);
//        npRamparts.setMaxValue(10);
//        npRockWall.setMinValue(0);
//        npRockWall.setMaxValue(10);
//        npSallyPort.setMinValue(0);
//        npSallyPort.setMaxValue(10);
//        npRoughTerrain.setMinValue(0);
//        npRoughTerrain.setMaxValue(10);

        teamName = getIntent().getExtras().getString("teamName");
        teamNum = getIntent().getExtras().getInt("teamNum");

        tvStatsTeamNum.setText(Integer.toString(teamNum));
        if (teamName != null)
            tvStatsTeamName.setText(teamName);

    }

    public void SaveStats(View view) {
        Statistics stats = getInputsFromUi();
        TeamStatsDataSource tsds = new TeamStatsDataSource(getApplicationContext());
        tsds.saveTeam(stats);
        Toast.makeText(this, "Stats for team " + teamNum + " added to database.", Toast.LENGTH_SHORT).show();
        finish();
    }


    public Statistics getInputsFromUi() {
        int TotalShots;
        int LowGoals;
        int HighGoals;
        int ChivalDeFrise;
        int DrawBridge;
        int LowBar;
        int Moat;
        int PortCullis;
        int Ramparts;
        int RockWall;
        int SallyPort;
        int RoughTerrain;
        int Autonomous;
        int EndGame;
        int TeamNum;
        String TeamName;
        String Comments;


//        NumberPicker npTotalShots = (NumberPicker) findViewById(R.id.npTotalShots);
//        NumberPicker npLowGoals = (NumberPicker) findViewById(R.id.npLowGoals);
//        NumberPicker npHighGoal = (NumberPicker) findViewById(R.id.npHighGoals);
//        NumberPicker npChivalDeFrise = (NumberPicker) findViewById(R.id.npChivalDeFrise);
//        NumberPicker npDrawBridge = (NumberPicker) findViewById(R.id.npDrawBridge);
//        NumberPicker npLowBar = (NumberPicker) findViewById(R.id.npLowBar);
//        NumberPicker npMoat = (NumberPicker) findViewById(R.id.npMoat);
//        NumberPicker npPortCullis = (NumberPicker) findViewById(R.id.npPortCullis);
//        NumberPicker npRamparts = (NumberPicker) findViewById(R.id.npRamparts);
//        NumberPicker npRockWall = (NumberPicker) findViewById(R.id.npRockWall);
//        NumberPicker npSallyPort = (NumberPicker) findViewById(R.id.npSallyPort);
//        NumberPicker npRoughTerrain = (NumberPicker) findViewById(R.id.npRoughTerrain);

        RadioGroup rgpAutonomous = (RadioGroup) findViewById(R.id.rgpAutonomous);
        RadioGroup rgpEndGame = (RadioGroup) findViewById(R.id.rgpEndGame);

        TextView tvTeamNum = (TextView) findViewById(R.id.tvStatsTeamNum);
        TextView tvTeamName = (TextView) findViewById(R.id.tvStatsTeamName);

        EditText edtComments = (EditText) findViewById(R.id.edtStatsComments);

//        TotalShots = npTotalShots.getValue();
//        LowGoals = npLowGoals.getValue();
//        HighGoals = npHighGoal.getValue();
//        ChivalDeFrise = npChivalDeFrise.getValue();
//        DrawBridge = npDrawBridge.getValue();
//        LowBar = npLowBar.getValue();
//        Moat = npMoat.getValue();
//        PortCullis = npPortCullis.getValue();
//        Ramparts = npRamparts.getValue();
//        RockWall = npRockWall.getValue();
//        SallyPort = npSallyPort.getValue();
//        RoughTerrain = npRoughTerrain.getValue();

        if (rgpEndGame.getCheckedRadioButtonId() == R.id.radStatsChallenge) {
            EndGame = 1;
        } else if (rgpEndGame.getCheckedRadioButtonId() == R.id.radStatsScale) {
            EndGame = 2;
        } else {
            EndGame = 0;
        }

        if (rgpAutonomous.getCheckedRadioButtonId() == R.id.radAutonomousStatYes) {
            Autonomous = 1;
        } else {
            Autonomous = 0;
        }

        TeamNum = Integer.parseInt(tvTeamNum.getText().toString());
        TeamName = tvTeamName.getText().toString();

        Comments = edtComments.getText().toString();

        return null;


    }
}
