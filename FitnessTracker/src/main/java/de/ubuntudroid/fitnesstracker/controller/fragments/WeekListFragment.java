package de.ubuntudroid.fitnesstracker.controller.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.R;
import de.ubuntudroid.fitnesstracker.controller.FitnessWeekListAdapter;
import de.ubuntudroid.fitnesstracker.events.ModelInvalidatedEvent;
import de.ubuntudroid.fitnesstracker.events.WeekAddedEvent;
import de.ubuntudroid.fitnesstracker.events.WeekChangedEvent;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;
import de.ubuntudroid.fitnesstracker.controller.FitnessWeekController;
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

    @Inject
    FitnessWeekController mFitnessWeekController;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A list of all fitness weeks.
     */
    private List<FitnessWeek> mFitnessWeeks;
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

        setHasOptionsMenu(true);
        mEventBus.register(this);

        // TODO: replace with a real list adapter.
        // TODO: use job queue for this task and show loader in the meantime
        mFitnessWeeks = mFitnessWeekController.getFitnessWeeks();
        if (mFitnessWeeks != null) {
            listAdapter = new FitnessWeekListAdapter(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_2,
                    android.R.id.text1,
                    mFitnessWeeks);
            setListAdapter(listAdapter);
        }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.week_list_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_menu_item:
                if (mFitnessWeeks.size() > 0) {
                    mFitnessWeekController.addWeek(new FitnessWeek(mFitnessWeeks.get(0).getWeekNumber() + 1));
                } else {
                    mFitnessWeekController.addWeek(new FitnessWeek(1));
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Subscribe
    public void onWeekChanged(WeekChangedEvent event) {
        if (listAdapter != null) {
//            FitnessWeek changedWeek = event.getWeek();
//            for (int i = 0; i < mFitnessWeeks.size(); i++) {
//                FitnessWeek week = mFitnessWeeks.get(i);
//                if (week.getWeekNumber() == changedWeek.getWeekNumber()) {
//                    // replace changed week (we want this to be atomic, so we notify the adapter of the change ourselves)
//                    listAdapter.setNotifyOnChange(false);
//                    listAdapter.remove(week);
//                    listAdapter.insert(changedWeek, i);
//                    listAdapter.notifyDataSetChanged();
//                }
//            }
            listAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onWeekAdded(WeekAddedEvent event) {
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
            if (mActivatedPosition >= mFitnessWeekController.getPositionForWeekNumber(event.getWeek().getWeekNumber())) {
                setActivatedPosition(mActivatedPosition + 1);
            }
        }
    }

    @Subscribe
    public void onModelInvalidated(ModelInvalidatedEvent event) {
        mFitnessWeeks = mFitnessWeekController.getFitnessWeeks();
        if (listAdapter != null) {
            listAdapter.notifyDataSetInvalidated();
        }
        listAdapter = new FitnessWeekListAdapter(
                getActivity(),
                android.R.layout.simple_list_item_activated_2,
                android.R.id.text1,
                mFitnessWeeks);
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mActivatedPosition = position;

        mEventBus.post(new WeekSelectedEvent(mFitnessWeeks.get(position).getWeekNumber()));
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
