package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        Button button = (Button)findViewById(R.id.button_L02);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getResources().
                        getString(R.string.toast_text_L02), Toast.LENGTH_LONG).show();
            }
        });

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkbox_L02);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {
                String x;
                if (b){
                    x = " on";
                } else {
                    x = " off";
                }
               Snackbar.make(checkBox, getResources()
                       .getString(R.string.snackbar_checkbox_L02) + x, Snackbar.LENGTH_LONG)
                       .setAction("Undo", click -> cb.setChecked(!b)).show();

            }


        }
        );
        Switch switchLab02 = (Switch)findViewById(R.id.switch_L02);
        switchLab02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {
                String x;
                if (b){
                    x = " on";
                } else {
                    x = " off";
                }
                Snackbar.make(switchLab02, getResources().getString(R.string.snackbar_switch_L02) + x,
                        Snackbar.LENGTH_LONG).setAction("Undo", click -> cb.setChecked((!b))).show();
            }
        });


    }

}