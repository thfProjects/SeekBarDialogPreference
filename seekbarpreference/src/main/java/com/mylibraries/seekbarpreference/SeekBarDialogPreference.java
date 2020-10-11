package com.mylibraries.seekbarpreference;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by marko on 3/19/2017.
 */

public class SeekBarDialogPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener{

    //text above seekbar
    protected String messageText;

    protected int min;
    protected int max;

    protected int defaultvalue;

    private TextView currentValueTextView;
    private TextView messageTextView;
    private SeekBar preferenceSeekBar;

    //increment value for the arrowkeys
    protected int increment = 1;

    protected final SharedPreferences preferences;

    protected int currentValue;

    public SeekBarDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SeekBarDialogPreference, 0, 0);

        try {
            min = a.getInteger(R.styleable.SeekBarDialogPreference_min, 0);
            max = a.getInteger(R.styleable.SeekBarDialogPreference_max, 5);
            defaultvalue = a.getInteger(R.styleable.SeekBarDialogPreference_defaultvalue, min);
            messageText = a.getString(R.styleable.SeekBarDialogPreference_message);
        }finally {
            a.recycle();
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setDialogLayoutResource(R.layout.seekbar_preference_dialog);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SeekBarDialogPreference(Context context) {
        super(context);

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setDialogLayoutResource(R.layout.seekbar_preference_dialog);
    }

    public SeekBarDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SeekBarDialogPreference, 0, 0);

        try {
            min = a.getInteger(R.styleable.SeekBarDialogPreference_min, 0);
            max = a.getInteger(R.styleable.SeekBarDialogPreference_max, 5);
            defaultvalue = a.getInteger(R.styleable.SeekBarDialogPreference_defaultvalue, min);
            messageText = a.getString(R.styleable.SeekBarDialogPreference_message);
        }finally {
            a.recycle();
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setDialogLayoutResource(R.layout.seekbar_preference_dialog);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SeekBarDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SeekBarDialogPreference, 0, 0);

        try {
            min = a.getInteger(R.styleable.SeekBarDialogPreference_min, 0);
            max = a.getInteger(R.styleable.SeekBarDialogPreference_max, 5);
            defaultvalue = a.getInteger(R.styleable.SeekBarDialogPreference_defaultvalue, min);
            messageText = a.getString(R.styleable.SeekBarDialogPreference_message);
        }finally {
            a.recycle();
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setDialogLayoutResource(R.layout.seekbar_preference_dialog);
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            int newValue = preferenceSeekBar.getProgress();
            if (currentValue != newValue) {
                currentValue = newValue;
                storeValue();
            }
        }
    }

    private void storeValue()
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getKey(), currentValue + min);
        editor.commit();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (currentValueTextView != null) {
            currentValueTextView.setText(getCurrentValueText(progress));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public View onCreateDialogView(){
        View view = super.onCreateDialogView();

        currentValue = preferences.getInt(getKey(), defaultvalue) - min;

        messageTextView = (TextView) view.findViewById(R.id.seekbar_preference_message);
        preferenceSeekBar = (SeekBar) view.findViewById(R.id.seekbar_preference_seekbar);
        currentValueTextView = (TextView) view.findViewById(R.id.seekbar_preference_current_value);

        if (messageText == null) {
            messageTextView.setVisibility(View.GONE);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText(messageText);
        }

        preferenceSeekBar.setOnSeekBarChangeListener(this);
        preferenceSeekBar.setMax(max - min);
        preferenceSeekBar.setProgress(Math.min(currentValue, max - min));
        preferenceSeekBar.setKeyProgressIncrement(increment);

        currentValueTextView.setText(getCurrentValueText(preferenceSeekBar
                .getProgress()));

        return view;
    }

    private String getCurrentValueText(int progress){
        return Integer.toString(progress + min);
    }

    public void setMessageText(String text){
        messageText = text;
    }

    public String setMessageText(){
        return messageText;
    }

    public void setMin(int min){
        this.min = min;
    }

    public int getMin(){
        return min;
    }

    public void setMax(int max){
        this.max = max;
    }

    public int getMax(){
        return max;
    }
}
