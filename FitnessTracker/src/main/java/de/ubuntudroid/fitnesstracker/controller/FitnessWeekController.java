package de.ubuntudroid.fitnesstracker.controller;

import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;
import de.ubuntudroid.fitnesstracker.events.GetAllFitnessWeeksEvent;
import de.ubuntudroid.fitnesstracker.events.ModelInvalidatedEvent;
import de.ubuntudroid.fitnesstracker.events.WeekAddedEvent;
import de.ubuntudroid.fitnesstracker.events.WeekChangedEvent;
import de.ubuntudroid.fitnesstracker.jobs.GetAllWeeksJob;
import de.ubuntudroid.fitnesstracker.jobs.PersistWeekJob;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;

/**
 * Created by ubuntudroid on 05/01/14.
 */
@Singleton
public class FitnessWeekController {

    @Inject
    Bus mEventBus;

    @Inject
    JobManager mJobManager;

    private List<FitnessWeek> fitnessWeeks;

    @Inject
    public FitnessWeekController() {
        FitnessTrackerApplication.getInstance().inject(this);
        mEventBus.register(this);
    }

    public List<FitnessWeek> getFitnessWeeks() {
        if (fitnessWeeks == null) {
            forceRefresh();
        }
        return fitnessWeeks;
    }

    @Subscribe
    public void onGetAllFitnessWeeks(GetAllFitnessWeeksEvent event) {
        if (fitnessWeeks != null) {
            synchronized (fitnessWeeks) {
                fitnessWeeks.clear();
                fitnessWeeks.addAll(event.getFitnessWeeks());
            }
        } else {
            fitnessWeeks = event.getFitnessWeeks();
        }
        mEventBus.post(new ModelInvalidatedEvent());
    }

    @Subscribe
    public void onWeekChanged(WeekChangedEvent event) {
        if (fitnessWeeks != null) {
            synchronized (fitnessWeeks) {
                for (int i = 0; i < fitnessWeeks.size(); i++) {
                    FitnessWeek fitnessWeek = fitnessWeeks.get(i);
                    if (fitnessWeek.getWeekNumber() == event.getWeek().getWeekNumber()) {
                        fitnessWeeks.set(i, event.getWeek());
                    }
                }
            }
        }
    }

    @Subscribe
    public void onWeekAdded(WeekAddedEvent event) {
        if (fitnessWeeks != null) {
            synchronized (fitnessWeeks) {
                if (fitnessWeeks.size() > 0) {
                    fitnessWeeks.add(getPositionForWeekNumber(event.getWeek().getWeekNumber()) + 1, event.getWeek());
                } else {
                    // this is a workaround as we receive an IndexOutOfBoundsException when adding an object at index 0 when the list is empty - weird
                    fitnessWeeks.add(event.getWeek());
                }
            }
        }
    }

    public void forceRefresh() {
        mJobManager.addJobInBackground(1, new GetAllWeeksJob());
    }

    public void addWeek(FitnessWeek week) {
        mJobManager.addJobInBackground(1, new PersistWeekJob(week));
    }

    public int getPositionForWeekNumber(int weekNumber) {
        return fitnessWeeks.size() - weekNumber;
    }
}
