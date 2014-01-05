package de.ubuntudroid.fitnesstracker.events;

import de.ubuntudroid.fitnesstracker.model.FitnessWeek;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class WeekAddedEvent {

    private FitnessWeek week;

    public WeekAddedEvent ( FitnessWeek week ) {
        this.week = week;
    }

    public FitnessWeek getWeek() {
        return week;
    }
}
