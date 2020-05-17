package com.ibm.ir;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    TextView myLabel;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    InputStream mmInputStream;
    Subscription subscription = null;
    boolean isReckordindData = false;
    DataSubscriber dataSubscriber = new DataSubscriber();
    BluetoothDataReceiver bluetoothDataReceiver = new BluetoothDataReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openButton = (Button) findViewById(R.id.open1);
        Button sendButton = (Button) findViewById(R.id.send1);
        Button closeButton = (Button) findViewById(R.id.close1);
        myLabel = (TextView) findViewById(R.id.label1);

        openButton.setOnClickListener(v -> {
            try {
                findBT();
                openBT();
            } catch (IOException ex) {
            }
        });
        //Send Button
        sendButton.setOnClickListener(v -> {
            if (!isReckordindData) {
                subscription = bluetoothDataReceiver.dataStream(mmInputStream, dataSubscriber);
                isReckordindData = true;
            } else {
                new Handler().post(() -> {
                    myLabel.setText(dataSubscriber.returnData().toString());
                    dataSubscriber.resetModel();
                });
                dataSubscriber.setEndDate();
                subscription.unsubscribe();
                isReckordindData = false;
            }
        });

        //Close button
        closeButton.setOnClickListener(v -> {
            try {
                closeBT();
            } catch (IOException ex) {
            }
        });
    }

    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            myLabel.setText("No bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-05")) {
                    mmDevice = device;
                    break;
                }
            }
        }
        myLabel.setText("Bluetooth Device Found");
    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmInputStream = mmSocket.getInputStream();

        myLabel.setText("Bluetooth Opened");
    }


    void closeBT() throws IOException {
        mmInputStream.close();
        mmSocket.close();
        myLabel.setText("Bluetooth Closed");
    }
}


