package example.moosa.com.musicplayer;

import android.content.Context;
import android.widget.Toast;

/*
 * Created by Moosa on 6/11/2015.
 */
public class Utilities {
    public String milliSecondToTimer(long milliSecond) {
        String finalTimerString = "";
        String secondsString = "";
        int hours = (int) (milliSecond / (1000 * 60 * 60));
        int mins = (int) (milliSecond % (1000 * 60 * 60) / (1000 * 60));
        int seconds = (int) (milliSecond / 1000) % 60;
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + mins + ":" + secondsString;
        return finalTimerString;
    }

    public int getProgressPercentage(long currentDuration, long totalDuration) {

        Double percentage = (double) 0;
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        return percentage.intValue();
    }

    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);
        return currentDuration * 1000;
    }

    public static void toast(Context c,String msg) {
        Toast.makeText(c,msg,Toast.LENGTH_SHORT).show();
    }
}
