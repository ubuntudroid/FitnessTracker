package de.ubuntudroid.fitnesstracker.view.helper;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import de.ubuntudroid.fitnesstracker.FitnessTrackerApplication;

/**
 * Created by ubuntudroid on 26/01/14.
 */
public abstract class RightDrawableOnTouchListener implements View.OnTouchListener {
    Drawable drawable;
    private boolean isConsumingTouch = false;

    /**
     * @param view
     */
    public RightDrawableOnTouchListener(TextView view) {
        super();
        final Drawable[] drawables = view.getCompoundDrawables();
        if (drawables != null && drawables.length == 4)
            this.drawable = drawables[2];
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && drawable != null) {
            final int x = (int) event.getRawX();
            final Rect bounds = drawable.getBounds();
            if (x >= (v.getRight() - v.getPaddingRight() - bounds.width()) && x <= v.getRight()) {
                isConsumingTouch = true;
                return onDrawableTouch(event);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP && isConsumingTouch) {
            isConsumingTouch = false;
            return true;
        } else if (isConsumingTouch) {
            return true;
        }
        return false;
    }

    public abstract boolean onDrawableTouch(final MotionEvent event);

}
