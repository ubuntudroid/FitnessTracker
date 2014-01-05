package de.ubuntudroid.fitnesstracker.jobs;

import com.path.android.jobqueue.BaseJob;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.events.GetAllFitnessWeeksEvent;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;
import de.ubuntudroid.fitnesstracker.model.helper.DatabaseHelper;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class GetAllWeeksJob extends BaseJob {

    @Inject
    Bus mEventBus;

    @Inject
    DatabaseHelper mDatabaseHelper;

    public GetAllWeeksJob () {
        super(false, false, FitnessTrackerGroupConsts.DB_WEEK_JOB);
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        List<FitnessWeek> fitnessWeeks = mDatabaseHelper.getFitnessWeekDao().queryBuilder().orderBy("weekNumber", false).query();
        mEventBus.post(new GetAllFitnessWeeksEvent(fitnessWeeks));
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
