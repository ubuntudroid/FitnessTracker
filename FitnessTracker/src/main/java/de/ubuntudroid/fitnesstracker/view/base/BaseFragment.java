package de.ubuntudroid.fitnesstracker.view.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;

/**
 * Created by ubuntudroid on 03/01/14.
 */
public class BaseFragment extends Fragment {

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        /*
         * TODO maybe we can replace this ugly workaround by an injection from AndroidModule?
         * However we shouln't store the application context in the fragment, can we instead just
         * inject a variable?
         */
        ((FitnessTrackerApplication) activity.getApplication()).inject(this);
    }
}
