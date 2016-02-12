package com.example.gearbox.scoutingappredux;


import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PictureDisplayFragment extends Fragment {


    public static String TAG = "PictureDisplayFragment";
    //FragmentManager fm;

    public PictureDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_display, container, false);
        //fm = getFragmentManager();
        //fm.beginTransaction().replace(R.id.fragContainer, new PictureDisplayFragment(), PictureDisplayFragment.TAG);

        String picLocation = getArguments().getString("picLocation");

        Uri picLoc = Uri.parse(picLocation);

        Bitmap bitMapFull = BitmapFactory.decodeFile(picLoc.getPath());

        ImageView imgFull = (ImageView) view.findViewById(R.id.imgFullSize);
        imgFull.setImageBitmap(bitMapFull);

        //back to the add team page
        Button btnGoBack = (Button) view.findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            FragmentManager fm = getFragmentManager();

            //            final Bundle lol = new Bundle();
            @Override
            public void onClick(View v) {
//                lol.putString("FromWhere", "PictureDisplayFragment");
//                final AddTeamFragment pdf = new AddTeamFragment();
//                pdf.setArguments(lol);
//                fm.beginTransaction().replace(R.id.fragContainer, pdf, PictureDisplayFragment.TAG).commit();
                fm.beginTransaction().replace(R.id.fragContainer, new AddTeamFragment(), AddTeamFragment.TAG)
                        .commit();
            }
        });

        return view;
    }

}
