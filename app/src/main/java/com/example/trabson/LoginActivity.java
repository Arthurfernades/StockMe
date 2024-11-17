package com.example.trabson;

import android.content.Intent;
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

import com.example.trabson.database.dao.UserDao;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private CheckBox cbRemember;

    private Button btnLogin;

    private UserDao uDao;

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

        uDao = new UserDao(this);

        btnLogin.setOnClickListener(clickRemember());

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

    private View.OnClickListener clickRemember() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uDao.emailExists(cpEmail.getEditText().getText().toString())) {
                    if(uDao.confirmByEmailAndPassword(cpEmail.getEditText().getText().toString(), cpPassword.getEditText().getText().toString())) {
                        setResult(200);
                        finish();
                    } else {
                        cpPassword.setError("Senha inválida");
                    }
                } else {
                    cpEmail.setError("E-mail inexistente");
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