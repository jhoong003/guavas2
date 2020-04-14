package com.example.guavas.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.guavas.R;

import androidx.annotation.Nullable;

/**
 * This class is a custom View class, which is an edit text with plus and minus button.
 * Used in "Others" of the health summary page.
 */
public class PlusMinusEditText extends LinearLayout implements View.OnClickListener {

    private final String STATE_KEY = "State";
    private final String SUPERCLASS_KEY = "Super";

    private Button plusButton, minusButton;
    private EditText editText;

    private int value = 0;

    public PlusMinusEditText(Context context) {
        super(context);
        initView(context);
    }

    public PlusMinusEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public PlusMinusEditText(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initView(context);
    }

    /**
     * Inflates the layout resource file.
     *
     * @param context the context.
     */
    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.plus_minus_edit_text, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupButtons();
        editText = this.findViewById(R.id.edit_text_data);
        setText(value);
    }

    /**
     * Make the plus and minus button respond to click.
     */
    private void setupButtons() {
        plusButton = this.findViewById(R.id.button_plus);
        plusButton.setOnClickListener(this);

        minusButton = this.findViewById(R.id.button_minus);
        minusButton.setOnClickListener(this);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(STATE_KEY, value);
        bundle.putParcelable(SUPERCLASS_KEY, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(SUPERCLASS_KEY));
            value = bundle.getInt(STATE_KEY, 0);
        } else super.onRestoreInstanceState(state);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_plus) {
            increment();
        } else if (v.getId() == R.id.button_minus) {
            decrement();
        }
    }

    /**
     * Increments the number in the text.
     */
    private void increment() {
        setText(++value);
    }

    /**
     * Decrements the number in the text. The value will not go below zero.
     */
    private void decrement() {
        if (value > 0) setText(--value);
    }

    /**
     * Sets the text value.
     *
     * @param n the value to set.
     */
    private void setText(int n) {
        editText.setText(Integer.toString(n));
    }

    /**
     * Returns the current value of the text.
     *
     * @return the current value of the text.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the current value of the text.
     *
     * @param value the value to set.
     */
    public void setValue(int value) {
        this.value = value;
        setText(value);
    }
}
