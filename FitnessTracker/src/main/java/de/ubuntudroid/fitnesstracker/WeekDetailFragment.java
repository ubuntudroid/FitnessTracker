package de.ubuntudroid.fitnesstracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.ubuntudroid.fitnesstracker.dummy.DummyContent;
import de.ubuntudroid.fitnesstracker.view.WeekDataInputView;

/**
 * A fragment representing a single Week detail screen.
 * This fragment is either contained in a {@link WeekListActivity}
 * in two-pane mode (on tablets) or a {@link WeekDetailActivity}
 * on handsets.
 */
public class WeekDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    // TODO replace dummy item by FitnessWeek
    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeekDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // TODO use the JobQueue to load the data from DB
            mItem = DummyContent.ITEM_MAP.get(getArguments().getInt(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            if (mItem.weight >= 0) {
                WeekDataInputView weightView = (WeekDataInputView) rootView.findViewById(R.id.weight_text);
                weightView.setText(String.valueOf(mItem.weight));
                weightView.setEditable(false);
            }
            if (mItem.muscleFraction >= 0) {
                WeekDataInputView muscleView = (WeekDataInputView) rootView.findViewById(R.id.muscle_fraction_text);
                muscleView.setText(String.valueOf(mItem.muscleFraction));
                muscleView.setEditable(false);
            }
            if (mItem.waterFraction >= 0) {
                WeekDataInputView waterView = (WeekDataInputView) rootView.findViewById(R.id.water_fraction_text);
                waterView.setText(String.valueOf(mItem.waterFraction));
                waterView.setEditable(false);
            }
            if (mItem.fatFraction >= 0) {
                WeekDataInputView fatView = (WeekDataInputView) rootView.findViewById(R.id.fat_fraction_text);
                fatView.setText(String.valueOf(mItem.fatFraction));
                fatView.setEditable(false);
            }
        }

        return rootView;
    }
}
