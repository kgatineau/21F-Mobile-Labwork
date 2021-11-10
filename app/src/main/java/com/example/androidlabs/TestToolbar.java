package com.example.androidlabs;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        //This gets the toolbar from the layout:
        Toolbar toolBar_L08 = findViewById(R.id.toolbar_L08);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(toolBar_L08);

        DrawerLayout drawer = findViewById(R.id.drawerLayout_L08);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolBar_L08, R.string.open_L08, R.string.close_L08);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView_L08);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lab8_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.message:
                message = String.valueOf(getText(R.string.menu_text1_L08));
                break;
            case R.id.weather:
                message = String.valueOf(getText(R.string.menu_text2_L08));
                break;
            case R.id.login:
                message = String.valueOf(getText(R.string.menu_text3_L08));
                break;
            case R.id.overflow:
                message = String.valueOf(getText(R.string.menu_text4_L08));
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.message:
                Intent toChat = new Intent(TestToolbar.this, ChatRoomActivity.class);
                TestToolbar.this.startActivity(toChat);
                break;
            case R.id.weather:
                Intent toWeather = new Intent(TestToolbar.this, WeatherForecast.class);
                TestToolbar.this.startActivity(toWeather);
                break;
            case R.id.login:
                setResult(500);
                this.finish();
                break;
            case R.id.overflow:

        }

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout_L08);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

}
