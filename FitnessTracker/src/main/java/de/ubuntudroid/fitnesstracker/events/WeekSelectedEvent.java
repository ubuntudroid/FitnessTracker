package de.ubuntudroid.fitnesstracker.events;

/**
 * Created by ubuntudroid on 02/01/14.
 */
public class WeekSelectedEvent {

    private int mWeekId;

    public WeekSelectedEvent(int weekId) {
        this.mWeekId = weekId;
    }

    public int getWeekId() {
        return mWeekId;
    }

    public void setWeekId(int mWeekId) {
        this.mWeekId = mWeekId;
    }
}
