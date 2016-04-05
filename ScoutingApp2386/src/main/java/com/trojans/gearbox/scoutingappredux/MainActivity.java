package com.trojans.gearbox.scoutingappredux;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "IntroPageFragment";

    public static Context contextOfApplication;
//    private final int REQUEST_ENABLE_BT = 1;

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        contextOfApplication = getApplicationContext();

        final int minVersionThreshold = 4;


        try {
            //This part checks weather current app  is less than the threshold app version
            //If YES the exits app and kills all processes
            //If NO then continues with everything else.
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int verCode = pInfo.versionCode;
            if (verCode < minVersionThreshold) {
                new AlertDialog.Builder(this)
                        .setTitle("Wrong App Version")
                        .setCancelable(false)
                        .setMessage("The current app version is below the minimum required version. Please update the app")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String deviceMan = android.os.Build.MANUFACTURER;
        if (deviceMan.toLowerCase().equals("samsung")) {
            if (!isPackageInstalled("net.sourceforge.opencamera", this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Third Party Camera App")
                        .setMessage("It appears that you have a Samsung Device." +
                                " For proper functioning it is recommended that you use a third party camera app." +
                                " Do you want to install it?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +
                                        "net.sourceforge.opencamera&hl=en")));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }

        if (getIntent().hasExtra("action")) {
            String action = getIntent().getExtras().getString("action");
            int teamNum = getIntent().getExtras().getInt("teamNum");

            if (action.equals("View")) {

                Bundle bundle = new Bundle();
                bundle.putString("updateTeam", "update");
                bundle.putInt("teamNum", teamNum);

                AddTeamFragment atf = new AddTeamFragment();
                atf.setArguments(bundle);

                android.app.FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragContainer, atf, AddTeamFragment.TAG)
                        .commit();
                setContentView(R.layout.activity_main);
            }
        } else {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.fragContainer, new IntroPageFragment(), IntroPageFragment.TAG).commit();
            setContentView(R.layout.activity_main);
        }

    }

    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
