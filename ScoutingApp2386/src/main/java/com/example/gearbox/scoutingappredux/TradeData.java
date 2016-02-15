package com.example.gearbox.scoutingappredux;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TradeData extends AppCompatActivity {

    public final static String TAG = "IntroPageFragment";

    ArrayAdapter<String> adapter;
    List<BluetoothDevice> DeviceList;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                adapter.add(device.getName() + "\n" + device.getAddress());
                DeviceList.add(device);
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

        ListView listView = (ListView) findViewById(R.id.lvDevices);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = DeviceList.get(position);
                Log.v(TAG, device.getName().toString() + " Selected");
                Log.v(TAG, "Selected Device Address is " + device.getAddress().toString());
                Toast.makeText(getApplicationContext(), adapter.getItem(position) + " Selected", Toast.LENGTH_LONG).show();

                ConnectBluetooth(device);


            }
        });

        TextView tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        listView.setEmptyView(tvEmpty);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        DeviceList = new
                ArrayList<BluetoothDevice>();

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
        if (DeviceList != null)
            DeviceList.clear();
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

    private void ConnectBluetooth(final BluetoothDevice device) {
        //Dialog to Select either server or client and start threads respectively
        new AlertDialog.Builder(this)
                .setTitle("Connect Bluetooth")
                .setMessage("Please Select Bluetooth Connection Type")
                .setPositiveButton("SENDER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        AcceptThread thread = new AcceptThread();
                        thread.run();
                    }
                })
                .setNegativeButton("RECIPIENT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConnectThread thread = new ConnectThread(device);
                        thread.run();
                    }
                })
                .setIcon(android.R.drawable.ic_secure)
                .show();
    }
}


class AcceptThread extends Thread {
    final String MY_UUID = "7855102e-2d60-46bd-b6c0-ce75ec467bf8";
    private final BluetoothServerSocket mmServerSocket;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public AcceptThread() {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("nicknagi", UUID.fromString(MY_UUID));
        } catch (IOException e) {
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                //manageConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * Will cancel the listening socket, and cause the thread to finish
     */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
        }
    }
}


class ConnectThread extends Thread {
    final String MY_UUID = "7855102e-2d60-46bd-b6c0-ce75ec467bf8";
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
        } catch (IOException e) {
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        //manageConnectedSocket(mmSocket);
    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}