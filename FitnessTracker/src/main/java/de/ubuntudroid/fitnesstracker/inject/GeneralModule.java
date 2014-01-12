package de.ubuntudroid.fitnesstracker.inject;

import android.content.Context;

import com.path.android.jobqueue.BaseJob;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;
import de.ubuntudroid.fitnesstracker.controller.fragments.StatisticsFragment;
import de.ubuntudroid.fitnesstracker.jobs.GetAllWeeksJob;
import de.ubuntudroid.fitnesstracker.controller.FitnessWeekController;
import de.ubuntudroid.fitnesstracker.controller.fragments.WeekDetailFragment;
import de.ubuntudroid.fitnesstracker.controller.activities.WeekListActivity;
import de.ubuntudroid.fitnesstracker.controller.fragments.WeekListFragment;
import de.ubuntudroid.fitnesstracker.events.FitnessWeekBus;
import de.ubuntudroid.fitnesstracker.inject.annotation.ForApplication;
import de.ubuntudroid.fitnesstracker.jobs.GetWeekJob;
import de.ubuntudroid.fitnesstracker.jobs.PersistWeekJob;

/**
 * Created by ubuntudroid on 05/01/14.
 */
@Module(injects = {
        WeekListActivity.class,
        WeekListFragment.class,
        WeekDetailFragment.class,
        StatisticsFragment.class,
        GetWeekJob.class,
        PersistWeekJob.class,
        FitnessWeekController.class,
        GetAllWeeksJob.class
}, complete = false)
public class GeneralModule {

    @Provides @Singleton
    public Bus provideBus() {
        return new FitnessWeekBus();
    }

    @Provides @Singleton
    public JobManager provideJobManager(@ForApplication Context context) {
        Configuration config = new Configuration.Builder(context).injector(new DependencyInjector() {
            @Override
            public void inject(BaseJob job) {
                FitnessTrackerApplication.getInstance().inject(job);
            }
        }).build();
        return new JobManager(context, config);
    }

}
