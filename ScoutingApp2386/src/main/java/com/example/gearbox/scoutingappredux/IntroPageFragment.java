package com.example.gearbox.scoutingappredux;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroPageFragment extends Fragment {

    public final static String TAG = "IntroPageFragment";
    FragmentManager fm;

    public IntroPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fm = getFragmentManager();
        //fm.beginTransaction().replace(R.id.fragContainer, new IntroPageFragment(), IntroPageFragment.TAG);

        final View view = inflater.inflate(R.layout.fragment_intro_page, container, false);

        FloatingActionButton btnAddTeam = (FloatingActionButton) view.findViewById(R.id.btnAddTeam);

        btnAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm.beginTransaction()
                        .replace(R.id.fragContainer, new AddTeamFragment(), AddTeamFragment.TAG)
                        .commit();

            }
        });


        return view;
    }

}
