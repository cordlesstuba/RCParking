package com.rcp.rcparking.customViews;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rcp.rcparking.R;

/**
 * Created by gcarves on 12/04/2017.
 */

public class KeyboardView extends FrameLayout implements View.OnClickListener {

    public EditText txt1,txt2,txt3,txt4;

    public KeyboardView(Context context) {
        super(context);
        init();
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.keyboard, this);
        initViews();
    }

    private void initViews() {

        txt1 = (EditText) findViewById(R.id.txt1);
        txt2 = (EditText) findViewById(R.id.txt2);
        txt3 = (EditText) findViewById(R.id.txt3);
        txt4 = (EditText) findViewById(R.id.txt4);

        txt1.setFocusable(false);
        txt2.setFocusable(false);
        txt3.setFocusable(false);
        txt4.setFocusable(false);

        $(R.id.t9_key_0).setOnClickListener(this);
        $(R.id.t9_key_1).setOnClickListener(this);
        $(R.id.t9_key_2).setOnClickListener(this);
        $(R.id.t9_key_3).setOnClickListener(this);
        $(R.id.t9_key_4).setOnClickListener(this);
        $(R.id.t9_key_5).setOnClickListener(this);
        $(R.id.t9_key_6).setOnClickListener(this);
        $(R.id.t9_key_7).setOnClickListener(this);
        $(R.id.t9_key_8).setOnClickListener(this);
        $(R.id.t9_key_9).setOnClickListener(this);
        $(R.id.t9_key_clear).setOnClickListener(this);
        $(R.id.t9_key_backspace).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // handle number button click
        if (v.getTag() != null && "number_button".equals(v.getTag())) {

            if (txt1.getText().length()==0){
                txt1.setText(((TextView) v).getText());
            }else {
                if (txt2.getText().length()==0){
                    txt2.setText(((TextView) v).getText());
                }else {
                    if (txt3.getText().length()==0){
                        txt3.setText(((TextView) v).getText());
                    }else {
                        if (txt4.getText().length()==0){
                            txt4.setText(((TextView) v).getText());
                        }
                    }
                }
            }


            return;
        }
        switch (v.getId()) {
            case R.id.t9_key_clear: { // handle clear button
                clear();
            }
            break;
            case R.id.t9_key_backspace: { // handle backspace button

                if (txt4.getText().length()!=0){
                    txt4.setText(null);
                }else {
                    if (txt3.getText().length()!=0){
                        txt3.setText(null);
                    }else {
                        if (txt2.getText().length()!=0){
                            txt2.setText(null);
                        }else {
                            if (txt1.getText().length()!=0){
                                txt1.setText(null);
                            }
                        }
                    }
                }






                // delete one character
                /*Editable editable = mPasswordField.getText();
                int charCount = editable.length();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                }*/
            }
            break;
        }
    }

    public void clear(){
        txt1.setText(null);
        txt2.setText(null);
        txt3.setText(null);
        txt4.setText(null);

    }

    public void changeColor(int color){
        txt1.setTextColor(color);
        txt2.setTextColor(color);
        txt3.setTextColor(color);
        txt4.setTextColor(color);
    }

    public String getInputText() {
        String password = txt1.getText().toString()+txt2.getText().toString()+txt3.getText().toString()+txt4.getText().toString();
        return password;
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }
}