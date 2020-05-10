package com.ibm.ir;

import java.io.InputStream;
import java.util.Date;

import rx.Observable;
import rx.observables.StringObservable;
import rx.schedulers.Schedulers;

public class DataSaver {

    private TrainingDataStructure trainingDataStructure ;
    DataSaver() {
        trainingDataStructure = new TrainingDataStructure();
    }


    void beginListenForData(InputStream mmInputStream) {
        trainingDataStructure.start = new Date();

        Observable<byte[]> byteArray = StringObservable.from(mmInputStream)
                .subscribeOn(Schedulers.io())
                .asObservable();

        StringObservable.decode(byteArray, "US-ASCII")
                .subscribeOn(Schedulers.io())
              /*  .map(string -> {
                   System.out.println(string);
                    return string;
                })*/
                .subscribe(string -> trainingDataStructure.emgData.add(Integer.parseInt(string)));

    }

    public TrainingDataStructure collectData() {
        try {
            trainingDataStructure.end = new Date();
            System.out.println(trainingDataStructure.emgData);
            return trainingDataStructure;
        }
        finally {
            eraseDataStructure();
        }
    }

    private void eraseDataStructure() {
        trainingDataStructure.end = null;
        trainingDataStructure.start = null;
        trainingDataStructure.emgData.clear();
    }


}

/*
handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            myLabel.setText(data);
                                        }
                                    });
 */


/*
 final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    trainingDataStructure.emgData.add(Integer.getInteger(data));
                                    if (stopWorker) {
                                        Thread.currentThread().interrupt();
                                    }
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
 */