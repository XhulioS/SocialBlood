package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Profile");


        BottomNavigationView navigationView=findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()

        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        actionBar.setTitle("Home");
                       HomeFragment fragment1=new HomeFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content,fragment1,"");
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_profile:
                        actionBar.setTitle("Profile");
                        ProfileFragment fragment2=new ProfileFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content,fragment2,"");
                        fragmentTransaction.commit();
                            return true;
                    case R.id.nav_blood:
                       actionBar.setTitle("Blood");
                        BloodFragment fragment3=new BloodFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content,fragment3,"");
                        fragmentTransaction.commit();
                        return true;

                }

                return false;
            }
        });

        actionBar.setTitle("Home");
        HomeFragment fragment1=new HomeFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        firebaseAuth=FirebaseAuth.getInstance();
        switch (item.getItemId()){
        case R.id.logoutmenu:{
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(SecondActivity.this, MainActivity.class));
        }}


        return super.onOptionsItemSelected(item);
    }
}
