package com.trojans.gearbox.scoutingappredux;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.trojans.gearbox.scoutingappredux.db.TeamDataSource;

import java.io.File;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroPageFragment extends Fragment {

    public final static String TAG = "IntroPageFragment";
    final int PERMISSION_REQUEST_CODE = 5;
    FragmentManager fm;
    Button btnViewTeam;

    public IntroPageFragment() {
        // Required empty public constructor//blah blah
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fm = getFragmentManager();

        final View view = inflater.inflate(R.layout.fragment_intro_page, container, false);

        final Button btnAddTeam = (Button) view.findViewById(R.id.btnAddTeam);

        final Button btnTeamStats = (Button) view.findViewById(R.id.btnTeamStats);

        //final Button btnTradeData = (Button) view.findViewById(R.id.btnTradeData);

        String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (!hasPermissions(getActivity(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_REQUEST_CODE);
        }


//        btnTradeData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TradeData(v);
//            }
//        });
        btnTeamStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTeamStatistics(v);
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

        btnViewTeam = (Button) view.findViewById(R.id.btnViewTeams);
        Button btnRankings = (Button) view.findViewById(R.id.btnRankings);

        btnRankings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Rankings.class);
                startActivity(i);
            }
        });
        btnViewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvwListTeams.setVisibility(View.VISIBLE);
                updateTeams(lvwListTeams);
            }
        });


        final Button btnHelp = (Button) view.findViewById(R.id.btnHelp);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartHelpActivity(v);
            }
        });

        Button btnWeights = (Button) view.findViewById(R.id.btnWeights);
        btnWeights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartWeightsActivity(v);
            }
        });

        lvwListTeams.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String tNum = lvwListTeams.getItemAtPosition(position).toString();
                String[] tNumSplit = tNum.split(" : ");
                final Integer teamNum = Integer.parseInt(tNumSplit[0]);//make this an int

                new AlertDialog.Builder(getActivity())
                        .setTitle("Selection")
                        .setMessage("Select whether to update or delete team")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bundle bundle = new Bundle();
                                bundle.putString("updateTeam", "update");
                                bundle.putInt("teamNum", teamNum);

                                AddTeamFragment atf = new AddTeamFragment();
                                atf.setArguments(bundle);

                                fm.beginTransaction()
                                        .replace(R.id.fragContainer, atf, AddTeamFragment.TAG)
                                        .commit();

                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Confirm")
                                        .setMessage("Are you sure?")
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                TeamDataSource tds = new TeamDataSource(getActivity());
                                                Team t = tds.getTeam(teamNum);
                                                tds.deleteTeam(teamNum);
                                                MovePicture(t.getmPicLoc(), teamNum);
                                                IntroPageFragment ipf = new IntroPageFragment();
                                                fm.beginTransaction().replace(R.id.fragContainer, ipf, IntroPageFragment.TAG)
                                                        .commit();
                                                //btnViewTeam.callOnClick();
                                            }
                                        })
                                        .setNeutralButton("NO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                btnViewTeam.callOnClick();
                                            }
                                        }).show();

                            }
                        }).show();

                return true;
            }
        });

        return view;
    }

    private void MovePicture(String fileLoc, int teamNum) {
        String finalFileName = Integer.toString(teamNum) + "-" + System.currentTimeMillis() + ".jpg";
        File SourceFile = new File(fileLoc);
        File FinalDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "RobotImagesDeletedBackup");
        File finalFile = new File(FinalDirectory, finalFileName);

        if (!FinalDirectory.isDirectory()) FinalDirectory.mkdirs();

        if (SourceFile.renameTo(finalFile)) {
            Log.v(TAG, "Move file successful.");
        } else {
            Log.v(TAG, "Move file failed.");
        }

    }

    private void updateTeams(ListView lvw) {
        TeamDataSource tds = new TeamDataSource(getActivity());
        List<Team> team = tds.getTeams();
        ArrayAdapter<Team> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, team);
        lvw.setAdapter(adapter);
    }


//    public void TradeData(View view) {
//        Intent intent = new Intent(getActivity(), TradeData.class);
//        startActivity(intent);
//    }

    public void StartHelpActivity(View view) {
        Intent intent = new Intent(getActivity(), HelpScreen.class);
        startActivity(intent);
    }

    public void StartTeamStatistics(View view) {
        Intent intent = new Intent(getActivity(), StatisticsActivity.class);
        startActivity(intent);
    }

    public void StartWeightsActivity(View view) {
        Intent intent = new Intent(getActivity(), Weights.class);
        startActivity(intent);
    }

}