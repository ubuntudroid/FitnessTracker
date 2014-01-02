package de.ubuntudroid.fitnesstracker.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;

/**
 * Created by ubuntudroid on 02/01/14.
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Perform injection so that when this call returns all dependencies will be available for use.
        ((FitnessTrackerApplication) getApplication()).inject(this);
    }

}
