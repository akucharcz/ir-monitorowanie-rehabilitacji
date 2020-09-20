package com.ibm.ir;

import java.io.InputStream;

import lombok.SneakyThrows;
import rx.Observer;
import rx.Subscription;
import rx.observables.StringObservable;
import rx.schedulers.Schedulers;

class BluetoothDataReceiver {

    @SneakyThrows
    Subscription dataStream(InputStream mmInputStream, Observer<Integer> dataReceiver) {
        return StringObservable.decode(StringObservable.from(mmInputStream)
                .asObservable(), "US-ASCII")
                .map(Integer::new)
                .map(it -> {
                    System.out.println(it);
                    return it;
                })
                .retry()
                .subscribeOn(Schedulers.io())
                .subscribe(dataReceiver);

    }

}