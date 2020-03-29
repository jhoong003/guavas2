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

public class PlusMinusEditText extends LinearLayout implements View.OnClickListener{

    private final String STATE_KEY = "State";
    private final String SUPERCLASS_KEY = "Super";

    private Button plusButton, minusButton;
    private EditText editText;

    private int value = 0;

    public PlusMinusEditText(Context context){
        super(context);
        initView(context);
    }

    public PlusMinusEditText(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        initView(context);
    }

    public PlusMinusEditText(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.plus_minus_edit_text, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupButtons();
        editText = this.findViewById(R.id.edit_text_data);
        setText(value);
    }

    private void setupButtons(){
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
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(SUPERCLASS_KEY));
            value = bundle.getInt(STATE_KEY, 0);
        }else super.onRestoreInstanceState(state);
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
        if (v.getId() == R.id.button_plus){
            increment();
        }else if (v.getId() == R.id.button_minus){
            decrement();
        }
    }

    private void increment(){
        setText(++value);
    }

    private void decrement(){
        if (value > 0) setText(--value);
    }

    private void setText(int n){
        editText.setText(Integer.toString(n));
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setText(value);
    }
}
