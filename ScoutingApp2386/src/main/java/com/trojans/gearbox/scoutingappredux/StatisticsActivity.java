package com.trojans.gearbox.scoutingappredux;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.trojans.gearbox.scoutingappredux.db.TeamDataSource;
import com.trojans.gearbox.scoutingappredux.db.TeamStatsDataSource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView lvwTeamStatsNumbers = (ListView) findViewById(R.id.lvwTeamStatsNumbers);
        updateTeams(lvwTeamStatsNumbers);

        TextView tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        lvwTeamStatsNumbers.setEmptyView(tvDisplay);

        lvwTeamStatsNumbers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String teamName;
                String tNum = lvwTeamStatsNumbers.getItemAtPosition(position).toString();
                String[] tNumSplit = tNum.split(" : ");
                final Integer teamNum = Integer.parseInt(tNumSplit[0]);//make this an int
                if (tNumSplit.length > 1) {
                    teamName = tNumSplit[1];
                } else {
                    teamName = "NONE";
                }
                new AlertDialog.Builder(StatisticsActivity.this)
                        .setTitle("Selection")
                        .setMessage("Select Action")
                        .setPositiveButton("Add Stats", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Bundle bundle = new Bundle();
//                                bundle.putString("updateTeam", "update");
//                                bundle.putInt("teamNum", teamNum);
//
//                                AddTeamFragment atf = new AddTeamFragment();
//                                atf.setArguments(bundle);
//
//                                fm.beginTransaction()
//                                        .replace(R.id.fragContainer, atf, AddTeamFragment.TAG)
//                                        .commit();
                                LaunchAddStats(teamNum, teamName);

                            }
                        })
                        .setNeutralButton("View Stats", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TeamStatsDataSource tsds = new TeamStatsDataSource(StatisticsActivity.this);
                                if (tsds.getCount(teamNum) != 0)
                                LaunchViewStats(teamNum, teamName);

                                else {
                                    Toast.makeText(StatisticsActivity.this, "No Data Exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Delete Stats", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TeamStatsDataSource tsds = new TeamStatsDataSource(StatisticsActivity.this);
                                List<Statistics> statisticses = new ArrayList<Statistics>();
                                if (tsds.getCount(teamNum) != 0) {
                                    statisticses = tsds.getTeam(teamNum);
                                    String[] array = new String[statisticses.size()];
                                    int i;
                                    for (i = 0; i < statisticses.size(); i++) {
                                        array[i] = Integer.toString(statisticses.get(i).getMatchID());
                                    }
                                    LaunchNumberPicker(1, tsds.getCount(teamNum), 1, teamNum, array);
                                } else {
                                    Toast.makeText(StatisticsActivity.this, "No Data Exists", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }).show();

                return true;
            }
        });


    }

    private void LaunchAddStats(Integer teamNum, String teamName) {
        Bundle bundle = new Bundle();
        bundle.putString("teamName", teamName);
        bundle.putInt("teamNum", teamNum);
        Intent i = new Intent(this, AddStatistics.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void LaunchViewStats(Integer teamNum, String teamName) {
        Bundle bundle = new Bundle();
        bundle.putString("teamName", teamName);
        bundle.putInt("teamNum", teamNum);
        Intent i = new Intent(this, ViewStatisticsActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void updateTeams(ListView lvw) {
        TeamDataSource tds = new TeamDataSource(this);
        List<Team> team = tds.getTeams();
        ArrayAdapter<Team> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, team);
        lvw.setAdapter(adapter);
    }

    public NumberPicker LaunchNumberPicker(int min, int max, int setVal, final int teamNum, String[] array) {

        final android.widget.NumberPicker myNumberPicker = new android.widget.NumberPicker(getApplicationContext());
        myNumberPicker.setMaxValue(max);
        myNumberPicker.setMinValue(min);
        myNumberPicker.setValue(setVal);
        myNumberPicker.setDisplayedValues(array);
        setNumberPickerTextColor(myNumberPicker, Color.BLACK);

        android.widget.NumberPicker.OnValueChangeListener myValChangedListener = new android.widget.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
            }
        };

        myNumberPicker.setOnValueChangedListener(myValChangedListener);

        new android.support.v7.app.AlertDialog.Builder(StatisticsActivity.this)
                .setView(myNumberPicker)
                .setTitle("Select Match# to Delete")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new android.app.AlertDialog.Builder(StatisticsActivity.this)
                                .setTitle("Confirm")
                                .setMessage("Are you sure? MATCH# " + myNumberPicker.getValue())
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TeamStatsDataSource tsds = new TeamStatsDataSource(StatisticsActivity.this);
                                        tsds.deleteTeam(teamNum, myNumberPicker.getValue(), tsds.getTeam(teamNum));
                                        Toast.makeText(StatisticsActivity.this, " Stats for team " + Integer.toString(teamNum)
                                                + " Successfully Deleted", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }
                })
                .show();

        return myNumberPicker;
    }

}
