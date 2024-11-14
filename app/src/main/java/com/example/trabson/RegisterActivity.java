package com.example.trabson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabson.database.dao.UserDao;
import com.example.trabson.model.Enum.EGender;
import com.example.trabson.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private UserDao uDao;

    private TextInputLayout cpRegisterName, cpRegisterEmail, cpRegisterPassword,
            cpRepeatedPassword,cpRegisterBirthDate;

    private RadioButton rbMale;

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        uDao = new UserDao(getApplicationContext());

        binding();

        btnRegister.setOnClickListener(clickRegister());
    }

    private View.OnClickListener clickRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsCheck()) {

                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                    Date date = null;

                    try {
                        date= formato.parse(cpRegisterBirthDate.getEditText().getText().toString());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    User u = new User(0, cpRegisterName.getEditText().getText().toString(), cpRegisterEmail.getEditText().getText().toString(),
                            cpRegisterPassword.getEditText().getText().toString(), date,
                            rbMale.isChecked()? EGender.MALE : EGender.FEMALE, null);
                    uDao.insert(u);

                    Intent itn = new Intent();
                    itn.putExtra("email", cpRegisterEmail.getEditText().getText().toString());
                    setResult(200);
                    finish();
                }
            }
        };
    }

    private boolean fieldsCheck() {

        boolean camposCorretos = false;

        if(cpRegisterName.getEditText().getText() != null && cpRegisterEmail.getEditText().getText() != null &&
                cpRegisterPassword.getEditText().getText() != null && cpRepeatedPassword.getEditText().getText() != null &&
                cpRegisterBirthDate.getEditText().getText() != null)
        {
            camposCorretos = true;
        }

        if(uDao.findByEmail(cpRegisterEmail.getEditText().getText().toString()) != null) {
            Toast.makeText(getApplicationContext(), "E-mail já em uso!", Toast.LENGTH_LONG).show();
            cpRegisterEmail.getEditText().setText("");
            camposCorretos = false;
        }

        if(!cpRegisterPassword.getEditText().getText().toString().equals(cpRepeatedPassword.getEditText().getText().toString())) {
            Toast.makeText(getApplicationContext(), "Senhas não estão iguais!", Toast.LENGTH_LONG).show();
            cpRegisterPassword.getEditText().setText("");
            cpRepeatedPassword.getEditText().setText("");
            camposCorretos = false;
        }

        return camposCorretos;

    }

    private void binding() {

        cpRegisterName = findViewById(R.id.cpRegisterName);
        cpRegisterEmail = findViewById(R.id.cpRegisterEmail);
        cpRegisterPassword = findViewById(R.id.cpRegisterPassword);
        cpRepeatedPassword = findViewById(R.id.cpRepeatedPassword);
        cpRegisterBirthDate = findViewById(R.id.cpRegisterBirthDate);
        rbMale = findViewById(R.id.rbRegisterMale);
        btnRegister = findViewById(R.id.btnRegister);

    }
}