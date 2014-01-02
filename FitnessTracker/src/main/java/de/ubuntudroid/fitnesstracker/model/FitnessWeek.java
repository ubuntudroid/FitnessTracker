package de.ubuntudroid.fitnesstracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ubuntudroid on 02/01/14.
 */

@DatabaseTable(tableName = "fitnessweeks")
public class FitnessWeek {
    @DatabaseField(id = true)
    private int mWeekNumber;
    @DatabaseField
    private float mWeight;
    @DatabaseField
    private float mMuscleFraction;
    @DatabaseField
    private float mWaterFraction;
    @DatabaseField
    private float mFatFraction;

    /**
     * no-arg constructor for ORMlite
     */
    public FitnessWeek() {

    }

    public FitnessWeek(int weekNumber, float weight, float muscleFraction, float waterFraction, float fatFraction) {
        this.mWeekNumber = weekNumber;
        this.mWeight = weight;
        this.mMuscleFraction = muscleFraction;
        this.mWaterFraction = waterFraction;
        this.mFatFraction = fatFraction;
    }

    public int getWeekNumber() {
        return mWeekNumber;
    }

    public void setWeekNumber(int mWeekNumber) {
        this.mWeekNumber = mWeekNumber;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float mWeight) {
        this.mWeight = mWeight;
    }

    public float getMuscleFraction() {
        return mMuscleFraction;
    }

    public void setMuscleFraction(float mMuscleFraction) {
        this.mMuscleFraction = mMuscleFraction;
    }

    public float getWaterFraction() {
        return mWaterFraction;
    }

    public void setWaterFraction(float mWaterFraction) {
        this.mWaterFraction = mWaterFraction;
    }

    public float getFatFraction() {
        return mFatFraction;
    }

    public void setFatFraction(float mFatFraction) {
        this.mFatFraction = mFatFraction;
    }
}
