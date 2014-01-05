package de.ubuntudroid.fitnesstracker.jobs;

import com.path.android.jobqueue.BaseJob;
import com.squareup.otto.Bus;

import java.sql.SQLException;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.events.GetWeekJobDoneEvent;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;
import de.ubuntudroid.fitnesstracker.model.helper.DatabaseHelper;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class GetWeekJob extends BaseJob {

    @Inject
    DatabaseHelper mDatabaseHelper;

    @Inject
    Bus mEventBus;

    private int mWeekNumber;

    public GetWeekJob ( int weekNumber ) {
        super(false, false, FitnessTrackerGroupConsts.DB_WEEK_JOB);
        this.mWeekNumber = weekNumber;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        try {
            FitnessWeek week = mDatabaseHelper.getFitnessWeekDao().queryBuilder().where().idEq(mWeekNumber).queryForFirst();
            if (week == null) {
                week = new FitnessWeek(mWeekNumber);
            }
            mEventBus.post(new GetWeekJobDoneEvent(week));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancel() {
        // TODO notify user about problem
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }

}
