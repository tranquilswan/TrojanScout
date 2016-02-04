package com.example.gearbox.scoutingappredux;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTeamFragment extends Fragment {

    private final static int TAKE_PICTURE = 1;
    private Uri outputFileUri;
    public static final String TAG = "AddTeamFragment";
    FragmentManager fm;

    public AddTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragContainer, new AddTeamFragment(), AddTeamFragment.TAG);

        final View view = inflater.inflate(R.layout.fragment_add_team, container, false);

        Button btnMainMenu = (Button) view.findViewById(R.id.btnMainMenu);
        //Go back to the main screen
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction()
                        .replace(R.id.fragContainer, new IntroPageFragment(), IntroPageFragment.TAG)
                        .commit();
            }
        });

        //Taking Picture logic
        Button btnTakePicture = (Button) view.findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(view);
            }
        });

        //View the taken pictures
//        ImageView imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
//        imgThumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fm.beginTransaction().replace(R.id.fragContainer, new PictureDisplayFragment(), PictureDisplayFragment.TAG).commit();
//            }
//        });


        return view;
    }

    //Method to get the Uri
    private Uri getFileUri(){
        //new Folder
        File folder
                = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/MyPics");

        //If the folder doesnt't exist
        if(!folder.exists()){
            //and cannot be made
            if(!folder.mkdirs()){
                Log.e("AddTeamFragment", "Issue with Folder Creating: " + folder.toString());
                return null;
            }
        }

        //if you cannot write to the folder---THE ERROR IS HERE---
        //Proper permissions in the manifest, still don't know whats up
        if(!folder.canWrite()){
            Log.e("AddTeamFragment", "Issue with writing to Folder: " + folder.toString() + " :Check Uses-Permission");
            return null;
        }

        String fileName
                = new SimpleDateFormat("yyMMdd_hhss", Locale.CANADA)
                .format(new Date()) + ".jpg";
        File file = new File(folder, fileName);
        Log.d("AddTeamFrag", Uri.fromFile(file).toString());
        return Uri.fromFile(file);
    }

    //To check if an app is available to to what is required (take pic in this case)
    private boolean inIntentHandlerAvailable(Intent intent){
        PackageManager pm = getActivity().getPackageManager();

        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        //return true if list.size is greater than
        return (list.size() > 0);
    }

    //taking the picture
    public void takePicture(View view){
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(inIntentHandlerAvailable(pictureIntent)){
            outputFileUri = getFileUri();
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(pictureIntent, TAKE_PICTURE);
        }else{
            Toast.makeText(getActivity(), "Camera Handler not available", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK){

            Bitmap bitmapFull = BitmapFactory.decodeFile(outputFileUri.getPath());
            ImageView imgThumbnail = (ImageView) getActivity().findViewById(R.id.imgThumbnail);
            imgThumbnail.setImageBitmap(bitmapFull.createScaledBitmap(bitmapFull, 200, 200, true));
        }
    }
}