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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

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

        balance = 100;
        InitiateUILogic();
    }

    public void CalculateScores(View view) {

    }

    public void InitiateUILogic() {
        final TextView tvVoidWeight = (TextView) findViewById(R.id.tvVoidWeight);
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


        final Button bVoidWeight = (Button) findViewById(R.id.bVoidWeight);
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

        bVoidWeight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LaunchNumberPicker(0, Integer.parseInt(tvVoidWeight.getText().toString()) + balance, Integer.parseInt(tvVoidWeight.getText().toString()), tvVoidWeight);
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

        bVoidWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncrementTextViewValue(tvVoidWeight);
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
        } else if (balance == 0) {
            Toast.makeText(getApplicationContext(), "No more balance left", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Use NumberPicker", Toast.LENGTH_SHORT).show();

        }
    }

    public void GenerateCSV(View view) {
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
