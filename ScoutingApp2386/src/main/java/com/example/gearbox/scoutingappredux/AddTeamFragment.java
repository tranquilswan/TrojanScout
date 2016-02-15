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
import android.widget.RadioButton;
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
        int check;

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
        EditText edtTeamNum = (EditText) view.findViewById(R.id.edtTeamNum);
        final Button btnSaveTeam = (Button) view.findViewById(R.id.btnSaveTeam);
        Button btnMainMenu = (Button) view.findViewById(R.id.btnMainMenu);
        ImageView imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);


        EditText edtDriveSyst = (EditText) view.findViewById(R.id.edtDriveSystem);
        EditText edtFuncMech = (EditText) view.findViewById(R.id.edtFuncMech);
        EditText edtTeamName = (EditText) view.findViewById(R.id.edtTeamName);
        EditText edtComments = (EditText) view.findViewById(R.id.edtComments);
        final RadioGroup rgpGoalScoring = (RadioGroup) view.findViewById(R.id.rgpGoalScoring);
        final RadioGroup rgpVision = (RadioGroup) view.findViewById(R.id.rgpVision);
        final RadioGroup rgpAutonomous = (RadioGroup) view.findViewById(R.id.rgpAutonomous);


        Bundle infoBund = getArguments();
        String updateFlag = infoBund.getString("updateTeam");
        if (updateFlag.equals("update")){
            int teamNum = infoBund.getInt("teamNum");

            edtTeamNum.setText(Integer.toString(teamNum));
            edtTeamNum.setEnabled(false);

            TeamDataSource tds = new TeamDataSource(getActivity());
            Team editTeam = tds.getTeam(teamNum);

            edtDriveSyst.setText(editTeam.getmDriveSystem());
            edtFuncMech.setText(editTeam.getmFuncMech());
            edtTeamName.setText(editTeam.getmTeamName());
            edtComments.setText(editTeam.getmComments());

            RadioButton radNoGoal = (RadioButton) view.findViewById(R.id.radNoGoal);
            RadioButton radLower = (RadioButton) view.findViewById(R.id.radLowerGoal);
            RadioButton radUpper = (RadioButton) view.findViewById(R.id.radUpperGoal);
            RadioButton radBoth = (RadioButton) view.findViewById(R.id.radBothGoal);

            if (editTeam.getmGoalType().equals("Upper")){
                radUpper.setChecked(true);
            }else if(editTeam.getmGoalType().equals("Lower")){
                radLower.setChecked(true);
            }else if(editTeam.getmGoalType().equals("Both")){
                radBoth.setChecked(true);
            }else if(editTeam.getmGoalType().equals("None")){
                radNoGoal.setChecked(true);
            }

            RadioButton radVisionYes = (RadioButton) view.findViewById(R.id.radVisionYes);
            RadioButton radVisionNo = (RadioButton) view.findViewById(R.id.radVisionNo);

            if(editTeam.isVisionExist() == 1){
                radVisionYes.setChecked(true);
            }else if(editTeam.isVisionExist() == 0){
                radVisionNo.setChecked(true);
            }

            RadioButton radAutonomousYes = (RadioButton) view.findViewById(R.id.radAutonomousYes);
            RadioButton radAutonomousNo = (RadioButton) view.findViewById(R.id.radAutonomousNo);

            if(editTeam.isAutonomousExist() == 1){
                radAutonomousYes.setChecked(true);
            }else if (editTeam.isAutonomousExist() == 0 ){
                radAutonomousNo.setChecked(true);
            }

//            imgThumbnail = (ImageView) getActivity().findViewById(R.id.imgThumbnail);
//
//            File outPutImageFile = Uri.fromFile(Uri.parse(editTeam.getmPicLoc()));
////            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
//            Bitmap bitmap = decodeSampledBitmapFromFile(editTeam.getmPicLoc(), 400, 400);
//            imgThumbnail.setImageBitmap(bitmap);


        } else if (updateFlag.equals("noUpdate")) {
            Toast.makeText(getActivity(), "UpdateFlag:" + updateFlag, Toast.LENGTH_SHORT).show();
        }
            //btnTakePicture = (Button) view.findViewById(R.id.btnTakePicture);
            btnTakePicture.setEnabled(false);
//        final Button btnSaveTeam = (Button) view.findViewById(R.id.btnSaveTeam);
            btnSaveTeam.setEnabled(false);
            //        final EditText edtTeamNum = (EditText) view.findViewById(R.id.edtTeamNum);
            edtTeamNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    btnTakePicture.setEnabled(true);
                    btnSaveTeam.setEnabled(true);
                } else {
                    btnTakePicture.setEnabled(false);
                    btnSaveTeam.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnMainMenu = (Button) view.findViewById(R.id.btnMainMenu);
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
        imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
        imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle picLoc = new Bundle();
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

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction()
                        .replace(R.id.fragContainer, new IntroPageFragment(), IntroPageFragment.TAG)
                        .commit();
            }
        });



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
        //UI Element References
        EditText edtTeamNum = (EditText) getView().findViewById(R.id.edtTeamNum);
        EditText edtDriveSyst = (EditText) getView().findViewById(R.id.edtDriveSystem);
        EditText edtFuncMech = (EditText) getView().findViewById(R.id.edtFuncMech);
        EditText edtTeamName = (EditText) getView().findViewById(R.id.edtTeamName);
        EditText edtComments = (EditText) getView().findViewById(R.id.edtComments);
        final RadioGroup rgpGoalScoring = (RadioGroup) getView().findViewById(R.id.rgpGoalScoring);
        final RadioGroup rgpVision = (RadioGroup) getView().findViewById(R.id.rgpVision);
        final RadioGroup rgpAutonomous = (RadioGroup) getView().findViewById(R.id.rgpAutonomous);

        //Getting the input form the UI Elements
        int teamNum = Integer.parseInt(edtTeamNum.getText().toString());
        String driveSystemInfo = edtDriveSyst.getText().toString();
        String funcMechInfo = edtFuncMech.getText().toString();
        String teamName = edtTeamName.getText().toString();
        String comments = edtComments.getText().toString();

        //Setting radio button values
        if (rgpVision.getCheckedRadioButtonId() == R.id.radVisionYes) {
            visionExist = 1;
        } else if (rgpVision.getCheckedRadioButtonId() == R.id.radVisionNo) {
            visionExist = 0;
        }

        //Setting radio button values
        if (rgpAutonomous.getCheckedRadioButtonId() == R.id.radAutonomousYes) {
            autonomousExists = 1;
        } else if (rgpAutonomous.getCheckedRadioButtonId() == R.id.radAutonomousNo) {
            autonomousExists = 0;
        }

        //Setting radio button values
        if (rgpGoalScoring.getCheckedRadioButtonId() == R.id.radUpperGoal) {
            goalType = "Upper";
        } else if (rgpGoalScoring.getCheckedRadioButtonId() == R.id.radLowerGoal) {
            goalType = "Lower";
        } else if (rgpGoalScoring.getCheckedRadioButtonId() == R.id.radBothGoal) {
            goalType = "Both";
        } else if (rgpGoalScoring.getCheckedRadioButtonId() == R.id.radNoGoal) {
            goalType = "None";
        }

        return new Team(teamNum, outputFileLoc.toString(), driveSystemInfo, funcMechInfo, goalType, visionExist, autonomousExists, teamName, comments);
    }

//    //Method to get the Uri
//   /* private Uri getFileUri(){
//        //new Folder
//        File folder
//                = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/MyPics");
//
//        //If the folder doesnt't exist
//        if(!folder.exists()){
//            //and cannot be made
//            if(!folder.mkdirs()){
//                Log.e("AddTeamFragment", "Issue with Folder Creating: " + folder.toString());
//                return null;
//            }
//        }
//
//        //if you cannot write to the folder---THE ERROR IS HERE---
//        //Proper permissions in the manifest, still don't know whats up
//        if(!folder.canWrite()){
//            Log.e("AddTeamFragment", "Issue with writing to Folder: " + folder.toString() + " :Check Uses-Permission");
//            return null;
//        }
//
//        String fileName
//                = new SimpleDateFormat("yyMMdd_hhss", Locale.CANADA)
//                .format(new Date()) + ".jpg";
//        File file = new File(folder, fileName);
//        Log.d("AddTeamFrag", Uri.fromFile(file).toString());
//        return Uri.fromFile(file);
//    } */
//
//    //To check if an app is available to to what is required (take pic in this case)
//   /* private boolean inIntentHandlerAvailable(Intent intent){
//        PackageManager pm = getActivity().getPackageManager();
//
//        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        //return true if list.size is greater than
//        return (list.size() > 0);
//    } */

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

    // @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

            /*Bitmap bitmapFull = BitmapFactory.decodeFile(outputFileUri.getPath());
            imgThumbnail.setImageBitmap(bitmapFull.createScaledBitmap(bitmapFull, 200, 200, true)); */

            ImageView imgThumbnail = (ImageView) getActivity().findViewById(R.id.imgThumbnail);

//            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
            Bitmap bitmap = decodeSampledBitmapFromFile(outputFileLoc.getAbsolutePath(), 400, 400);
            imgThumbnail.setImageBitmap(bitmap);

            // Toast.makeText(getActivity(), Integer.toString(resultCode), Toast.LENGTH_LONG).show();


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