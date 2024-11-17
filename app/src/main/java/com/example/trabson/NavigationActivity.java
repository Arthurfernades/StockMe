package com.example.trabson;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabson.database.dao.UserDao;
import com.example.trabson.model.User;
import com.example.trabson.ui.home.HomeFragment;
import com.example.trabson.ui.stock.StockFragment;
import com.example.trabson.ui.wallet.WalletFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabson.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ActivityNavigationBinding binding;

    private TextView tvUserName, tvUserEmail;

    private UserDao uDao;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindingKeys();

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.toolbar);
        binding.appBarNavigation.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        Toolbar toolbar = binding.appBarNavigation.toolbar;

        navigationView.setNavigationItemSelectedListener(openSelectedNavItem());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_stocks, R.id.nav_wallet)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/

        SharedPreferences prefs = getSharedPreferences("StockMe", MODE_PRIVATE);

        if(!prefs.getBoolean("loged", false)) {
            callLoginPage();
        }

        uDao = new UserDao(getApplicationContext());

        user =  uDao.findByEmail(prefs.getString("email", null));

//        tvUserEmail.setText(user.getEmail());
//        tvUserName.setText(user.getName());

    }

    private NavigationView.OnNavigationItemSelectedListener openSelectedNavItem() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.nav_home) {

                    HomeFragment home = new HomeFragment();

                    Bundle args = new Bundle();
                    args.putString("nome", user.getName());

                    home.setArguments(args);

                    changeFragment(home);

                } else if(item.getItemId() == R.id.nav_stocks) {

                    StockFragment stock = new StockFragment();

                    changeFragment(stock);

                } else if(item.getItemId() == R.id.nav_wallet) {

                    WalletFragment wallet = new WalletFragment();

                    changeFragment(wallet);

                }

                binding.drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        };
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.nav_host_fragment_content_navigation, fragment);
        transaction.commit();
    }

    private void bindingKeys() {
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
    }

    private void callLoginPage() {

        Intent itn = new Intent(getApplicationContext(), LoginActivity.class);
        viewLoginPage.launch(itn);
    }

    ActivityResultLauncher<Intent> viewLoginPage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() != 200) {
                        finish();
                    }
                }
            }
    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.mnExit) {
            SharedPreferences prefs = getSharedPreferences("StockMe", MODE_PRIVATE);
            SharedPreferences.Editor edt = prefs.edit();
            edt.remove("email");
            edt.remove("loged");
            edt.apply();

            callLoginPage();
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}