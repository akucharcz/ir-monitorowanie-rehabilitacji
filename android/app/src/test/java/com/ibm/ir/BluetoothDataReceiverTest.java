package com.ibm.ir;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import rx.observers.TestSubscriber;

public class BluetoothDataReceiverTest {
    int[] testDataArray = {10, 100, 200, 300, 20, 60};
    InputStream anyInputStream;

    @Before
    public void before() {
        int srcLength = testDataArray.length;

        ByteBuffer byteBuffer = ByteBuffer.allocate(testDataArray.length * 8);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(testDataArray);

        byte[] array = byteBuffer.array();
        anyInputStream = new ByteArrayInputStream(array);
    }

    @Test
    public void test() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        BluetoothDataReceiver bluetoothDataReceiver = new BluetoothDataReceiver();
      // bluetoothDataReceiver.dataStream(anyInputStream);
        try {
            testSubscriber.awaitValueCount(5,10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("lol");
        testSubscriber.assertReceivedOnNext(Collections.emptyList());
    }
}
