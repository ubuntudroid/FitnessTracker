package de.ubuntudroid.fitnesstracker.controller.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.shamanland.fab.ShowHideOnScroll;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.R;
import de.ubuntudroid.fitnesstracker.controller.FitnessWeekController;
import de.ubuntudroid.fitnesstracker.controller.base.BaseFragmentActivity;
import de.ubuntudroid.fitnesstracker.controller.fragments.WeekDetailFragment;
import de.ubuntudroid.fitnesstracker.controller.fragments.WeekListFragment;
import de.ubuntudroid.fitnesstracker.events.WeekSelectedEvent;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;


/**
 * An activity representing a list of Weeks. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link WeekDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link de.ubuntudroid.fitnesstracker.controller.fragments.WeekListFragment} and the item details
 * (if present) is a {@link de.ubuntudroid.fitnesstracker.controller.fragments.WeekDetailFragment}.
 */
public class WeekListActivity extends BaseFragmentActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Inject
    Bus mEventBus;

    @Inject
    FitnessWeekController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_list);

        mEventBus.register(this);

        WeekListFragment weekListFragment = (WeekListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.week_list);
        if (findViewById(R.id.week_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            weekListFragment.setActivateOnItemClick(true);
        } else {
            weekListFragment.setOnTouchListener(new ShowHideOnScroll(findViewById(R.id.add_week_button)));
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    protected void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();
    }

    @SuppressWarnings("PublicMethodNotExposedInInterface")
    public void onAddWeek ( final View view ) {
        final int weekNumber;
        if (mController.getFitnessWeeks().size() > 0) {
            weekNumber = mController.getFitnessWeeks().get(0).getWeekNumber() + 1;
        } else {
            weekNumber = 1;
        }
        mController.addWeek(new FitnessWeek(weekNumber));
        view.setEnabled(false);

        // we have to wait until the new week has been successfully added, this is somewhat hacky but works
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
                weekSelected(new WeekSelectedEvent(weekNumber, view, WeekSelectedEvent.ANIMATION_TYPE_SCENE_TRANSITION));
            }
        }, 500);
    }

    @Subscribe
    public void weekSelected(@SuppressWarnings("MethodParameterOfConcreteClass") WeekSelectedEvent event) {
        int weekId = event.getWeekId();
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(WeekDetailFragment.ARG_ITEM_ID, weekId);
            Fragment fragment = new WeekDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.week_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, WeekDetailActivity.class);
            detailIntent.putExtra(WeekDetailFragment.ARG_ITEM_ID, weekId);
            View sourceView = event.getView();
            if (sourceView != null) {
                ActivityOptions activityOptions;
                if (event.getDesiredAnimationType() == WeekSelectedEvent.ANIMATION_TYPE_SCALE) {
                    activityOptions = ActivityOptions.makeScaleUpAnimation(sourceView, sourceView.getWidth() / 2, sourceView.getHeight() / 2, sourceView.getWidth(), sourceView.getHeight());
                } else {
                    activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, sourceView, "week_detail_container");
                }
                startActivity(detailIntent, activityOptions.toBundle());
            } else {
                startActivity(detailIntent);
            }
        }
    }
}
