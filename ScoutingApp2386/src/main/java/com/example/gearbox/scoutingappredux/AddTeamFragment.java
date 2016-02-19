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
        if (updateFlag.equals("update")) {
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

            btnSaveTeam.setText("Update Team");
            btnTakePicture.setEnabled(false);

            if (editTeam.getmGoalType().equals("Upper")) {
                radUpper.setChecked(true);
            } else if (editTeam.getmGoalType().equals("Lower")) {
                radLower.setChecked(true);
            } else if (editTeam.getmGoalType().equals("Both")) {
                radBoth.setChecked(true);
            } else if (editTeam.getmGoalType().equals("None")) {
                radNoGoal.setChecked(true);
            }

            RadioButton radVisionYes = (RadioButton) view.findViewById(R.id.radVisionYes);
            RadioButton radVisionNo = (RadioButton) view.findViewById(R.id.radVisionNo);

            if (editTeam.isVisionExist() == 1) {
                radVisionYes.setChecked(true);
            } else if (editTeam.isVisionExist() == 0) {
                radVisionNo.setChecked(true);
            }

            RadioButton radAutonomousYes = (RadioButton) view.findViewById(R.id.radAutonomousYes);
            RadioButton radAutonomousNo = (RadioButton) view.findViewById(R.id.radAutonomousNo);

            if (editTeam.isAutonomousExist() == 1) {
                radAutonomousYes.setChecked(true);
            } else if (editTeam.isAutonomousExist() == 0) {
                radAutonomousNo.setChecked(true);
            }

            File newFile = new File(editTeam.getmPicLoc());
            outputFileLoc = newFile;

            Bitmap bitmap = decodeSampledBitmapFromFile(outputFileLoc.getAbsolutePath(), 400, 400);
            imgThumbnail.setImageBitmap(bitmap);

            imgThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);

                    intent.setDataAndType(Uri.fromFile(outputFileLoc), "image/*");
                    startActivity(intent);
                }
            });
            btnSaveTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Team team = createTeam();
                    TeamDataSource teamDS = new TeamDataSource(getActivity().getApplicationContext());
                    teamDS.updateTeam(team);

                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragContainer, new IntroPageFragment(), IntroPageFragment.TAG)
                            .commit();
                    Toast.makeText(getActivity(), "Team " + team.getmTeamNum() + " Updated", Toast.LENGTH_SHORT).show();
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


        } else if (updateFlag.equals("noUpdate")) {

            btnTakePicture.setEnabled(false);
            btnSaveTeam.setEnabled(false);
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

            RequestCameraPermission();
            //Write Permission Request For Marshmallow Devices
            RequestWritePermission();

            ////Write Permission Request For Marshmallow Devices


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

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);

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

        }
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

    private void RequestCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);

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
                    Toast.makeText(getActivity(), "Picture Taking Functionality Disabled!! Please grant Write/Camera permission", Toast.LENGTH_LONG).show();
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


    //taking the picture
    public void takePicture(View view) {
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        //The directory path can be used to save in the db...

        EditText edtTeamNum = (EditText) getView().findViewById(R.id.edtTeamNum);
        String fileName = edtTeamNum.getText().toString() + ".jpg";


        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "RobotImages");
        if (!directory.isDirectory()) directory.mkdirs();

        outputFileLoc = new File(directory, fileName);

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

            ImageView imgThumbnail = (ImageView) getActivity().findViewById(R.id.imgThumbnail);
          Bitmap bitmap = decodeSampledBitmapFromFile(outputFileLoc.getAbsolutePath(), 400, 400);
            imgThumbnail.setImageBitmap(bitmap);

        }
    }
}