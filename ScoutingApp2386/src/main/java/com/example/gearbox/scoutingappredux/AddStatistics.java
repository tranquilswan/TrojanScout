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
                    Log.w("setNumberPickerTextColo", e);
                } catch (IllegalAccessException e) {
                    Log.w("setNumberPickerTextColr", e);
                } catch (IllegalArgumentException e) {
                    Log.w("setNumberPickerTextColo", e);
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


        TextView tvStatsTeamNum = (TextView) findViewById(R.id.tvStatsTeamNum);
        final TextView tvStatsTeamName = (TextView) findViewById(R.id.tvStatsTeamName);

        InitiateUILogic();

        //SetTitle
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

    public void IncrementTextViewValue(TextView view) {
        int i = Integer.parseInt(view.getText().toString());
        i++;
        view.setText(Integer.toString(i));
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

        RadioGroup rgpAutonomous = (RadioGroup) findViewById(R.id.rgpAutonomous);
        RadioGroup rgpEndGame = (RadioGroup) findViewById(R.id.rgpEndGame);

        TextView tvTeamNum = (TextView) findViewById(R.id.tvStatsTeamNum);
        TextView tvTeamName = (TextView) findViewById(R.id.tvStatsTeamName);

        EditText edtComments = (EditText) findViewById(R.id.edtStatsComments);


        TotalShots = Integer.parseInt(tvShots.getText().toString());
        LowGoals = Integer.parseInt(tvLowGoals.getText().toString());
        HighGoals = Integer.parseInt(tvHighGoal.getText().toString());
        ChivalDeFrise = Integer.parseInt(tvChivalDeFrise.getText().toString());
        DrawBridge = Integer.parseInt(tvDrawBridge.getText().toString());
        LowBar = Integer.parseInt(tvLowBar.getText().toString());
        Moat = Integer.parseInt(tvMoat.getText().toString());
        PortCullis = Integer.parseInt(tvPortCullis.getText().toString());
        Ramparts = Integer.parseInt(tvRampart.getText().toString());
        RockWall = Integer.parseInt(tvRockWall.getText().toString());
        SallyPort = Integer.parseInt(tvSallyport.getText().toString());
        RoughTerrain = Integer.parseInt(tvRoughTerrain.getText().toString());

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

    public void InitiateUILogic() {
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


        Button bShots = (Button) findViewById(R.id.bShots);
        Button bLowGoals = (Button) findViewById(R.id.bLowGoals);
        final Button bHighGoal = (Button) findViewById(R.id.bHighGoal);
        Button bLowBar = (Button) findViewById(R.id.bLowBar);
        Button bChivalDeFrise = (Button) findViewById(R.id.bChivalDeFrise);
        Button bDrawBridge = (Button) findViewById(R.id.bDrawBridge);
        final Button bMoat = (Button) findViewById(R.id.bMoat);
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
                IncrementTextViewValue(tvShots);
            }
        });

        bLowGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvLowGoals);
                IncrementTextViewValue(tvShots);
            }
        });
        bHighGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvHighGoal);
                IncrementTextViewValue(tvShots);
            }
        });
        bChivalDeFrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvChivalDeFrise);
            }
        });
        bDrawBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvDrawBridge);
            }
        });
        bLowBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvLowBar);
            }
        });
        bMoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvMoat);
            }
        });

        bPortCullis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvPortCullis);
            }
        });
        bRamparts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvRampart);
            }
        });
        bRockWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvRockWall);
            }
        });
        bRoughTerrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvRoughTerrain);
            }
        });
        bSallyPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvSallyport);
            }
        });
    }
}
