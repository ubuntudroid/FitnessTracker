package de.ubuntudroid.fitnesstracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ubuntudroid on 02/01/14.
 */

@DatabaseTable(tableName = "fitnessweeks")
public class FitnessWeek {
    @DatabaseField(id = true)
    private int weekNumber;
    @DatabaseField
    private float weight = -1;
    @DatabaseField
    private float muscleFraction = -1;
    @DatabaseField
    private float waterFraction = -1;
    @DatabaseField
    private float fatFraction = -1;

    /**
     * no-arg constructor for ORMlite
     */
    public FitnessWeek() {

    }

    public FitnessWeek(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public FitnessWeek(int weekNumber, float weight, float muscleFraction, float waterFraction, float fatFraction) {
        this.weekNumber = weekNumber;
        this.weight = weight;
        this.muscleFraction = muscleFraction;
        this.waterFraction = waterFraction;
        this.fatFraction = fatFraction;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int mWeekNumber) {
        this.weekNumber = mWeekNumber;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float mWeight) {
        this.weight = mWeight;
    }

    public float getMuscleFraction() {
        return muscleFraction;
    }

    public void setMuscleFraction(float mMuscleFraction) {
        this.muscleFraction = mMuscleFraction;
    }

    public float getWaterFraction() {
        return waterFraction;
    }

    public void setWaterFraction(float mWaterFraction) {
        this.waterFraction = mWaterFraction;
    }

    public float getFatFraction() {
        return fatFraction;
    }

    public void setFatFraction(float mFatFraction) {
        this.fatFraction = mFatFraction;
    }

    public boolean isDummy () {
        return weight == -1 && muscleFraction == -1 && waterFraction == -1 && fatFraction == -1;
    }

    @Override
    public String toString() {
        return "Week " + weekNumber;
    }
}
