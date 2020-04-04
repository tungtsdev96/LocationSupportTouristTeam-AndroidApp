package com.svmc.android.locationsupportteam.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;

/**
 *  Create by TUNGTS on 4/17/2019
 */

public class CustomInputEditText extends LinearLayout {

    private TextView tv;
    private EditText edt;

    private String title;
    private String hintText;
    private String text;

    private boolean isEnable = true;

    public CustomInputEditText(Context context) {
        super(context);
        innitView();
    }

    public CustomInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomInputEditText);
        this.hintText = typedArray.getString(R.styleable.CustomInputEditText_hint_text);
        this.text =typedArray.getString(R.styleable.CustomInputEditText_text);
        this.title =typedArray.getString(R.styleable.CustomInputEditText_title);
        typedArray.recycle();
        innitView();
    }

    public CustomInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        innitView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomInputEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        innitView();
    }

    private void innitView() {
        View view = inflate(getContext(), R.layout.custom_edittext, null);
        addView(view, new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tv = view.findViewById(R.id.tv_title);
        edt = view.findViewById(R.id.edt_input);

        tv.setText(this.title);
        edt.setHint(this.hintText);
        edt.setText(this.text);

        edt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tv.setTextColor(Color.BLACK);
                    edt.setBackgroundResource(R.drawable.bg_with_bottom_line_green);
                    return;
                }
                tv.setTextColor(getResources().getColor(R.color.text_color_default));
                edt.setBackgroundResource(R.drawable.bg_with_bottom_line_black);
            }
        });

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setText(String s) {
        edt.setText(s);
    }

    public String getText() {
        return edt.getText().toString();
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
        if (edt != null) edt.setEnabled(isEnable);
    }
}
