package de.ubuntudroid.fitnesstracker.events;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by ubuntudroid on 02/01/14.
 */
public class WeekSelectedEvent {

    public static final int ANIMATION_TYPE_SCALE = 0;
    public static final int ANIMATION_TYPE_SCENE_TRANSITION = 1;

    private int mWeekId;
    private View mView;
    private int mDesiredAnimationType;

    public WeekSelectedEvent(int weekId, @Nullable View view, int desiredAnimationType) {
        this.mWeekId = weekId;
        mView = view;
        mDesiredAnimationType = desiredAnimationType;
    }

    public int getWeekId() {
        return mWeekId;
    }

    @Nullable
    public View getView() {
        return mView;
    }

    public int getDesiredAnimationType() {
        return mDesiredAnimationType;
    }
}
