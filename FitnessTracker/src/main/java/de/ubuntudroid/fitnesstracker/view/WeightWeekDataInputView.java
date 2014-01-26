package de.ubuntudroid.fitnesstracker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import java.text.DecimalFormat;

import de.ubuntudroid.fitnesstracker.R;
import de.ubuntudroid.fitnesstracker.view.helper.RightDrawableOnTouchListener;

/**
 * Created by ubuntudroid on 26/01/14.
 */
public class WeightWeekDataInputView extends WeekDataInputView {

    //region constants
    public static final float LBS2KG_CONVERSION_FACTOR = 2.2046f;
    private final int MAXIMUM_FRACTION_DIGITS = 2;
    //endregion

    //region private fields
    private DecimalFormat mDecimalFormat;
    //endregion

    //region constructors
    public WeightWeekDataInputView(Context context) {
        super(context);
    }

    public WeightWeekDataInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeightWeekDataInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //endregion

    //region overrides
    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);

        input.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_info), null);
        input.setOnTouchListener(new RightDrawableOnTouchListener(input) {
            @Override
            public boolean onDrawableTouch(MotionEvent event) {
                onClickInfo();
                return true;
            }
        });

        mDecimalFormat = new DecimalFormat();
        mDecimalFormat.setMaximumFractionDigits(MAXIMUM_FRACTION_DIGITS);
    }
    //endregion

    //region private methods
    private void onClickInfo() {
        if (!input.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), convertLbsToKg(Float.parseFloat(input.getText().toString())) + " kg", Toast.LENGTH_SHORT).show();
        }
    }

    private String convertLbsToKg ( float lbs ) {
        return mDecimalFormat.format(lbs / LBS2KG_CONVERSION_FACTOR);
    }
    //endregion
}
