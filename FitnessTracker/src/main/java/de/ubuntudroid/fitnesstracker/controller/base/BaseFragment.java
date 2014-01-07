package de.ubuntudroid.fitnesstracker.controller.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;

/**
 * Created by ubuntudroid on 03/01/14.
 */
public class BaseFragment extends Fragment {

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Perform injection so that when this call returns all dependencies will be available for use.
        FitnessTrackerApplication.getInstance().inject(this);
    }
}
