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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavController navController;
    private DrawerLayout drawerLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private NavigationView navView;
    private TextView userName;
    private TextView userEmail;
    private ShapeableImageView userImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);
        userImageView = navView.getHeaderView(0).findViewById(R.id.nav_header_user_image);
        userName = navView.getHeaderView(0).findViewById(R.id.nav_header_name);
        userEmail = navView.getHeaderView(0).findViewById(R.id.nav_header_email);

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(this::onDestinationChanged);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.loginFragment, R.id.splashFragment, R.id.homeFragment, R.id.listBillsFragment, R.id.listEarningsFragment, R.id.billCategoryListFragment, R.id.earningCategoryListFragment).build();
        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        loadUserData();
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

    public void loadUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());

            if(user.getPhotoUrl() != null){
                Picasso.get().load(user.getPhotoUrl()).into(userImageView);
            }else {
                userImageView.setImageResource(R.drawable.ic_baseline_person_24);
            }
        }
    }

    private void onDestinationChanged(@NonNull NavController controller,
                                      @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if (destination.getId() == R.id.loginFragment) {
            toolbar.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (destination.getId() == R.id.splashFragment) {
            toolbar.setVisibility(View.GONE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

        if (destination.getId() == R.id.addBillFragment || destination.getId() == R.id.addEarningFragment || destination.getId() == R.id.createBillCategoryFragment || destination.getId() == R.id.createEarningCategoryFragment) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        if (destination.getId() == R.id.listBillsFragment) {
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_category_earnings:
                navController.navigate(R.id.earningCategoryListFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.menu_category_expenses:
                navController.navigate(R.id.billCategoryListFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.menu_list_bills:
                navController.navigate(R.id.listBillsFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.menu_list_earnings:
                navController.navigate(R.id.listEarningsFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.menu_home:
                navController.navigate(R.id.homeFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.login_fragment:
                toolbar.setVisibility(View.GONE);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                return true;
            case R.id.logout_btn:
                SharedPreferences preferences = getSharedPreferences("credential", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLogged", false);
                editor.apply();
                navController.navigate(R.id.splashFragment);

                return true;
        }
        return false;
    }
}