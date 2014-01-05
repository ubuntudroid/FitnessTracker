package de.ubuntudroid.fitnesstracker;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import de.ubuntudroid.fitnesstracker.inject.AndroidModule;
import de.ubuntudroid.fitnesstracker.inject.GeneralModule;
import de.ubuntudroid.fitnesstracker.inject.JobModule;

/**
 * Created by ubuntudroid on 02/01/14.
 */
public class FitnessTrackerApplication extends Application{

    private ObjectGraph mObjectGraph;
    private static FitnessTrackerApplication application;

    public static FitnessTrackerApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        mObjectGraph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                new AndroidModule(this),
                new GeneralModule(),
                new JobModule()
        );
    }

    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }

    public void inject(Object object) {
        mObjectGraph.inject(object);
    }
}
