package de.ubuntudroid.fitnesstracker.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.ubuntudroid.fitnesstracker.R;

/**
 * Created by ubuntudroid on 03/01/14.
 */
public class WeekDataInputView extends RelativeLayout {

    protected TextView desc;
    protected EditText input;
    protected TextView unit;

    // TODO allow for setting of text validator

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

    protected void init(Context context, AttributeSet attrs) {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.week_data_input, null);
        addView(layout);
        desc = (TextView) layout.findViewById(R.id.label);
        input = (EditText) layout.findViewById(R.id.input);
        unit = (TextView) layout.findViewById(R.id.label_unit);

        if (attrs != null && context != null && context.getTheme() != null) {
            TypedArray attributeArray = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.WeekDataInputView,
                    0, 0);

            if (attributeArray != null) {
                try {
                    String descriptionText = attributeArray.getString(R.styleable.WeekDataInputView_desc);
                    desc.setText(descriptionText);

                    // for calabash
                    input.setContentDescription(descriptionText);

                    input.setHint(attributeArray.getString(R.styleable.WeekDataInputView_hint));
                    unit.setText(attributeArray.getString(R.styleable.WeekDataInputView_unit));
                } finally {
                    attributeArray.recycle();
                }
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

        if (input != null) {
            // for calabash
            input.setTag(text);
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

    public String getText() {
        if (input != null && input.getText() != null) {
            return input.getText().toString().trim();
        } else {
            return "";
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

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        // we have to store/restore the input value ourselves as there could be multiple WeekDataInputViews
        // in one Activity causing all of them having the same values after an orientation change if
        // we let Android handle this itself. That's also the reason for setting saveEnabled to false
        // in our XML.
        bundle.putString("inputValue", input.getText().toString());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            input.setText(bundle.getString("inputValue"));
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}
