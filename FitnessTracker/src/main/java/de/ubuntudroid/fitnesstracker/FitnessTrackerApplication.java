package de.ubuntudroid.fitnesstracker;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import de.ubuntudroid.fitnesstracker.inject.AndroidModule;
import de.ubuntudroid.fitnesstracker.inject.CommunicationModule;

/**
 * Created by ubuntudroid on 02/01/14.
 */
public class FitnessTrackerApplication extends Application{

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        mObjectGraph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                new AndroidModule(this),
                new CommunicationModule()
        );
    }

    public void inject(Object object) {
        mObjectGraph.inject(object);
    }
}
