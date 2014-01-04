package de.ubuntudroid.fitnesstracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.events.WeekChangedEvent;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;
import de.ubuntudroid.fitnesstracker.model.helper.DatabaseHelper;
import de.ubuntudroid.fitnesstracker.view.base.BaseListFragment;
import de.ubuntudroid.fitnesstracker.events.WeekSelectedEvent;

/**
 * A list fragment representing a list of Weeks. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link WeekDetailFragment}.
 */
public class WeekListFragment extends BaseListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    @Inject
    Bus mEventBus;

    // TODO replace direct database usage by abstraction (aka WeekModel)
    @Inject
    DatabaseHelper mDatabaseHelper;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A list of all fitness weeks. In most cases these objects will just have filled their weekNumber.
     */
    private List<FitnessWeek> fitnessWeeks;
    private ArrayAdapter<FitnessWeek> listAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeekListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: replace with a real list adapter.
        // TODO: use job queue for this task and show loader in the meantime
        try {
            fitnessWeeks = mDatabaseHelper.getFitnessWeekDao().queryBuilder().selectColumns("weekNumber").orderBy("weekNumber", false).query();
            // add new week at the top of the list
            if (fitnessWeeks.size() > 0) {
                fitnessWeeks.add(0, new FitnessWeek(fitnessWeeks.get(0).getWeekNumber() + 1));
            } else {
                fitnessWeeks.add(0, new FitnessWeek(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            getActivity().finish();
        }
        listAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                fitnessWeeks);
        setListAdapter(listAdapter);

        mEventBus.register(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    @Subscribe
    public void weekChanged(WeekChangedEvent event) {
        FitnessWeek week = fitnessWeeks.get(0);
        if (week.getWeekNumber() == event.getWeekId()) {
            // the last week was filled by the user, add a new one automatically
            listAdapter.insert(new FitnessWeek(week.getWeekNumber() + 1), 0);
            setActivatedPosition(1);
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        mEventBus.post(new WeekSelectedEvent(fitnessWeeks.get(position).getWeekNumber()));
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
