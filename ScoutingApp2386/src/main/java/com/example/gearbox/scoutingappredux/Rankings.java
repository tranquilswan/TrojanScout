package com.example.gearbox.scoutingappredux;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearbox.scoutingappredux.db.TeamDataSource;
import com.example.gearbox.scoutingappredux.db.TeamStatsDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Rankings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CalculateAverageScoreAndDisplay();

    }

    private void CalculateAverageScoreAndDisplay() {

        HashMap<String, Double> map = new HashMap<>();
        TeamDataSource tds = new TeamDataSource(this);
        TeamStatsDataSource tsds = new TeamStatsDataSource(this);

        List<Team> teams = new ArrayList<>();
        teams = tds.getTeams();
        List<Integer> teamnums = new ArrayList<>();
        int i;

        for (i = 0; i < teams.size(); i++) {
            teamnums.add(teams.get(i).getmTeamNum());
        }

        int x;

        for (x = 0; x < teamnums.size(); x++) {

            int teamnum = teamnums.get(x);
            int q;
            float SCORE = 0;
            double AverageScore;

            for (q = 1; q <= tsds.getCount(teamnum); q++) {

                Statistics statistics = tsds.getTeam(teamnum, q);
                int matchID = statistics.getMatchID();

                SCORE = SCORE + statistics.getScore();
            }

            AverageScore = SCORE / tsds.getCount(teamnum);
            //int AverageScoreInteger = Math.round(AverageScore);

            map.put(Integer.toString(teamnum), (double) Math.round(AverageScore));
        }


        LinkedHashMap<Integer, Integer> lhm = new LinkedHashMap<>();
        lhm = sortHashMapByValues(map);
        Log.v("Rankings", lhm.toString());


        final ListView lvRankings = (ListView) findViewById(R.id.lvRankings);
        TextView tvDisplay100 = (TextView) findViewById(R.id.tvDisplay100);
        lvRankings.setEmptyView(tvDisplay100);

        final List<Integer> keyList = new ArrayList<Integer>(lhm.keySet());
        List<Integer> valueList = new ArrayList<Integer>(lhm.values());
        List<String> adapterList = new ArrayList<>();


        int iterator;
        for (iterator = 0; iterator < keyList.size(); iterator++) {
            adapterList.add((iterator + 1) + " | " + "  Team # |  " + keyList.get(iterator) + "      | Score : " + valueList.get(iterator));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, adapterList);
        lvRankings.setAdapter(adapter);

        lvRankings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tNum = lvRankings.getItemAtPosition(position).toString();
                String[] tNumSplit = tNum.split(" | ");
                final Integer teamNum = Integer.parseInt(tNumSplit[8]);//make this an int
                TeamStatsDataSource tsds = new TeamStatsDataSource(getApplicationContext());
                if (tsds.getTeam(teamNum, 1) != null) {
                    String teamName = tsds.getTeam(teamNum, 1).getmTeamName();
                    LaunchViewStats(teamNum, teamName);
                } else {
                    Toast.makeText(Rankings.this, "Team Stats Do NOT Exist", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public LinkedHashMap sortHashMapByValues(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues, Collections.reverseOrder());
        Collections.sort(mapKeys, Collections.reverseOrder());

        LinkedHashMap sortedMap = new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put(key, val);
                    break;
                }

            }

        }
        return sortedMap;
    }

    private void LaunchViewStats(Integer teamNum, String teamName) {
        Bundle bundle = new Bundle();
        bundle.putString("teamName", teamName);
        bundle.putInt("teamNum", teamNum);
        Intent i = new Intent(this, ViewStatisticsActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

}
