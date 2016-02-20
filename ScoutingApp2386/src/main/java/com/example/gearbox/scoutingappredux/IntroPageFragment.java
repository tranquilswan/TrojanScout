package com.example.gearbox.scoutingappredux;


import android.app.Fragment;
import android.app.FragmentManager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.gearbox.scoutingappredux.db.DbOpenHelper;
import com.example.gearbox.scoutingappredux.db.TeamDataSource;

import java.util.List;
import java.util.jar.Manifest;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroPageFragment extends Fragment {

    public final static String TAG = "IntroPageFragment";
    FragmentManager fm;

    final int PERMISSION_REQUEST_CODE = 5;

    public IntroPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fm = getFragmentManager();
        //fm.beginTransaction().replace(R.id.fragContainer, new IntroPageFragment(), IntroPageFragment.TAG);

        final View view = inflater.inflate(R.layout.fragment_intro_page, container, false);

        final Button btnAddTeam = (Button) view.findViewById(R.id.btnAddTeam);

        final Button btnTradeData = (Button) view.findViewById(R.id.btnTradeData);

        String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION};
        if(!hasPermissions(getActivity(), PERMISSIONS)){
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_REQUEST_CODE);
        }


        btnTradeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TradeData(v);
            }
        });


        btnAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTeamFragment atf = new AddTeamFragment();
                Bundle bundle = new Bundle();
                bundle.putString("updateTeam", "noUpdate");
                atf.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.fragContainer, atf, AddTeamFragment.TAG)
                        .commit();

            }
        });

        final ListView lvwListTeams = (ListView) view.findViewById(R.id.lvwExistingTeams);

        Button btnViewTeam = (Button) view.findViewById(R.id.btnViewTeams);
        btnViewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvwListTeams.setVisibility(View.VISIBLE);
                updateTeams(lvwListTeams);


            }
        });

        lvwListTeams.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Integer teamNum = Integer.parseInt(lvwListTeams.getItemAtPosition(position).toString());//make this an int

                Bundle bundle = new Bundle();
                bundle.putString("updateTeam", "update");
                bundle.putInt("teamNum", teamNum);

                AddTeamFragment atf = new AddTeamFragment();
                atf.setArguments(bundle);

                fm.beginTransaction()
                        .replace(R.id.fragContainer, atf, AddTeamFragment.TAG)
                        .commit();

                return true;
            }
        });


        return view;
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null){
            for (String permission : permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    private void updateTeams(ListView lvw){
        TeamDataSource tds = new TeamDataSource(getActivity());
        List<Team> team = tds.getTeams();

        ArrayAdapter<Team> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, team);
        lvw.setAdapter(adapter);
    }


    public void TradeData(View view) {
        Intent intent = new Intent(getActivity(), TradeData.class);
        startActivity(intent);
    }

}
