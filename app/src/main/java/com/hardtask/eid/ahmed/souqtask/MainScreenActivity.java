package com.hardtask.eid.ahmed.souqtask;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hardtask.eid.ahmed.souqtask.view.fragments.CategoryFragment;

public class MainScreenActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //get language from sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        language = sharedPreferences.getString(AppConfig.ShardPreference_Language_Key, "en");

        CategoryFragment fragment = new CategoryFragment();
        FragmentManager manager = getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if (language.equals("ar")) {
            fragmentTransaction.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_right, R.anim.in_from_right, R.anim.out_to_left);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        }
        fragmentTransaction.replace(R.id.container_layout,fragment).commit();

    }
}
