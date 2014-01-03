package de.ubuntudroid.fitnesstracker.dummy;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static SparseArray<DummyItem> ITEM_MAP = new SparseArray<DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem(1, 127.8f, 0.313f, 0.492f, 0.274f));
        addItem(new DummyItem(2, 127.2f, 0.311f, 0.501f, 0.270f));
        addItem(new DummyItem(3, 126.6f, 0.329f, 0.487f, 0.268f));
        addItem(new DummyItem(4, -1, -1, -1, -1));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public int id;
        public float weight;
        public float muscleFraction;
        public float waterFraction;
        public float fatFraction;

        public DummyItem(int id, float weight, float muscleFraction, float waterFraction, float fatFraction) {
            this.id = id;
            this.weight = weight;
            this.muscleFraction = muscleFraction;
            this.waterFraction = waterFraction;
            this.fatFraction = fatFraction;
        }

        @Override
        public String toString() {
            return "Week " + id;
        }
    }
}
