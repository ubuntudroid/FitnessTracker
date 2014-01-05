package de.ubuntudroid.fitnesstracker.helper;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class ConcurrencyHelper {

    public static void runOnMainThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            });
        }
    }

}
