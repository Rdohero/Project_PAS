package com.example.project_pas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.project_pas.HomeFragment;
import com.example.project_pas.SearchFragment;
import com.example.project_pas.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    BottomNavigationView bottomNavigationView;
    BroadcastReceiver broadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*        binding.rv_anime.setLayoutManager(new GridLayoutManager(this,2));*/

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //fragment
        HomeFragment homeFragment = new HomeFragment();
        SearchFragment searchFragment = new SearchFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_frame_layout, homeFragment, HomeFragment.class.getSimpleName())
                .commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                                                           @Override
                                                           public boolean onNavigationItemSelected( MenuItem item) {

                                                               // Melakukan transaksi fragment sesuai item yang dipilih
                                                               if (item.getItemId() == R.id.home) {

                                                                   getSupportFragmentManager()
                                                                           .beginTransaction()
                                                                           .replace(R.id.container_frame_layout, homeFragment)
                                                                           .commit();
                                                               } else if (item.getItemId() == R.id.search) {

                                                                   getSupportFragmentManager()
                                                                           .beginTransaction()
                                                                           .replace(R.id.container_frame_layout, searchFragment)
                                                                           .commit();
                                                               }else if (item.getItemId() == R.id.profile) {

                                                                   getSupportFragmentManager()
                                                                           .beginTransaction()
                                                                           .replace(R.id.container_frame_layout, profileFragment)
                                                                           .commit();
                                                               }


                                                               return true;
                                                           }
                                                       }



        );



    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }


}