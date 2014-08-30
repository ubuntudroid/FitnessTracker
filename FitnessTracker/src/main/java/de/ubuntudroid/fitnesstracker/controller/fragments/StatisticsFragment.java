package de.ubuntudroid.fitnesstracker.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.R;
import de.ubuntudroid.fitnesstracker.controller.FitnessWeekController;
import de.ubuntudroid.fitnesstracker.controller.base.BaseFragment;
import de.ubuntudroid.fitnesstracker.events.ModelInvalidatedEvent;
import de.ubuntudroid.fitnesstracker.events.WeekAddedEvent;
import de.ubuntudroid.fitnesstracker.events.WeekChangedEvent;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;

/**
 * Created by ubuntudroid on 12/01/14.
 */
public class StatisticsFragment extends BaseFragment {

    public static final int VISIBLE_X = 5;
    @Inject
    FitnessWeekController mFitnessWeekController;

    @Inject
    Bus mEventBus;

    private LineGraphView mGraphView;

    public StatisticsFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_statistics, container, false);

        mGraphView = new LineGraphView(getActivity(), "");
        prepareGraphView();

        refreshGraph();

        rootView.addView(mGraphView);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);

        super.onDestroy();
    }

    @Subscribe
    public void onWeekChanged(WeekChangedEvent event) {
        refreshGraph();
    }

    @Subscribe
    public void onWeekAdded(WeekAddedEvent event) {
        refreshGraph();
    }

    @Subscribe
    public void onModelInvalidated(ModelInvalidatedEvent event) {
        refreshGraph();
    }

    private void prepareGraphView() {
        mGraphView.setScrollable(true);
        mGraphView.setScalable(true);
        mGraphView.setDrawDataPoints(true);
        mGraphView.getGraphViewStyle().setHorizontalLabelsColor(getResources().getColor(android.R.color.darker_gray));
        mGraphView.getGraphViewStyle().setVerticalLabelsColor(getResources().getColor(android.R.color.darker_gray));
        mGraphView.getGraphViewStyle().setGridColor(getResources().getColor(android.R.color.darker_gray));
        mGraphView.getGraphViewStyle().setLegendWidth(200);
        mGraphView.setShowLegend(true);
        mGraphView.setLegendAlign(GraphView.LegendAlign.TOP);
    }

    private void refreshGraph() {
        List<FitnessWeek> fitnessWeeks = mFitnessWeekController.getFitnessWeeks();
        if (fitnessWeeks != null && fitnessWeeks.size() > 0) {
            GraphView.GraphViewData[] weightData = new GraphView.GraphViewData[fitnessWeeks.size()];
            for (int i = 0; i < fitnessWeeks.size(); i++) {
                FitnessWeek fitnessWeek = fitnessWeeks.get(i);
                // fill from last to first to ensure proper orientation
                weightData[weightData.length - 1 - i] = new GraphView.GraphViewData(fitnessWeek.getWeekNumber(), fitnessWeek.getWeight());
            }
            GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle = new GraphViewSeries.GraphViewSeriesStyle(getResources().getColor(R.color.detail_light_red), 5);
            GraphViewSeries graphViewSeries = new GraphViewSeries(getString(R.string.week_data_input_weight), graphViewSeriesStyle, weightData);

            mGraphView.removeAllSeries();
            mGraphView.addSeries(graphViewSeries);

            // set viewport
            int start = weightData.length - VISIBLE_X + 1;
            if (start < 1) {
                start = 1;
            }
            int end = weightData.length - start - 1;
            if (end <= start) {
                end = start + 1;
            }
            mGraphView.setViewPort(start, end);
        }
    }

}
