package de.ubuntudroid.fitnesstracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import java.sql.SQLException;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.events.WeekChangedEvent;
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

    @Inject
    Bus mEventBus;

    private WeekDataInputView weightView;
    private WeekDataInputView muscleView;
    private WeekDataInputView waterView;
    private WeekDataInputView fatView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeekDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // TODO use the JobQueue to load the data from DB
            try {
                week = mDatabaseHelper.getFitnessWeekDao().queryBuilder().where().idEq(getArguments().getInt(ARG_ITEM_ID)).queryForFirst();
                if (week == null) {
                    week = new FitnessWeek(getArguments().getInt(ARG_ITEM_ID));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week_detail, container, false);
        weightView = (WeekDataInputView) rootView.findViewById(R.id.weight_text);
        muscleView = (WeekDataInputView) rootView.findViewById(R.id.muscle_fraction_text);
        waterView = (WeekDataInputView) rootView.findViewById(R.id.water_fraction_text);
        fatView = (WeekDataInputView) rootView.findViewById(R.id.fat_fraction_text);

        // Show the dummy content as text in a TextView.
        updateGui();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // TODO add edit menu item
        inflater.inflate(R.menu.week_detail_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu_item:
                if (persistInputs()) {
                    updateGui();
                    break;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void updateGui() {
        if (week != null) {
            if (week.getWeight() >= 0) {
                weightView.setText(String.valueOf(week.getWeight()));
                weightView.setEditable(false);
            }

            if (week.getMuscleFraction() >= 0) {
                muscleView.setText(String.valueOf(week.getMuscleFraction()));
                muscleView.setEditable(false);
            }

            if (week.getWaterFraction() >= 0) {
                waterView.setText(String.valueOf(week.getWaterFraction()));
                waterView.setEditable(false);
            }

            if (week.getFatFraction() >= 0) {
                fatView.setText(String.valueOf(week.getFatFraction()));
                fatView.setEditable(false);
            }
        }
    }

    private boolean persistInputs() {
        if (weightView != null) {
            String weightText = weightView.getText();
            if (!TextUtils.isEmpty(weightText)) {
                try {
                    float weight = Float.parseFloat(weightText);
                    week.setWeight(weight);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (muscleView != null) {
            String muscleText = muscleView.getText();
            if (!TextUtils.isEmpty(muscleText)) {
                try {
                    float muscleFraction = Float.parseFloat(muscleText);
                    week.setMuscleFraction(muscleFraction);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (waterView != null) {
            String waterText = waterView.getText();
            if (!TextUtils.isEmpty(waterText)) {
                try {
                    float waterFraction = Float.parseFloat(waterText);
                    week.setWaterFraction(waterFraction);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (fatView != null) {
            String fatText = fatView.getText();
            if (!TextUtils.isEmpty(fatText)) {
                try {
                    float fatFraction = Float.parseFloat(fatText);
                    week.setFatFraction(fatFraction);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }

        try {
            mDatabaseHelper.getFitnessWeekDao().createOrUpdate(week);
            mEventBus.post(new WeekChangedEvent(week.getWeekNumber()));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
