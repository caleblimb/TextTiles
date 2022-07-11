package com.caleblimb.texttiles;

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer;
import android.view.View;

class soundManager() {

    fun playSound(sound: String, activity: Context) {

        if (sound == "move") {
            var mp = MediaPlayer.create(activity, R.raw.move);
            mp.start();
        } else if (sound == "found") {
            var mp = MediaPlayer.create(activity, R.raw.found);
            mp.start();
        }
    }

    fun startBackground(activity: Context) {
        var back = MediaPlayer.create(activity, R.raw.background);
        back.start()
    }

}
