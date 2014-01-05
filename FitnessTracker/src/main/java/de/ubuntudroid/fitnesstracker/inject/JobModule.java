package de.ubuntudroid.fitnesstracker.inject;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ubuntudroid.fitnesstracker.controller.activities.WeekDetailActivity;
import de.ubuntudroid.fitnesstracker.controller.activities.WeekListActivity;
import de.ubuntudroid.fitnesstracker.controller.fragments.WeekListFragment;
import de.ubuntudroid.fitnesstracker.inject.annotation.ForApplication;
import de.ubuntudroid.fitnesstracker.jobs.GetAllWeeksJob;
import de.ubuntudroid.fitnesstracker.jobs.GetWeekJob;
import de.ubuntudroid.fitnesstracker.jobs.PersistWeekJob;
import de.ubuntudroid.fitnesstracker.model.helper.DatabaseHelper;

/**
 * Created by ubuntudroid on 02/01/14.
 */

@Module(injects = {
        GetWeekJob.class,
        PersistWeekJob.class,
        GetAllWeeksJob.class
}, complete = false)
public class JobModule {

    @Provides @Singleton
    public DatabaseHelper provideDatabaseHelper(@ForApplication Context context) {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }
}
