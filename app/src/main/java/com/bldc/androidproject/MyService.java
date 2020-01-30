package com.bldc.androidproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {

    MediaPlayer player;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /***
     * Create MusicPlayer
     */
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.plateaumusic);
        player.setLooping(true);
        player.setVolume(100, 100);
    }

    /***
     * Start MusicPlayer
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return START_STICKY;
    }

    /**
     * Stop MusicPlayer
     */
    @Override
    public void onDestroy() {
        player.pause();
    }


}