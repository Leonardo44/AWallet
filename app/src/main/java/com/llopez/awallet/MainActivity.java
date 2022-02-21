package com.llopez.awallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavController navController;
    private DrawerLayout drawerLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        navView = findViewById(R.id.nav_view);

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(this::onDestinationChanged);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.login_fragment, R.id.splashFragment, R.id.listBillsFragment, R.id.listEarningsFragment).build();
        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navController.removeOnDestinationChangedListener(this::onDestinationChanged);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Override
    public void onBackPressed() {
    }

    private void onDestinationChanged(@NonNull NavController controller,
                                      @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if (destination.getId() == R.id.login_fragment) {
            toolbar.setVisibility(View.GONE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (destination.getId() == R.id.splashFragment) {
            toolbar.setVisibility(View.GONE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

        if (destination.getId() == R.id.addBillFragment) {

        }

        if (destination.getId() == R.id.listBillsFragment) {
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_category_earnings:
            case R.id.menu_category_expenses:
            case R.id.menu_list_bills:
                navController.navigate(R.id.listBillsFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.menu_list_earnings:
                navController.navigate(R.id.listEarningsFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }
}