package com.example.gearbox.scoutingappredux;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

public class TradeData extends AppCompatActivity {

    public final static String TAG = "IntroPageFragment";

    ArrayAdapter<String> adapter;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                adapter.add(device.getName() + "\n" + device.getAddress());
                PopulateListViewDiscoverable();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            new AlertDialog.Builder(this)
                    .setTitle("NO BLUETOOTH CAPABILITY")
                    .setMessage("The device does not support bluetooth.")
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
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent discoverableIntent = new
                        Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivityForResult(discoverableIntent, 30);
            } else {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                mBluetoothAdapter.startDiscovery();
            }
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        if (requestCode == 30) {
            if (resultCode == 300) {
                Toast.makeText(this, "Bluetooth is now Enabled", Toast.LENGTH_LONG).show();
                Log.v(TAG, "Bluetooth enabled");
//                PopulateListViewPaired();
                View v = null;
                StartDiscovery(v);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Error occured while enabling.Leaving the application..", Toast.LENGTH_LONG).show();
                Log.v(TAG, "Bluetooth Not Enabled..Permission Denied");
                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(1);
                //System.exit(0);
                //finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }//onActivityResult

    private void PopulateListViewDiscoverable() {
        ListView listView = (ListView) findViewById(R.id.lvDevices);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.disable();
        Toast.makeText(this, "Bluetooth is Disabled", Toast.LENGTH_LONG).show();
        Log.v(TAG, "Bluetooth Disabled OnDestroy");

        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void StartDiscovery(View view) {
        adapter.clear();
        ListView listView = (ListView) findViewById(R.id.lvDevices);
        listView.setAdapter(adapter);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.cancelDiscovery();
        mBluetoothAdapter.startDiscovery();
    }

    private void PopulateListViewPaired() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
// If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                adapter.add(device.getName() + "\n" + device.getAddress());
            }
        }


        ListView listView = (ListView) findViewById(R.id.lvDevices);
        listView.setAdapter(adapter);
    }
}
