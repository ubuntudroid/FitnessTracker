package de.ubuntudroid.fitnesstracker.events;

import de.ubuntudroid.fitnesstracker.model.FitnessWeek;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class GetWeekJobDoneEvent {

    private FitnessWeek week;

    public GetWeekJobDoneEvent(FitnessWeek week) {
        this.week = week;
    }

    public FitnessWeek getWeek() {
        return week;
    }
}
