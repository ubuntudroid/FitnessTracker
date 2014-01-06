package de.ubuntudroid.fitnesstracker.controller;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;
import de.ubuntudroid.fitnesstracker.R;
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
        TextView mainTextView = (TextView) view.findViewById(R.id.week_name);
        TextView subTextView = (TextView) view.findViewById(R.id.week_mini_indicators);
        if (position < getCount() - 1) {
            // we're not the last in the list
            FitnessWeek weekBefore = getItem(position + 1);
            FitnessWeek currentWeek = getItem(position);
            if (weekBefore != null && currentWeek.getWeight() > -1) {
                if (weekBefore.getWeight() > currentWeek.getWeight()) {
                    mainTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_green_check , 0);
                } else if (weekBefore.getWeight() < currentWeek.getWeight()) {
                    mainTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_red_exclamation , 0);
                } else {
                    mainTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_yellow_check , 0);
                }

                SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                stringBuilder.append("Fat ");
                if (weekBefore.getFatFraction() > currentWeek.getFatFraction()) {
                    stringBuilder.append(Html.fromHtml("&darr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_green_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                } else if (weekBefore.getFatFraction() < currentWeek.getFatFraction()) {
                    stringBuilder.append(Html.fromHtml("&uarr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_red_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                } else {
                    stringBuilder.append(Html.fromHtml("&rarr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_orange_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                stringBuilder.append("  Muscle ");
                if (weekBefore.getMuscleFraction() > currentWeek.getMuscleFraction()) {
                    stringBuilder.append(Html.fromHtml("&darr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_red_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                } else if (weekBefore.getMuscleFraction() < currentWeek.getMuscleFraction()) {
                    stringBuilder.append(Html.fromHtml("&uarr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_green_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                } else {
                    stringBuilder.append(Html.fromHtml("&rarr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_orange_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                stringBuilder.append("  Water ");
                if (weekBefore.getWaterFraction() > currentWeek.getWaterFraction()) {
                    stringBuilder.append(Html.fromHtml("&darr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_red_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                } else if (weekBefore.getWaterFraction() < currentWeek.getWaterFraction()) {
                    stringBuilder.append(Html.fromHtml("&uarr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_green_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                } else {
                    stringBuilder.append(Html.fromHtml("&rarr;"));
                    stringBuilder.setSpan(new ForegroundColorSpan(mApplicationContext.getResources().getColor(android.R.color.holo_orange_light)), stringBuilder.length() - 1, stringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                subTextView.setText(stringBuilder);

            } else {
                subTextView.setText("");
                mainTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        } else {
            subTextView.setText("");
            mainTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        return view;
    }
}
