package com.example.trabson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabson.config.RetrofitConfig;
import com.example.trabson.database.dao.UserDao;
import com.example.trabson.model.dto.UserLoginDTO;
import com.example.trabson.service.IUserService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private CheckBox cbRemember;

    private Button btnLogin;

    private IUserService userService;

    private TextInputLayout cpEmail, cpPassword;

    private TextView tvNewUser;

    private boolean loginVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding();

        Retrofit retrofit = new RetrofitConfig().getUserRetrofit();

        userService = retrofit.create(IUserService.class);

        btnLogin.setOnClickListener(clickLogin());

        tvNewUser.setOnClickListener(clickNewUser());

    }

    private View.OnClickListener clickNewUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(getApplicationContext(), RegisterActivity.class);

                viewUserRegister.launch(itn);
            }
        };
    }

    ActivityResultLauncher<Intent> viewUserRegister = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 200) {
                        String email = (String) result.getData().getExtras().getSerializable("email");
                        cpEmail.getEditText().setText(email);
                    }
                }
            }
    );

    private View.OnClickListener clickLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loginVerified = false;
                    UserLoginDTO userLoginDTO = new UserLoginDTO(cpEmail.getEditText().getText().toString(),
                            cpPassword.getEditText().getText().toString());

                    Call<String> resp = userService.login(userLoginDTO);

                    resp.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.message().equals("User exists")) {
                                loginVerified = true;
                            } else {
                                Log.e("API", "Erro: " + response.errorBody());
                                Toast.makeText(getApplicationContext(), "E-mail ou senha incorreto", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable throwable) {
                            Log.e("API", "Falha: " + throwable.getMessage());
                        }
                    });

                    if(loginVerified) {

                        SharedPreferences prefs = getSharedPreferences("StockMe", MODE_PRIVATE);
                        SharedPreferences.Editor edt = prefs.edit();
                        edt.putBoolean("loged", cbRemember.isChecked());
                        edt.putString("email", cpEmail.getEditText().getText().toString());
                        edt.apply();

                        setResult(200);
                        finish();
                    }
            }
        };
    }

    private void binding() {

        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        cpEmail = findViewById(R.id.cpEmail);
        cpPassword = findViewById(R.id.cpPassword);
        tvNewUser = findViewById(R.id.tvNewUser);
    }
}