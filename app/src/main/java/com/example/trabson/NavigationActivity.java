package com.example.trabson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabson.database.dao.UserDao;
import com.example.trabson.model.User;
import com.example.trabson.model.dto.LoginDTO;
import com.example.trabson.service.user.UserServiceImp;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabson.databinding.ActivityNavigationBinding;

import java.util.Objects;

public class NavigationActivity extends AppCompatActivity {

    private ActivityNavigationBinding binding;

    private String email;

    private User currentUser;

    private TextView tvUserName, tvUserEmail;

    private View headerView;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("StockMe", MODE_PRIVATE);

        if(!prefs.getBoolean("loged", false)) {
            SharedPreferences.Editor edt = prefs.edit();
            edt.remove("email");
            edt.apply();
            callLoginPage();
        }

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.tbMain);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        Toolbar toolbar = binding.appBarNavigation.tbMain;
        headerView = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(openSelectedNavItem());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        changeFragment(new HomeFragment());
        getSupportActionBar().setTitle("Notícias");

    }

    private void setEmailAndNameOnDrawer() {

        prefs = getSharedPreferences("StockMe", MODE_PRIVATE);

        email = prefs.getString("email", null);

        currentUser = null;

        UserServiceImp userServiceImp = new UserServiceImp();

        userServiceImp.findByEmail(email, new UserServiceImp.UserCallback() {
            @Override
            public void onSuccess(User user) {
                currentUser = user;
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Erro de resposta da API: " + error, Toast.LENGTH_LONG).show();
            }
        });

        tvUserName = headerView.findViewById(R.id.tvUserName);
        tvUserEmail = headerView.findViewById(R.id.tvUserEmail);


        if(currentUser != null) {
            tvUserName.setText(currentUser.getName());
            tvUserEmail.setText(currentUser.getEmail());
        }

    }

    private NavigationView.OnNavigationItemSelectedListener openSelectedNavItem() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.nav_home) {

                    getSupportActionBar().setTitle("Notícias");

                    HomeFragment home = new HomeFragment();

                    Bundle args = new Bundle();

                    home.setArguments(args);

                    changeFragment(home);

                } else if(item.getItemId() == R.id.nav_stocks) {

                    getSupportActionBar().setTitle("Ações");

                    StockFragment stock = new StockFragment();

                    changeFragment(stock);

                } else if(item.getItemId() == R.id.nav_wallet) {

                    getSupportActionBar().setTitle("Carteira");

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

    private void callLoginPage() {

        Intent itn = new Intent(getApplicationContext(), LoginActivity.class);
        viewLoginPage.launch(itn);
    }

    ActivityResultLauncher<Intent> viewLoginPage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 200) {

                        setEmailAndNameOnDrawer();

                        showNews();

                    } else {
                        finish();
                    }
                }
            }
    );

    private void showNews() {

        changeFragment(new HomeFragment());

    }

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
}