package de.ubuntudroid.fitnesstracker.inject;

import android.content.Context;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.support.ConnectionSource;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ubuntudroid.fitnesstracker.WeekDetailActivity;
import de.ubuntudroid.fitnesstracker.WeekDetailFragment;
import de.ubuntudroid.fitnesstracker.WeekListActivity;
import de.ubuntudroid.fitnesstracker.WeekListFragment;
import de.ubuntudroid.fitnesstracker.inject.annotation.ForApplication;
import de.ubuntudroid.fitnesstracker.model.helper.DatabaseHelper;

/**
 * Created by ubuntudroid on 02/01/14.
 */

@Module(injects = {
        WeekListActivity.class,
        WeekDetailActivity.class,
        WeekListFragment.class,
        WeekDetailFragment.class
}, complete = false,
library = true)
public class CommunicationModule {

    @Provides @Singleton
    public Bus provideBus() {
        return new Bus();
    }

    @Provides @Singleton
    public ConnectionSource provideConnectionSource(DatabaseHelper dbHelper) {
        return new AndroidConnectionSource(dbHelper);
    }

    @Provides @Singleton
    public DatabaseHelper provideDatabaseHelper(@ForApplication Context context) {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }
}
