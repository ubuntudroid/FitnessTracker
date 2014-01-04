package de.ubuntudroid.fitnesstracker.events;

/**
 * Created by ubuntudroid on 04/01/14.
 */
public class WeekChangedEvent {
    private int mWeekId;

    public WeekChangedEvent(int weekId) {
        this.mWeekId = weekId;
    }

    public int getWeekId() {
        return mWeekId;
    }

    public void setWeekId(int mWeekId) {
        this.mWeekId = mWeekId;
    }
}
