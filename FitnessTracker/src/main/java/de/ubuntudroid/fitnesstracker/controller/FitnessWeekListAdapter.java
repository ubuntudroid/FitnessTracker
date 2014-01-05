package de.ubuntudroid.fitnesstracker.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;
import de.ubuntudroid.fitnesstracker.inject.annotation.ForApplication;
import de.ubuntudroid.fitnesstracker.model.FitnessWeek;

/**
 * Created by ubuntudroid on 05/01/14.
 */
public class FitnessWeekListAdapter extends ArrayAdapter<FitnessWeek> {

    @Inject @ForApplication
    Context mApplicationContext;

    public FitnessWeekListAdapter(Context context, int resource, int textViewResourceId, List<FitnessWeek> objects) {
        super(context, resource, textViewResourceId, objects);

        FitnessTrackerApplication.getInstance().inject(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView subTextView = (TextView) view.findViewById(android.R.id.text2);
        if (position < getCount() - 1) {
            // we're not the last in the list
            FitnessWeek weekBefore = getItem(position + 1);
            FitnessWeek currentWeek = getItem(position);
            if (weekBefore != null && currentWeek.getWeight() > -1) {
                if (weekBefore.getWeight() > currentWeek.getWeight()) {
                    subTextView.setTextColor(mApplicationContext.getResources().getColor(android.R.color.holo_green_light));
                    subTextView.setText("v");
                } else if (weekBefore.getWeight() < currentWeek.getWeight()) {
                    subTextView.setTextColor(mApplicationContext.getResources().getColor(android.R.color.holo_red_light));
                    subTextView.setText("^");
                } else {
                    subTextView.setTextColor(mApplicationContext.getResources().getColor(android.R.color.holo_orange_light));
                    subTextView.setText("=");
                }
            } else {
                subTextView.setText("");
            }
        } else {
            subTextView.setText("");
        }
        return view;
    }
}
