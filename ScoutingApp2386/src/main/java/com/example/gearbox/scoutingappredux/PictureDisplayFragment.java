package com.example.gearbox.scoutingappredux;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * A simple {@link Fragment} subclass.
 */
public class PictureDisplayFragment extends Fragment {


    public static String TAG = "PictureDisplayFragment";
    FragmentManager fm;

    public PictureDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_display, container, false);
        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragContainer, new PictureDisplayFragment(), PictureDisplayFragment.TAG);

        //back to the add team page
        Button btnGoBack = (Button) view.findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.fragContainer, new AddTeamFragment(), AddTeamFragment.TAG)
                        .commit();
            }
        });

      /*  String photoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpg";;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        ImageView imgFullSize = (ImageView) view.findViewById(R.id.imgFullSize);
        imgFullSize.setImageBitmap(bitmap); */

        //Specifies where the image is stored and passes to method for loading
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String path = directory.toString();
        loadImageFromStorage(path, view);
        return view;
    }

    //method for retrieving image from external storage
    private void loadImageFromStorage(String path, View view) {

        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = (ImageView) view.findViewById(R.id.imgFullSize);
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
