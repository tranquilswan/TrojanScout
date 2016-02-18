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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TradeData extends AppCompatActivity {
    public static final int MESSAGE_READ = 2;
    public final static String TAG = "TradeData Activity";
    final int LAUNCH_BLUETOOTH_TYPE_DIALOG = 3;
    ArrayAdapter<String> adapter;
    String test;
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
    String type;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    TextView tvEmpty = (TextView) findViewById(R.id.tvTitle);
                    tvEmpty.setText(readMessage);

                    // Closes Activity after getting a bluetooth reply
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String s = readMessage.substring(0, 16);
                    if (s.equals("Message sent from")) {
                        finish();
                    }
//                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "Thread can talk", Toast.LENGTH_LONG).show();
                    break;
                case LAUNCH_BLUETOOTH_TYPE_DIALOG:
                    new AlertDialog.Builder(TradeData.this)
                            .setTitle("Select Connection Type")
                            .setMessage("THINK BEFORE MAKING A CHOICE")
                            .setPositiveButton("RECIEVE", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    BluetoothSocket socket = (BluetoothSocket) msg.obj;
                                    ConnectedThread thread = new ConnectedThread(socket, mHandler);
                                    thread.start();
                                }
                            })
                            .setNegativeButton("SEND", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    BluetoothSocket socket = (BluetoothSocket) msg.obj;
                                    ConnectedThread thread = new ConnectedThread(socket, mHandler);
                                    String s = "Hello World";
                                    byte[] bytes = s.getBytes();
                                    thread.write(bytes);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    break;


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
                ThreadStartWithType("AcceptThread", null);
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
                ThreadStartWithType("ConnectThread", device);
                //ConnectBluetooth(device);


            }
        });

//        TextView tvEmpty = (TextView) findViewById(R.id.tvEmpty);
//        listView.setEmptyView(tvEmpty);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        DeviceList = new
                ArrayList<BluetoothDevice>();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy


        TestThread t = new TestThread(mHandler);
        t.start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        if (requestCode == 30) {
            if (resultCode == 300) {
                Toast.makeText(this, "Bluetooth is now Enabled", Toast.LENGTH_LONG).show();
                Log.v(TAG, "Bluetooth enabled");
//                PopulateListViewPaired();
                ThreadStartWithType("AcceptThread", null);
                View v = null;
                StartDiscovery(v);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Error occured while enabling. Cannot transfer data", Toast.LENGTH_LONG).show();
                Log.v(TAG, "Bluetooth Not Enabled..Permission Denied");
                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(1);
                //System.exit(0);
                //finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }//on ActivityResult

    private void PopulateListViewDiscoverable() {
        ListView listView = (ListView) findViewById(R.id.lvDevices);
//        TextView tvEmpty = (TextView) findViewById(R.id.tvTitle);
//        if (adapter.getCount() > 0) tvEmpty.setText("Discoverable Devices");
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {//Work on onDestroy and add label  textView
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
        Toast.makeText(this, "Bluetooth is Disabled", Toast.LENGTH_LONG).show();
        Log.v(TAG, "Bluetooth Disabled OnDestroy TradeData");

        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void StartDiscovery(View view) {
        adapter.clear();
        if (DeviceList != null)
            DeviceList.clear();
        ListView listView = (ListView) findViewById(R.id.lvDevices);
        TextView tvEmpty = (TextView) findViewById(R.id.tvTitle);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.cancelDiscovery();
        mBluetoothAdapter.startDiscovery();
    }

    public void ThreadStartWithType(final String whichThread, final BluetoothDevice device) {
        if (type == null) {
            new AlertDialog.Builder(TradeData.this)
                    .setTitle("Select Connection Type")
                    .setMessage("THINK BEFORE MAKING A CHOICE")
                    .setPositiveButton("RECIEVE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final String HowToRunConnectedThread = "RECIEVE";
                            type = HowToRunConnectedThread;
                            if (whichThread.equals("AcceptThread")) {
                                AcceptThread thread = new AcceptThread(mHandler, HowToRunConnectedThread);
                                thread.start();
                            } else if (whichThread.equals("ConnectThread")) {
                                ConnectThread thread = new ConnectThread(device, mHandler, HowToRunConnectedThread);
                                thread.start();
                            }
                        }
                    })
                    .setNegativeButton("SEND", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final String HowToRunConnectedThread = "SEND";
                            type = HowToRunConnectedThread;
                            if (whichThread.equals("AcceptThread")) {
                                AcceptThread thread = new AcceptThread(mHandler, HowToRunConnectedThread);
                                thread.start();
                            } else if (whichThread.equals("ConnectThread")) {
                                ConnectThread thread = new ConnectThread(device, mHandler, HowToRunConnectedThread);
                                thread.start();
                            }
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            String HowToRunConnectedThread;
            HowToRunConnectedThread = type;
            if (whichThread.equals("AcceptThread")) {
                AcceptThread thread = new AcceptThread(mHandler, HowToRunConnectedThread);
                thread.start();
            } else if (whichThread.equals("ConnectThread")) {
                ConnectThread thread = new ConnectThread(device, mHandler, HowToRunConnectedThread);
                thread.start();
            }
        }
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

//    private void ConnectBluetooth(final BluetoothDevice device) {
//        //Dialog to Select either server or client and start threads respectively
//        new AlertDialog.Builder(this)
//                .setTitle("Connect Bluetooth")
//                .setMessage("Please Select Bluetooth Connection Type")
//                .setPositiveButton("SENDER", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // continue with delete
//                        AcceptThread thread = new AcceptThread(mHandler);
//                        thread.run();
//                    }
//                })
//                .setNegativeButton("RECIPIENT", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ConnectThread thread = new ConnectThread(device, mHandler);
//                        thread.run();
//                    }
//                })
//                .setIcon(android.R.drawable.ic_secure)
//                .show();
//    }
}


class AcceptThread extends Thread {
    final String MY_UUID = "7855102e-2d60-46bd-b6c0-ce75ec467bf8";
    final int LAUNCH_BLUETOOTH_TYPE_DIALOG = 3;
    private final BluetoothServerSocket mmServerSocket;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Handler handler;
    String HowToRunConnectedThread;

    public AcceptThread(Handler mHandler, String type) {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        handler = mHandler;
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            HowToRunConnectedThread = type;
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("nicknagi", UUID.fromString(MY_UUID));
        } catch (IOException e) {
        }
        mmServerSocket = tmp;
    }

    public void run() {
        final String TAG = "AcceptThread";
        Log.v(TAG, "Starting AcceptThread as " + HowToRunConnectedThread);
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
                    //Handler handler = new Handler(Looper.getMainLooper());
//                    Message completeMessage =
//                            handler.obtainMessage(LAUNCH_BLUETOOTH_TYPE_DIALOG,socket);
//                    completeMessage.sendToTarget();
                    ConnectedThread thread = new ConnectedThread(socket, handler);
                    //String s = "Hello World sent by " + mBluetoothAdapter.getName();
                    if (HowToRunConnectedThread.equals("SEND")) {
                        String s = "Message sent from " + mBluetoothAdapter.getName();
                        byte[] bytes = s.getBytes();
                        thread.write(bytes);
                    } else if (HowToRunConnectedThread.equals("RECIEVE")) {
                        thread.start();
                    }

                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        Log.v(TAG, "Closing AccceptThread");
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
    final int LAUNCH_BLUETOOTH_TYPE_DIALOG = 3;
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Handler handler;
    String HowToRunConnectedThread;

    public ConnectThread(BluetoothDevice device, Handler mHandler, String type) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        handler = mHandler;
        BluetoothSocket tmp = null;
        mmDevice = device;
        HowToRunConnectedThread = type;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
        } catch (IOException e) {
        }
        mmSocket = tmp;
    }

    public void run() {
        final String TAG = "ConnectRequestThread";
        Log.v(TAG, "Starting ConnectThread as " + HowToRunConnectedThread);
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
        //Handler h = new Handler(Looper.getMainLooper());
//        Message completeMessage =
//                handler.obtainMessage(LAUNCH_BLUETOOTH_TYPE_DIALOG, mmSocket);
//        completeMessage.sendToTarget();

        ConnectedThread thread = new ConnectedThread(mmSocket, handler);
        //String s = "Hello World sent by " + mBluetoothAdapter.getName();
        if (HowToRunConnectedThread.equals("SEND")) {
            String s = "Message sent from " + mBluetoothAdapter.getName();
            byte[] bytes = s.getBytes();
            thread.write(bytes);
        } else if (HowToRunConnectedThread.equals("RECIEVE")) {
            thread.start();
        }

        Log.v(TAG, "Closing ConnectRequestThread");
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


class ConnectedThread extends Thread {
    public static final int MESSAGE_READ = 2;
    public final static String TAG = "ConnectedThread";
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler mHandler;
    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mHandler = handler;
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        final String TAG = "ConnectedThread";
        Log.v(TAG, "Starting ConnectedThread");
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity
                Log.v(TAG, "Contacting handler to send bytes");
                byte[] readBuf = (byte[]) buffer;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, bytes);
                Log.v(TAG, readMessage);
                mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
        Log.v(TAG, "Closing ConnectedThread");
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}


class TestThread extends Thread {
    final String MY_UUID = "7855102e-2d60-46bd-b6c0-ce75ec467bf8";
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Handler handler;

    public TestThread(Handler h) {
        //h=new Handler(Looper.getMainLooper());
        handler = h;
    }

    public void run() {
        handler.sendEmptyMessage(1);
    }

    /**
     * Will cancel the listening socket, and cause the thread to finish
     */
    public void cancel() {
    }

}