package de.ubuntudroid.fitnesstracker.events;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class FitnessWeekBus extends Bus {

    private final Handler mainThread = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    FitnessWeekBus.super.post(event);
                }
            });
        }
    }

}
