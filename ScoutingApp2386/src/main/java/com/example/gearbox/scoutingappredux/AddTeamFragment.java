package com.example.gearbox.scoutingappredux;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gearbox.scoutingappredux.db.TeamDataSource;

import java.io.File;


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
    int visionExist;
    int autonomousExists;
    String goalType;
    private File outputFileLoc;

    //private static final String TAG1 = "nicknagi";


    public AddTeamFragment() {
        // Required empty public constructor
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragContainer, new AddTeamFragment(), AddTeamFragment.TAG);

        final View view = inflater.inflate(R.layout.fragment_add_team, container, false);
        btnTakePicture = (Button) view.findViewById(R.id.btnTakePicture);
        btnTakePicture.setEnabled(false);
        EditText edtTeamNum = (EditText) view.findViewById(R.id.edtTeamNum);
        edtTeamNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1)
                    btnTakePicture.setEnabled(true);
                else {
                    btnTakePicture.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(view);
            }
        });

        Button btnSaveTeam = (Button) view.findViewById(R.id.btnSaveTeam);

        btnSaveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (outputFileLoc != null) {
                    Team team = createTeam();


                    TeamDataSource teamDS = new TeamDataSource(getActivity().getApplicationContext());

                    teamDS.saveTeam(team);

                    Toast.makeText(getActivity().getApplicationContext(), "Team " + team.getmTeamNum() + " added to database", Toast.LENGTH_SHORT).show();

                    fm.beginTransaction()
                            .replace(R.id.fragContainer, new IntroPageFragment(), IntroPageFragment.TAG)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Please take a robot picture", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //View the taken pictures
        ImageView imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
        imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Bundle picLoc = new Bundle();
                //Does'nt throw a null error
                //Only executes if picture already taken
                if (outputFileLoc != null) {
//                    picLoc.putString("picLocation", outputFileLoc.toString());
//                    final PictureDisplayFragment pdf = new PictureDisplayFragment();
//                    pdf.setArguments(picLoc);
//                    fm.beginTransaction().replace(R.id.fragContainer, pdf, PictureDisplayFragment.TAG).commit();
                    //Toast.makeText(getActivity(), "Sent Data", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
//                    Log.v(TAG1, outputFileLoc.toString());
//                    Log.v(TAG1, Uri.parse(outputFileLoc.getAbsolutePath()).toString());
                    intent.setDataAndType(Uri.fromFile(outputFileLoc), "image/*");
                    startActivity(intent);

                }
                //Info given to the user
                else {
                    Toast.makeText(getActivity(), "Please take a robot picture", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Bundle b = getArguments();
//        if (b!=null){
//            String FromWhere = b.getString("FromWhere");
//            if (FromWhere.equals("PictureDisplayFragment")){
//                Toast.makeText(getActivity(), FromWhere, Toast.LENGTH_SHORT).show();
//                Team team = ((Team) getActivity().getApplicationContext());
//                Toast.makeText(this.getActivity(),team.getmDriveSystem(),Toast.LENGTH_LONG).show();
//
//            }
//        }

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

    private Team createTeam() {
        EditText edtTeamNum = (EditText) getView().findViewById(R.id.edtTeamNum);
        EditText edtDriveSyst = (EditText) getView().findViewById(R.id.edtDriveSystem);
        EditText edtFuncMech = (EditText) getView().findViewById(R.id.edtFuncMech);
//        CheckBox chkUpperGoal = (CheckBox) getView().findViewById(R.id.chkUpperGoal);
//        CheckBox chkLowerGoal = (CheckBox) getView().findViewById(R.id.chkLowerGoal);
        final RadioGroup rgpGoalScoring = (RadioGroup) getView().findViewById(R.id.rgpGoalScoring);
        final RadioGroup rgpVision = (RadioGroup) getView().findViewById(R.id.rgpVision);
        final RadioGroup rgpAutonomous = (RadioGroup) getView().findViewById(R.id.rgpVision);
        EditText edtTeamName = (EditText) getView().findViewById(R.id.edtTeamName);

        //if (edtTeamNum.getText().toString().equalsIgnoreCase("")) {


        int teamNum = Integer.parseInt(edtTeamNum.getText().toString());

        String driveSystemInfo = edtDriveSyst.getText().toString();
        String funcMechInfo = edtFuncMech.getText().toString();
        String teamName = edtTeamName.getText().toString();

//        //final String goalType;
//        if (chkUpperGoal.isChecked() && chkLowerGoal.isChecked()) {
//            goalType = "Upper and Lower";
//        } else if (chkLowerGoal.isChecked()) {
//            goalType = "Lower";
//        } else if (chkUpperGoal.isChecked()) {
//            goalType = "Upper";
//        } else {
//            goalType = "None Selected";
//        }

        rgpGoalScoring.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = rgpGoalScoring.getCheckedRadioButtonId();
                if(checkedId == R.id.radUpperGoal){
                    goalType = "Upper";
                }else if(checkedId == R.id.radLowerGoal){
                    goalType = "Lower";
                }else if (checkedId == R.id.radBothGoal){
                    goalType = "Both";
                }else if (checkedId == R.id.radNoGoal){
                    goalType = "None";
                }
            }
        });
        //int visionExist;

        rgpVision.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = rgpVision.getCheckedRadioButtonId();

                if (checkedId == R.id.radVisionYes) {
                    visionExist = 1;
                } else if (checkedId == R.id.radVisionNo) {
                    visionExist = 0;
                }
            }
        });

        rgpAutonomous.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = rgpAutonomous.getCheckedRadioButtonId();

                if (checkedId == R.id.radAutonomousYes) {
                    autonomousExists = 1;
                } else if (checkedId == R.id.radAutonomousNo) {
                    autonomousExists = 0;
                }
            }
        });

        return new Team(teamNum, outputFileLoc.getAbsolutePath(), driveSystemInfo, funcMechInfo, goalType, visionExist, autonomousExists, teamName);
//        }else {
//            Toast.makeText(getActivity().getApplicationContext(), "Must Enter a Team Number", Toast.LENGTH_SHORT).show();
//        }
//
//        return null;
    }

    //taking the picture
    public void takePicture(View view) {
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        //The directory path can be used to save in the db...
        // path to /data/data/yourapp/app_data/imageDir
        //File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        EditText edtTeamNum = (EditText) getView().findViewById(R.id.edtTeamNum);
        String fileName = edtTeamNum.getText().toString() + ".jpg";
        //outputFileLoc = new File(directory, fileName);

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "RobotImages");
        if (!directory.isDirectory()) directory.mkdirs();
        //Log.v(TAG1, Boolean.toString(file.isDirectory()));
        outputFileLoc = new File(directory, fileName);
        //Log.v(TAG1, outputFileLoc.toString());
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFileLoc));



        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(pictureIntent, TAKE_PICTURE);
        } else {
            Toast.makeText(getActivity(), "Picture Taking Functionality Disabled!! Please grant camera permission", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

            /*Bitmap bitmapFull = BitmapFactory.decodeFile(outputFileUri.getPath());
            imgThumbnail.setImageBitmap(bitmapFull.createScaledBitmap(bitmapFull, 200, 200, true)); */

            ImageView imgThumbnail = (ImageView) getActivity().findViewById(R.id.imgThumbnail);

//            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
            Bitmap bitmap = decodeSampledBitmapFromFile(outputFileLoc.getAbsolutePath(), 400, 400);
            imgThumbnail.setImageBitmap(bitmap);


//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
////          imgThumbnail.setImageBitmap(imageBitmap);
//            try {
//                saveToInternalSorage(imageBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
                /*try {
                    saveToInternalSorage(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
        }
    }
//
//    private String saveToInternalSorage(Bitmap bitmapImage) throws IOException {
//        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
//        //The directory path can be used to save in the db...
//        // path to /data/data/yourapp/app_data/imageDir
//        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//        // Create imageDir
//        EditText edtTeamNum = (EditText) getView().findViewById(R.id.edtTeamNum);
//        String fileName = edtTeamNum.getText().toString() + ".jpg";
//        outputFileLoc = new File(directory, fileName);
//        Toast.makeText(getActivity(), outputFileLoc.toString(), Toast.LENGTH_LONG).show();
//
//
//        //directory.mkdir();
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(outputFileLoc);
//            // Use the compress method on the BitMap object to write image to the OutputStream
//            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            fos.close();
//        }
//        return directory.getAbsolutePath();
//    }
}