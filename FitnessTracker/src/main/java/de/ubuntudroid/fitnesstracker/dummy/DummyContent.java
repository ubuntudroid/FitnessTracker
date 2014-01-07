package de.ubuntudroid.fitnesstracker.dummy;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

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
        addItem(new DummyItem(1, 127.8f, 31.3f, 49.2f, 27.4f));
        addItem(new DummyItem(2, 127.2f, 31.1f, 50.1f, 27.0f));
        addItem(new DummyItem(3, 126.6f, 32.9f, 48.7f, 26.8f));
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
