package com.example.gearbox.scoutingappredux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


    }

    private void updateTeams(ListView lvw) {
        TeamDataSource tds = new TeamDataSource(this);
        List<Team> team = tds.getTeams();
        ArrayAdapter<Team> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, team);
        lvw.setAdapter(adapter);
    }

}
