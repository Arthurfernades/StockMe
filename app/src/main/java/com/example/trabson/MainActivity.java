package com.example.trabson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabson.database.dao.UserDao;
import com.example.trabson.model.User;

public class MainActivity extends AppCompatActivity {

    private Toolbar tbMain;

    private TextView tvUserName;

    private UserDao uDao;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        uDao = new UserDao(getApplicationContext());

        callLoginPage();

        binding();

        tbMain.setTitle("Olá, " + user.getName() + "!");

        setSupportActionBar(tbMain);
    }

    private void callLoginPage() {

        SharedPreferences prefs = getSharedPreferences("StockMe", MODE_PRIVATE);

        if(!prefs.getBoolean("loged", false)) {
            Intent itn = new Intent(getApplicationContext(), LoginActivity.class);
            viewLoginPage.launch(itn);
        }
        String email = prefs.getString("email", null);
        user = uDao.findByEmail(email);
    }

    ActivityResultLauncher<Intent> viewLoginPage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 0) {
                        finish();
                    }
                }
            }
    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mnStocks) {
            Toast.makeText(getApplicationContext(), "Clicou em ações!", Toast.LENGTH_LONG).show();
        } else if (id == R.id.mnWallet) {
            Toast.makeText(getApplicationContext(), "Clicou em carteira!", Toast.LENGTH_LONG).show();
        } else if (id == R.id.mnExit) {
            SharedPreferences prefs = getSharedPreferences("StockMe", MODE_PRIVATE);
            SharedPreferences.Editor edt = prefs.edit();
            edt.remove("email");
            edt.remove("loged");
            edt.commit();
            callLoginPage();
        }

        return super.onOptionsItemSelected(item);
    }

    private void binding() {
        tbMain = findViewById(R.id.tbMain);
    }
}