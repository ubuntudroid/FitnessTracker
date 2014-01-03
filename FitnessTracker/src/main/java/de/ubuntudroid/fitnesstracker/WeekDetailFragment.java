package de.ubuntudroid.fitnesstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.model.FitnessWeek;
import de.ubuntudroid.fitnesstracker.model.helper.DatabaseHelper;
import de.ubuntudroid.fitnesstracker.view.WeekDataInputView;
import de.ubuntudroid.fitnesstracker.view.base.BaseFragment;

/**
 * A fragment representing a single Week detail screen.
 * This fragment is either contained in a {@link WeekListActivity}
 * in two-pane mode (on tablets) or a {@link WeekDetailActivity}
 * on handsets.
 */
public class WeekDetailFragment extends BaseFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    // TODO replace direct database usage by abstraction (aka WeekModel)
    private FitnessWeek week;

    @Inject
    DatabaseHelper mDatabaseHelper;

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
            // TODO use the JobQueue to load the data from DB
            try {
                week = mDatabaseHelper.getFitnessWeekDao().queryBuilder().where().idEq(getArguments().getInt(ARG_ITEM_ID)).queryForFirst();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (week != null) {
            if (week.getWeight() >= 0) {
                WeekDataInputView weightView = (WeekDataInputView) rootView.findViewById(R.id.weight_text);
                weightView.setText(String.valueOf(week.getWeight()));
                weightView.setEditable(false);
            }
            if (week.getMuscleFraction() >= 0) {
                WeekDataInputView muscleView = (WeekDataInputView) rootView.findViewById(R.id.muscle_fraction_text);
                muscleView.setText(String.valueOf(week.getMuscleFraction()));
                muscleView.setEditable(false);
            }
            if (week.getWaterFraction() >= 0) {
                WeekDataInputView waterView = (WeekDataInputView) rootView.findViewById(R.id.water_fraction_text);
                waterView.setText(String.valueOf(week.getWaterFraction()));
                waterView.setEditable(false);
            }
            if (week.getFatFraction() >= 0) {
                WeekDataInputView fatView = (WeekDataInputView) rootView.findViewById(R.id.fat_fraction_text);
                fatView.setText(String.valueOf(week.getFatFraction()));
                fatView.setEditable(false);
            }
        }

        return rootView;
    }
}
