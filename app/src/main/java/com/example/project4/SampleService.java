package com.example.project4;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class SampleService extends Service
{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v("abcd","reached ");

        return super.onStartCommand(intent, flags, startId);



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
