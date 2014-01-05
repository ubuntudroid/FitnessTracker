package de.ubuntudroid.fitnesstracker.events;

import de.ubuntudroid.fitnesstracker.model.FitnessWeek;

/**
 * Created by ubuntudroid on 04/01/14.
 */
public class WeekChangedEvent {
    private FitnessWeek mWeek;

    public WeekChangedEvent(FitnessWeek week) {
        this.mWeek = week;
    }

    public FitnessWeek getWeek() {
        return mWeek;
    }
}
