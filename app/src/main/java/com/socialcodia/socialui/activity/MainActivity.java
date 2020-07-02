package com.socialcodia.socialui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.socialcodia.socialui.R;
import com.socialcodia.socialui.fragment.AddFeedFragment;
import com.socialcodia.socialui.fragment.HomeFragment;
import com.socialcodia.socialui.fragment.ProfileFragment;
import com.socialcodia.socialui.fragment.SettingsFragment;
import com.socialcodia.socialui.fragment.UsersFragment;
import com.socialcodia.socialui.storage.SharedPrefHandler;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ActionBar actionBar;
    SharedPrefHandler sharedPrefHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        navigationView = findViewById(R.id.bottomNavigation);
        actionBar = getSupportActionBar();
        sharedPrefHandler = SharedPrefHandler.getInstance(getApplicationContext());
        Fragment fragment = new HomeFragment();
        setFragment(fragment);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment fragment = new HomeFragment();
                switch (id)
                {
                    case R.id.miHome:
                        actionBar.setTitle("Home");
                        fragment = new HomeFragment();
                        break;
                    case R.id.miUsers:
                        actionBar.setTitle("Users");
                        fragment = new UsersFragment();
                        break;
                    case R.id.miSetting:
                        actionBar.setTitle("Settings");
                        fragment = new SettingsFragment();
                                break;
                    case R.id.miProfile:
                        actionBar.setTitle("Profile");
                        fragment = new ProfileFragment();
                        break;
                }
                setFragment(fragment);
                return  true;
            }
        });
        isLoggedIn();
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.miAbout:
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.miLogout:
                doLogout();
                break;
            case R.id.miAddFeed:
                Fragment fragment = new AddFeedFragment();
                setFragment(fragment);
                actionBar.setTitle("Add Feed");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void isLoggedIn()
    {
        if (!SharedPrefHandler.getInstance(getApplicationContext()).isLoggedIn())
        {
            doLogout();
        }
    }

    private void doLogout()
    {
        Toast.makeText(this, "Logout Has Been Done", Toast.LENGTH_SHORT).show();
        sharedPrefHandler.doLogout();
        sendToLogin();
    }

    private void sendToLogin()
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
