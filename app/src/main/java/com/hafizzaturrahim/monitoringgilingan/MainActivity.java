package com.hafizzaturrahim.monitoringgilingan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hafizzaturrahim.monitoringgilingan.beranda.HomeFragment;
import com.hafizzaturrahim.monitoringgilingan.grafik.GraphFragment;
import com.hafizzaturrahim.monitoringgilingan.instruksi.DetailInstructionActivity;
import com.hafizzaturrahim.monitoringgilingan.instruksi.InstructionFragment;
import com.hafizzaturrahim.monitoringgilingan.laporan.ReportFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);

        TextView nav_nama = (TextView) hView.findViewById(R.id.nav_nama);

        nav_nama.setText("Halo, " + sessionManager.getUsername());

        int chosenMenu;
        Intent intent = getIntent();

        chosenMenu = intent.getIntExtra("menu", 0);
        //set beranda sebagai menu pertama
        navigationView.getMenu().getItem(chosenMenu).setChecked(true);
        if (chosenMenu == 0) {
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_beranda));
        }else if(chosenMenu == 3){
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_instruksi));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        selectMenu(id);
        return true;
    }

    private void selectMenu(int id) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_beranda) {
            fragment = new HomeFragment();
            title = "Beranda";
        } else if (id == R.id.nav_grafik) {
            fragment = new GraphFragment();
            title = "Grafik";
        } else if (id == R.id.nav_laporan) {
            fragment = new ReportFragment();
            title = "Laporan";
        } else if (id == R.id.nav_instruksi) {
            fragment = new InstructionFragment();
            title = "Instruksi";
        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    private void logout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Logout");
        alert.setMessage("Apakah anda akan melakukan logout?");
        alert.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });
        alert.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        sessionManager.logoutUser();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        dialog.dismiss();
                        startActivity(intent);
                        finish();
                    }
                });

        alert.show();

    }
}
