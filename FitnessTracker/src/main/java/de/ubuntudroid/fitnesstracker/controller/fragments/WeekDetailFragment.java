package de.ubuntudroid.fitnesstracker.controller.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.ubuntudroid.fitnesstracker.R;
import de.ubuntudroid.fitnesstracker.controller.FitnessWeekController;
import de.ubuntudroid.fitnesstracker.controller.base.BaseFragment;
import de.ubuntudroid.fitnesstracker.events.ModelInvalidatedEvent;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;
import de.ubuntudroid.fitnesstracker.view.WeekDataInputView;
import de.ubuntudroid.fitnesstracker.view.WeightWeekDataInputView;

/**
 * A fragment representing a single Week detail screen.
 * This fragment is either contained in a {@link de.ubuntudroid.fitnesstracker.controller.activities.WeekListActivity}
 * in two-pane mode (on tablets) or a {@link de.ubuntudroid.fitnesstracker.controller.activities.WeekDetailActivity}
 * on handsets.
 */
public class WeekDetailFragment extends BaseFragment {
    //region public members
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    //endregion

    //region injections
    @Inject
    Bus mEventBus;

    @Inject
    FitnessWeekController mFitnessWeekController;
    //endregion

    //region Views
    @InjectView(R.id.weight_text)
    WeightWeekDataInputView weightView;
    @InjectView(R.id.muscle_fraction_text)
    WeekDataInputView muscleView;
    @InjectView(R.id.water_fraction_text)
    WeekDataInputView waterView;
    @InjectView(R.id.fat_fraction_text)
    WeekDataInputView fatView;
    @InjectView(R.id.refresh_progress)
    ProgressBar refreshProgressBar;
    //endregion

    //region private members
    private boolean isRefreshing = false;
    private FitnessWeek week;
    //endregion

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeekDetailFragment() {
    }

    //region Android lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mEventBus.register(this);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            List<FitnessWeek> fitnessWeeks = mFitnessWeekController.getFitnessWeeks();
            if (fitnessWeeks != null) {
                week = fitnessWeeks.get(mFitnessWeekController.getPositionForWeekNumber(getArguments().getInt(ARG_ITEM_ID)));
            } else {
                isRefreshing = true;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week_detail, container, false);
        if (rootView != null) {
            ButterKnife.inject(this, rootView);
            if (isRefreshing) {
                refreshProgressBar.setVisibility(View.VISIBLE);
            } else {
                updateGui();
            }
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();
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
                persistInputs();
                updateGui();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    //endregion

    //region subscriptions
    @Subscribe
    public void onModelInvalidatedEvent(ModelInvalidatedEvent event) {
        List<FitnessWeek> fitnessWeeks = mFitnessWeekController.getFitnessWeeks();
        if (fitnessWeeks != null) {
            this.week = fitnessWeeks.get(getArguments().getInt(ARG_ITEM_ID) - 1);
            updateGui();
            if (refreshProgressBar != null) {
                refreshProgressBar.setVisibility(View.GONE);
            }
            isRefreshing = false;
        } else {
            // TODO There seems to be some error with the DB, notify the user
        }
    }
    //endregion

    //region private methods
    private void updateGui() {
        if (week != null) {
            if (week.getWeight() >= 0) {
                weightView.setText(String.valueOf(week.getWeight()));
            }

            if (week.getMuscleFraction() >= 0) {
                muscleView.setText(String.valueOf(week.getMuscleFraction()));
            }

            if (week.getWaterFraction() >= 0) {
                waterView.setText(String.valueOf(week.getWaterFraction()));
            }

            if (week.getFatFraction() >= 0) {
                fatView.setText(String.valueOf(week.getFatFraction()));
            }
        }
    }

    private void persistInputs() {
        if (weightView != null) {
            String weightText = weightView.getText();
            if (!TextUtils.isEmpty(weightText)) {
                try {
                    float weight = Float.parseFloat(weightText);
                    week.setWeight(weight);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    week.setWeight(-1);
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
                    week.setMuscleFraction(-1);
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
                    week.setWaterFraction(-1);
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
                    week.setFatFraction(-1);
                }
            }
        }

        mFitnessWeekController.addWeek(week);
    }
    //endregion

}
