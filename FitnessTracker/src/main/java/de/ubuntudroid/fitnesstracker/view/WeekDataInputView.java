package de.ubuntudroid.fitnesstracker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.ubuntudroid.fitnesstracker.R;

/**
 * Created by ubuntudroid on 03/01/14.
 */
public class WeekDataInputView extends RelativeLayout {

    private TextView label;
    private EditText input;

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
        label = (TextView) layout.findViewById(R.id.label);
        input = (EditText) layout.findViewById(R.id.input);

        if (attrs != null) {
            TypedArray attributeArray = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.WeekDataInputView,
                    0, 0);

            try {
                label.setText(attributeArray.getString(R.styleable.WeekDataInputView_labelTitle));
                input.setHint(attributeArray.getString(R.styleable.WeekDataInputView_hint));
            } finally {
                attributeArray.recycle();
            }
        } else {
            // default layout
            label.setText("Value");
            input.setHint("Hint");
        }
    }

    public void setLabel(String text) {
        if (label != null) {
            label.setText(text);
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
}
