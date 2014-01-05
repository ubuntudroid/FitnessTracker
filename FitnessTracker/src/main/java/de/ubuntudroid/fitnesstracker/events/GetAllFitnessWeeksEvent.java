package de.ubuntudroid.fitnesstracker.events;

import java.util.List;

import de.ubuntudroid.fitnesstracker.model.FitnessWeek;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class GetAllFitnessWeeksEvent {

    private List<FitnessWeek> mFitnessWeeks;

    public GetAllFitnessWeeksEvent ( List<FitnessWeek> fitnessWeeks) {
        this.mFitnessWeeks = fitnessWeeks;
    }

    public List<FitnessWeek> getFitnessWeeks() {
        return mFitnessWeeks;
    }
}
