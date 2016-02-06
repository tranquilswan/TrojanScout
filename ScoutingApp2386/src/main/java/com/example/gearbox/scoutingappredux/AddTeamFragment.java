package com.example.gearbox.scoutingappredux;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public static final String TAG = "AddTeamFragment";
    private final static int TAKE_PICTURE = 1;
    // PERMISSION_REQUEST_CODE is the write permission constant.
    final int PERMISSION_REQUEST_CODE = 5;
    FragmentManager fm;
    Button btnTakePicture;
    private Uri outputFileUri;

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

        //Write Permission Request For Marshmallow Devices
        RequestWritePermission();

        //Taking Picture logic
        btnTakePicture = (Button) view.findViewById(R.id.btnTakePicture);
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

    private void RequestWritePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the picture taking buttonn now...
                    Toast.makeText(getActivity(), "Picture Taking Functionality Disabled!! Please grant write permission", Toast.LENGTH_LONG).show();
                    btnTakePicture.setEnabled(false);
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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