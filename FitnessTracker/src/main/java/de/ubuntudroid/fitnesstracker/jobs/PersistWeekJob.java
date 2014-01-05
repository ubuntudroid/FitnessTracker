package de.ubuntudroid.fitnesstracker.jobs;

import com.path.android.jobqueue.BaseJob;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.events.WeekAddedEvent;
import de.ubuntudroid.fitnesstracker.events.WeekChangedEvent;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;
import de.ubuntudroid.fitnesstracker.model.helper.DatabaseHelper;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class PersistWeekJob extends BaseJob {

    private FitnessWeek week;

    @Inject
    Bus mEventBus;

    @Inject
    DatabaseHelper mDatabaseHelper;

    public PersistWeekJob (FitnessWeek week) {
        /*
         TODO normally we would want this job to be persistent - however then onRun() seems never
         to be called!
          */
        super(false, false, FitnessTrackerGroupConsts.DB_WEEK_JOB);
        this.week = week;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        if (mDatabaseHelper.getFitnessWeekDao().createOrUpdate(week).isUpdated()) {
            mEventBus.post(new WeekChangedEvent(week));
        } else {
            mEventBus.post(new WeekAddedEvent(week));
        }
    }

    @Override
    protected void onCancel() {
        // TODO send event to reset GUI, notify user about problem (i.e via Toast)
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
