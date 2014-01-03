package de.ubuntudroid.fitnesstracker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.ubuntudroid.fitnesstracker.R;

/**
 * Created by ubuntudroid on 03/01/14.
 */
public class WeekDataInputView extends RelativeLayout {

    private TextView desc;
    private EditText input;
    private TextView unit;

    public WeekDataInputView(Context context) {
        super(context);
        init(context, null);
    }

    public WeekDataInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WeekDataInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.week_data_input, null);
        addView(layout);
        desc = (TextView) layout.findViewById(R.id.label);
        input = (EditText) layout.findViewById(R.id.input);
        unit = (TextView) layout.findViewById(R.id.label_unit);

        if (attrs != null) {
            TypedArray attributeArray = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.WeekDataInputView,
                    0, 0);

            try {
                desc.setText(attributeArray.getString(R.styleable.WeekDataInputView_desc));
                input.setHint(attributeArray.getString(R.styleable.WeekDataInputView_hint));
                unit.setText(attributeArray.getString(R.styleable.WeekDataInputView_unit));
            } finally {
                attributeArray.recycle();
            }
        } else {
            // default layout
            desc.setText("Value");
            input.setHint("Hint");
        }
    }

    public void setDesc(String text) {
        if (desc != null) {
            desc.setText(text);
            invalidate();
            requestLayout();
        }
    }

    public void setHint(String text) {
        if (input != null) {
            input.setHint(text);
            invalidate();
            requestLayout();
        }
    }

    public void setText(String text) {
        if (input != null) {
            input.setText(text);
            invalidate();
            requestLayout();
        }
    }

    public void setEditable(boolean editable) {
        if (input != null) {
            input.setEnabled(editable);
            invalidate();
            requestLayout();
        }
    }

    public TextView getUnit() {
        return unit;
    }

    public void setUnit(TextView unit) {
        this.unit = unit;
    }
}
