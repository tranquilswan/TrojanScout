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

import com.example.gearbox.scoutingappredux.db.TeamDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TradeData extends AppCompatActivity {
    public static final int MESSAGE_READ = 2;
    public final static String TAG = "TradeData Activity";
    final int LAUNCH_BLUETOOTH_TYPE_DIALOG = 3;
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
    TeamDataSource tds;
    String type;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    ArrayList<String> recArray = new ArrayList<>();
                    recArray.add(new String(readBuf, 0, msg.arg1));

                    Toast.makeText(getApplicationContext(), "0: " + recArray.get(0), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "1: " + recArray.get(1), Toast.LENGTH_LONG).show();
                    Log.v(TAG, recArray.get(0));

                    String teamVal = recArray.get(0);
                    String[] teamSep = teamVal.split("#");

                    Team newTeam = new Team(Integer.parseInt(teamSep[0]), teamSep[1], teamSep[2], teamSep[3], teamSep[4], Integer.parseInt(teamSep[5]), Integer.parseInt(teamSep[6]), teamSep[7], teamSep[8]);

                    tds.saveTeam(newTeam);

                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "Thread can talk", Toast.LENGTH_LONG).show();
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

        tds = new TeamDataSource(getApplicationContext());

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
                ThreadStartWithType("AcceptThread", null);
                View v = null;
                StartDiscovery(v);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Error occured while enabling. Cannot transfer data", Toast.LENGTH_LONG).show();
                Log.v(TAG, "Bluetooth Not Enabled..Permission Denied");
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }//on ActivityResult

    private void PopulateListViewDiscoverable() {
        ListView listView = (ListView) findViewById(R.id.lvDevices);
        listView.setAdapter(adapter);

//        //Retrieve Image and convert to byte array
//        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "RobotImages");
//        File outputFileLoc = new File(directory, "2386");
//        Bitmap myBitmap = BitmapFactory.decodeFile(outputFileLoc.getAbsolutePath());
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//
//        //Converting Byte[] to image where bytearray is the recieved array
//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        File directory1 = new File(Environment.getExternalStorageDirectory() + File.separator + "RobotImages");
//        if (!directory1.isDirectory()) directory.mkdirs();
//        File outputFileLoc1 = new File(directory, "2386");
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(outputFileLoc1);
//            // Use the compress method on the BitMap object to write image to the OutputStream
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
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
                                AcceptThread thread = new AcceptThread(mHandler, HowToRunConnectedThread, tds);
                                thread.start();
                            } else if (whichThread.equals("ConnectThread")) {
                                ConnectThread thread = new ConnectThread(device, mHandler, HowToRunConnectedThread, tds);
                                thread.start();
                            }
                        }
                    })
                    .setNegativeButton("SEND", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final String HowToRunConnectedThread = "SEND";
                            type = HowToRunConnectedThread;
                            if (whichThread.equals("AcceptThread")) {
                                AcceptThread thread = new AcceptThread(mHandler, HowToRunConnectedThread, tds);
                                thread.start();
                            } else if (whichThread.equals("ConnectThread")) {
                                ConnectThread thread = new ConnectThread(device, mHandler, HowToRunConnectedThread, tds);
                                thread.start();
                            }
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        } else {
            String HowToRunConnectedThread;
            HowToRunConnectedThread = type;
            if (whichThread.equals("AcceptThread")) {
                AcceptThread thread = new AcceptThread(mHandler, HowToRunConnectedThread, tds);
                thread.start();
            } else if (whichThread.equals("ConnectThread")) {
                ConnectThread thread = new ConnectThread(device, mHandler, HowToRunConnectedThread, tds);
                thread.start();
            }
        }
    }
}


class AcceptThread extends Thread {
    final String MY_UUID = "7855102e-2d60-46bd-b6c0-ce75ec467bf8";
    final int LAUNCH_BLUETOOTH_TYPE_DIALOG = 3;
    private final BluetoothServerSocket mmServerSocket;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Handler handler;
    String HowToRunConnectedThread;
    TeamDataSource tdsFromUI;

    public AcceptThread(Handler mHandler, String type, TeamDataSource tds) {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        handler = mHandler;
        BluetoothServerSocket tmp = null;
        tdsFromUI = tds;
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

            if (socket != null) {

                try {

                    ConnectedThread thread = new ConnectedThread(socket, handler, tdsFromUI);
                    //String s = "Hello World sent by " + mBluetoothAdapter.getName();
                    if (HowToRunConnectedThread.equals("SEND")) {
                        Team team1 = tdsFromUI.getTeam(2386);

                        ArrayList<byte[]> byteArray = new ArrayList<>();

                        byteArray.add(Integer.toString(team1.getmTeamNum()).getBytes());
                        byteArray.add("#".getBytes());
                        byteArray.add(team1.getmPicLoc().getBytes());
                        byteArray.add("#".getBytes());
                        byteArray.add(team1.getmDriveSystem().getBytes());
                        byteArray.add("#".getBytes());
                        byteArray.add(team1.getmFuncMech().getBytes());
                        byteArray.add("#".getBytes());
                        byteArray.add(team1.getmGoalType().getBytes());
                        byteArray.add("#".getBytes());
                        byteArray.add(Integer.toString(team1.isVisionExist()).getBytes());
                        byteArray.add("#".getBytes());
                        byteArray.add(Integer.toString(team1.isAutonomousExist()).getBytes());
                        byteArray.add("#".getBytes());
                        byteArray.add(team1.getmTeamName().getBytes());
                        byteArray.add("#".getBytes());
                        byteArray.add(team1.getmComments().getBytes());
                        byteArray.add("#".getBytes());

                        for (int i =0; i < 18; i++){
                            thread.write(byteArray.get(i));

                        }

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
    TeamDataSource tdsFromUI;

    public ConnectThread(BluetoothDevice device, Handler mHandler, String type, TeamDataSource tds) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        handler = mHandler;
        BluetoothSocket tmp = null;
        mmDevice = device;
        HowToRunConnectedThread = type;
        tdsFromUI = tds;

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



        ConnectedThread thread = new ConnectedThread(mmSocket, handler, tdsFromUI);

        if (HowToRunConnectedThread.equals("SEND")) {
            Team team1 = tdsFromUI.getTeam(2386);

            ArrayList<byte[]> byteArray = new ArrayList<>();

            byteArray.add(Integer.toString(team1.getmTeamNum()).getBytes());
            byteArray.add("#".getBytes());
            byteArray.add(team1.getmPicLoc().getBytes());
            byteArray.add("#".getBytes());
            byteArray.add(team1.getmDriveSystem().getBytes());
            byteArray.add("#".getBytes());
            byteArray.add(team1.getmFuncMech().getBytes());
            byteArray.add("#".getBytes());
            byteArray.add(team1.getmGoalType().getBytes());
            byteArray.add("#".getBytes());
            byteArray.add(Integer.toString(team1.isVisionExist()).getBytes());
            byteArray.add("#".getBytes());
            byteArray.add(Integer.toString(team1.isAutonomousExist()).getBytes());
            byteArray.add("#".getBytes());
            byteArray.add(team1.getmTeamName().getBytes());
            byteArray.add("#".getBytes());
            byteArray.add(team1.getmComments().getBytes());
            byteArray.add("#".getBytes());

            for (int i =0; i < 18; i++){
                thread.write(byteArray.get(i));
            }
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
    TeamDataSource tdsChild;

    public ConnectedThread(BluetoothSocket socket, Handler handler, TeamDataSource tds) {
        mHandler = handler;
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        tdsChild = tds;

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