package de.ubuntudroid.fitnesstracker.base;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;

/**
 * Created by ubuntudroid on 03/01/14.
 */
public class BaseListFragment extends ListFragment {

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
         * TODO maybe we can replace this ugly workaround by an injection from AndroidModule?
         * However we shouln't store the application context in the fragment, can we instead just
         * inject a variable?
         */
        ((FitnessTrackerApplication) getActivity().getApplication()).inject(this);
    }
}
