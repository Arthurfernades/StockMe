package com.example.trabson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.trabson.model.dto.LoginDTO;
import com.example.trabson.service.user.UserServiceImp;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private CheckBox cbRemember;

    private Button btnLogin;

    private TextInputLayout cpEmail, cpPassword;

    private TextView tvNewUser;

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

                cpEmail.setError(null);
                cpPassword.setError(null);

                UserServiceImp userServiceImp = new UserServiceImp();

                LoginDTO loginDTO = new LoginDTO(cpEmail.getEditText().getText().toString(),
                        cpPassword.getEditText().getText().toString());

                userServiceImp.authorizeLogin(loginDTO, new UserServiceImp.AuthServiceCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals("Authorized")) {
                            SharedPreferences prefs = getSharedPreferences("StockMe", MODE_PRIVATE);
                            SharedPreferences.Editor edt = prefs.edit();
                            edt.putBoolean("loged", cbRemember.isChecked());
                            edt.putString("email", cpEmail.getEditText().getText().toString());
                            edt.apply();

                            setResult(200);
                            finish();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        if(error.equals("Email not registered")) {
                            cpEmail.setError("E-mail não cadastrado");
                            cpPassword.getEditText().setText("");

                        } else if(error.equals("Invalid credentials")) {
                            cpPassword.setError("Senha incorreta");
                            cpPassword.getEditText().setText("");
                            cpEmail.setError(null);

                        } else {
                            Toast.makeText(getApplicationContext(), "Erro na API: " + error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
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