package com.mylibraries.seekbardialogpreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by marko on 3/19/2017.
 */

public class MainActivity extends AppCompatActivity {

    Button settings;
    Intent settingsIntent;

    TextView display;

    SharedPreferences sharedPreferences;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(settingsIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);

        settings = (Button)findViewById(R.id.settings);
        settings.setOnClickListener(onClickListener);

        display = (TextView)findViewById(R.id.display);
    }

    @Override
    protected void onResume(){
        super.onResume();
        display.setText(String.valueOf(sharedPreferences.getInt("value", 0)));
    }
}
