package com.example.gearbox.scoutingappredux;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gearbox.scoutingappredux.db.TeamDataSource;

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

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
                String tNum = lvwTeamStatsNumbers.getItemAtPosition(position).toString();
                String[] tNumSplit = tNum.split(" : ");
                final Integer teamNum = Integer.parseInt(tNumSplit[0]);//make this an int

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
                                LaunchAddStats(teamNum);

                            }
                        })
                        .setNeutralButton("View Stats", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Delete Stats", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                return true;
            }
        });


    }

    private void LaunchAddStats(Integer teamNum) {
        Intent i = new Intent(this, AddStatistics.class);
        startActivity(i);
    }

    private void updateTeams(ListView lvw) {
        TeamDataSource tds = new TeamDataSource(this);
        List<Team> team = tds.getTeams();
        ArrayAdapter<Team> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, team);
        lvw.setAdapter(adapter);
    }

}
