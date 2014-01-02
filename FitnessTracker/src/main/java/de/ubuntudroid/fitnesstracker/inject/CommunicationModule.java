package de.ubuntudroid.fitnesstracker.inject;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ubuntudroid.fitnesstracker.WeekDetailActivity;
import de.ubuntudroid.fitnesstracker.WeekListActivity;
import de.ubuntudroid.fitnesstracker.WeekListFragment;

/**
 * Created by ubuntudroid on 02/01/14.
 */

@Module(injects = {
        WeekListActivity.class,
        WeekDetailActivity.class,
        WeekListFragment.class
})
public class CommunicationModule {

    @Provides @Singleton
    public Bus provideBus() {
        return new Bus();
    }
}
