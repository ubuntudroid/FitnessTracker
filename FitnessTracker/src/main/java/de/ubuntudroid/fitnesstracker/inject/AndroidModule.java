package de.ubuntudroid.fitnesstracker.inject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;
import de.ubuntudroid.fitnesstracker.inject.annotation.ForApplication;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module(library = true)
public class AndroidModule {

    private final FitnessTrackerApplication application;

    public AndroidModule(FitnessTrackerApplication application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

}
