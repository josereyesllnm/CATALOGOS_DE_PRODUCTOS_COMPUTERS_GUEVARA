package com.ugb.COMPUTERS_GUEVARA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.home:
                    {
                        Toast.makeText(MainActivity2.this,"COMPUTADORAS select",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), COMPUTADORAS.class));
                        break;
                    }
                    case R.id.escritorio:
                    {
                        Toast.makeText(MainActivity2.this,"ESCRITORIO selected",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), ESCRITORIO.class));
                        break;
                    }
                    case R.id.laptops:
                    {
                        Toast.makeText(MainActivity2.this,"LAPTOPS selected", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), LAPTOPS.class));
                        break;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}