package com.ibm.ir;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    TextView myLabel;
    TextView measurementDate;
    TextView fullname;
    TextView sendButtonText;
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
        setContentView(R.layout.activity_home);

        String username = LoginActivity.username;
        sendButtonText = (TextView) findViewById(R.id.sendButtonText);
        ImageButton sendButton = (ImageButton) findViewById(R.id.send1);
        Button closeButton = (Button) findViewById(R.id.close1);
        ImageButton historyButton = (ImageButton) findViewById(R.id.history_btn);
        fullname = (TextView) findViewById(R.id.name);
        measurementDate = (TextView) findViewById(R.id.measurement_date);
        myLabel = (TextView) findViewById(R.id.label1);
        fullname.setText(username);
        Log.e("TAG1", username);
        try {
            if(!Build.PRODUCT.matches(".*_?sdk_?.*")){
                findBT();
                openBT();
                Toast.makeText(MainActivity.this, "Bluetooth connected", Toast.LENGTH_LONG).show();
            }
           else{
                Toast.makeText(MainActivity.this, "Bluetooth not connected. Running on emulator", Toast.LENGTH_LONG).show();
            }
        } catch (IOException ex) {
        }
        ApiUtils.getAPIService().getLastResult(username)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.e("TAG", "tag");
                        setInfo(response.body());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
        //Send Button
        sendButton.setOnClickListener(v -> {
            if (!isReckordindData) {
                dataSubscriber = new DataSubscriber();
                sendButtonText.setText("Zakończ pomiar");
                subscription = bluetoothDataReceiver.dataStream(mmInputStream, dataSubscriber);
                isReckordindData = true;
            } else {
                sendButtonText.setText("Rozpocznij nowy pomiar");
                new Handler().post(() -> {
                    myLabel.setText(dataSubscriber.returnData().getEmgData().toString());
                    ApiUtils.getAPIService().postTrainingResults(dataSubscriber.returnData())
                            .enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {
                                    Toast.makeText(MainActivity.this, "Data succesfully uploaded", Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, "Error while uploading training data", Toast.LENGTH_LONG).show();
                                }
                            });
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

        //History button
        historyButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HistoryMenuActivity.class));
            MainActivity.this.finish();
        });


    }

    private void setInfo(Object body) {
        LinkedTreeMap rs = (LinkedTreeMap) body;
        String lastResultDate = (String) rs.get("lastResultDate");
        lastResultDate = lastResultDate.replace("T", " ");
        lastResultDate = lastResultDate.substring(0, lastResultDate.length() - 4);
        measurementDate.setText(lastResultDate);
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


