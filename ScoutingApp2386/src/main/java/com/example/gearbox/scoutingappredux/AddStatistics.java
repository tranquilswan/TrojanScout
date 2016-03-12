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

        final TextView tvShots = (TextView) findViewById(R.id.tvShots);
        final TextView tvLowGoals = (TextView) findViewById(R.id.tvLowGoals);
        final TextView tvHighGoal = (TextView) findViewById(R.id.tvHighGoal);
        final TextView tvRampart = (TextView) findViewById(R.id.tvRampart);
        final TextView tvDrawBridge = (TextView) findViewById(R.id.tvDrawBridge);
        final TextView tvChivalDeFrise = (TextView) findViewById(R.id.tvChivalDeFrise);
        final TextView tvLowBar = (TextView) findViewById(R.id.tvLowBar);
        final TextView tvRockWall = (TextView) findViewById(R.id.tvRockWall);
        final TextView tvSallyport = (TextView) findViewById(R.id.tvSallyport);
        final TextView tvMoat = (TextView) findViewById(R.id.tvMoat);
        final TextView tvRoughTerrain = (TextView) findViewById(R.id.tvRoughTerrain);
        final TextView tvPortCullis = (TextView) findViewById(R.id.tvPortCullis);

//        SwipeNumberPicker swipeNumberPicker = (SwipeNumberPicker) findViewById(R.id.number_picker);
//        swipeNumberPicker.setNumberPickerDialogTitle("Chival De Frise");

        Button bShots = (Button) findViewById(R.id.bShots);
        Button bLowGoals = (Button) findViewById(R.id.bLowGoals);
        Button bHighGoal = (Button) findViewById(R.id.bHighGoal);
        Button bLowBar = (Button) findViewById(R.id.bLowBar);
        Button bChivalDeFrise = (Button) findViewById(R.id.bChivalDeFrise);
        Button bDrawBridge = (Button) findViewById(R.id.bDrawBridge);
        Button bMoat = (Button) findViewById(R.id.bMoat);
        Button bRamparts = (Button) findViewById(R.id.bRamparts);
        Button bRockWall = (Button) findViewById(R.id.bRockWall);
        Button bRoughTerrain = (Button) findViewById(R.id.bRoughTerrain);
        Button bPortCullis = (Button) findViewById(R.id.bPortCullis);
        Button bSallyPort = (Button) findViewById(R.id.bSallyPort);

        bShots.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 25, Integer.parseInt(tvShots.getText().toString()), tvShots);
                return true;
            }
        });

        bLowGoals.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 20, Integer.parseInt(tvLowGoals.getText().toString()), tvLowGoals);
                return true;
            }
        });
        bHighGoal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 20, Integer.parseInt(tvHighGoal.getText().toString()), tvHighGoal);
                return true;
            }
        });
        bChivalDeFrise.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvChivalDeFrise.getText().toString()), tvChivalDeFrise);
                return true;
            }
        });
        bDrawBridge.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvDrawBridge.getText().toString()), tvDrawBridge);
                return true;
            }
        });
        bLowBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvLowBar.getText().toString()), tvLowBar);
                return true;
            }
        });
        bMoat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvMoat.getText().toString()), tvMoat);
                return true;
            }
        });
        bPortCullis.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvPortCullis.getText().toString()), tvPortCullis);
                return true;
            }
        });
        bRamparts.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvRampart.getText().toString()), tvRampart);
                return true;
            }
        });
        bRockWall.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvRockWall.getText().toString()), tvRockWall);
                return true;
            }
        });
        bRoughTerrain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvRoughTerrain.getText().toString()), tvRoughTerrain);
                return true;
            }
        });
        bSallyPort.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvSallyport.getText().toString()), tvSallyport);
                return true;
            }
        });



        bShots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 25, Integer.parseInt(tvShots.getText().toString()), tvShots);
            }
        });

        bLowGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 20, Integer.parseInt(tvLowGoals.getText().toString()), tvLowGoals);
            }
        });
        bHighGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 20, Integer.parseInt(tvHighGoal.getText().toString()), tvHighGoal);
            }
        });
        bChivalDeFrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvChivalDeFrise.getText().toString()), tvChivalDeFrise);
            }
        });
        bDrawBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvDrawBridge.getText().toString()), tvDrawBridge);
            }
        });
        bLowBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvLowBar.getText().toString()), tvLowBar);
            }
        });
        bMoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvMoat.getText().toString()), tvMoat);
            }
        });
        bPortCullis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvPortCullis.getText().toString()), tvPortCullis);
            }
        });
        bRamparts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvRampart.getText().toString()), tvRampart);
            }
        });
        bRockWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvRockWall.getText().toString()), tvRockWall);
            }
        });
        bRoughTerrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvRoughTerrain.getText().toString()), tvRoughTerrain);
            }
        });
        bSallyPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchNumberPicker(0, 10, Integer.parseInt(tvSallyport.getText().toString()), tvSallyport);
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

    public void LaunchNumberPicker(int min, int max, int setVal, final TextView view) {

        android.widget.NumberPicker myNumberPicker = new android.widget.NumberPicker(getApplicationContext());
        myNumberPicker.setMaxValue(max);
        myNumberPicker.setMinValue(min);
        myNumberPicker.setValue(setVal);
        setNumberPickerTextColor(myNumberPicker, Color.BLACK);

        android.widget.NumberPicker.OnValueChangeListener myValChangedListener = new android.widget.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
                view.setText(Integer.toString(newVal));

                final TextView tvLowGoals = (TextView) findViewById(R.id.tvLowGoals);
                final TextView tvHighGoal = (TextView) findViewById(R.id.tvHighGoal);
                final TextView tvShots = (TextView) findViewById(R.id.tvShots);

                if ((view.getId() == tvHighGoal.getId() || (view.getId() == tvLowGoals.getId()))) {
                    int i = Integer.parseInt(tvShots.getText().toString());
                    i = i + (newVal - oldVal);
                    tvShots.setText(Integer.toString(i));
                }
            }
        };

        myNumberPicker.setOnValueChangedListener(myValChangedListener);

        new AlertDialog.Builder(AddStatistics.this).setView(myNumberPicker).show();
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

        //EditText edtComments = (EditText) findViewById(R.id.edtStatsComments);

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

        //Comments = edtComments.getText().toString();

        return null;


    }
}
