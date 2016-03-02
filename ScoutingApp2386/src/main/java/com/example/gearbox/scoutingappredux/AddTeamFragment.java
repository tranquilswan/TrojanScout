package com.example.gearbox.scoutingappredux;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
    // PERMISSION_REQUEST_CODE is the write permission constant. LOLOL
    final int PERMISSION_REQUEST_CODE = 5;
    FragmentManager fm;
    Button btnTakePicture;
    int visionExist;
    int autonomousExists;
    String goalType;
    int challengeOrScale;
    int groupA;
    int groupB;
    int groupC;
    int groupD;
    int lowBar;
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
        final RadioGroup rgpChallengeOrScale = (RadioGroup) view.findViewById(R.id.rgpChallengeOrScale);


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
            btnTakePicture.setEnabled(true);

            //Taking Picture logic
            btnTakePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePicture(view);
                }
            });

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


            if (editTeam.getmChallengeOrScale() == 1) {
                ((RadioButton) view.findViewById(R.id.radScale)).setChecked(true);
            } else if (editTeam.getmChallengeOrScale() == 2) {
                ((RadioButton) view.findViewById(R.id.radChallenge)).setChecked(true);
            }

            //CheckBox chkGroupA = (CheckBox) view.findViewById(R.id.chkGroupA);
            if (editTeam.getmGroupA() == 1) {
                //view.findViewById(R.id.chkGroupA).setC
                ((CheckBox) view.findViewById(R.id.chkGroupA)).setChecked(true);
            }
            if (editTeam.getmGroupB() == 1) {
                ((CheckBox) view.findViewById(R.id.chkGroupB)).setChecked(true);
            }
            if (editTeam.getmGroupC() == 1) {
                ((CheckBox) view.findViewById(R.id.chkGroupC)).setChecked(true);
            }
            if (editTeam.getmGroupD() == 1) {
                ((CheckBox) view.findViewById(R.id.chkGroupD)).setChecked(true);
            }
            if (editTeam.getmLowBar() == 1) {
                ((CheckBox) view.findViewById(R.id.chkLowBar)).setChecked(true);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(getActivity(), "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(getActivity(), "portrait", Toast.LENGTH_SHORT).show();
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
        final RadioGroup rgpChallengeOrScale = (RadioGroup) getView().findViewById(R.id.rgpChallengeOrScale);


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


        if (rgpChallengeOrScale.getCheckedRadioButtonId() == R.id.radScale) {
            challengeOrScale = 1;
        } else if (rgpChallengeOrScale.getCheckedRadioButtonId() == R.id.radChallenge) {
            challengeOrScale = 2;
        } else {
            challengeOrScale = 0;
        }


        if (((CheckBox) getView().findViewById(R.id.chkGroupA)).isChecked()) {
            groupA = 1;
        } else {
            groupA = 0;
        }


        if (((CheckBox) getView().findViewById(R.id.chkGroupB)).isChecked()) {
            groupB = 1;
        } else {
            groupB = 0;
        }


        if (((CheckBox) getView().findViewById(R.id.chkGroupC)).isChecked()) {
            groupC = 1;
        } else {
            groupC = 0;
        }


        if (((CheckBox) getView().findViewById(R.id.chkGroupD)).isChecked()) {
            groupD = 1;
        } else {
            groupD = 0;
        }

        if (((CheckBox) getView().findViewById(R.id.chkLowBar)).isChecked()) {
            lowBar = 1;
        } else {
            lowBar = 0;
        }


        return new Team(teamNum, outputFileLoc.toString(), driveSystemInfo, funcMechInfo, goalType, visionExist, autonomousExists, teamName, comments, groupA, groupB, groupC, groupD, lowBar, challengeOrScale);
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