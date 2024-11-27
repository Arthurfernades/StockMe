package com.example.trabson;

import static com.example.trabson.helper.Validations.validaCampoVazio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabson.model.Enum.EGender;
import com.example.trabson.model.dto.UserDTO;
import com.example.trabson.service.user.UserServiceImp;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout cpRegisterName, cpRegisterEmail, cpRegisterPassword,
            cpRepeatedPassword,cpRegisterBirthDate;

    private RadioButton rbMale;

    private Button btnRegister;

    private String formattedDate;

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

        binding();

        btnRegister.setOnClickListener(clickRegister());
    }

    private View.OnClickListener clickRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsCheck()) {

                    UserDTO userDTO = new UserDTO(cpRegisterName.getEditText().getText().toString(), cpRegisterEmail.getEditText().getText().toString(),
                            cpRegisterPassword.getEditText().getText().toString(), formattedDate,
                            rbMale.isChecked()? EGender.MALE : EGender.FEMALE, 0, 0);

                    UserServiceImp userServiceImp = new UserServiceImp();

                    userServiceImp.createUser(userDTO, new UserServiceImp.AuthServiceCallback() {
                        @Override
                        public void onSuccess(String result) {
                            if(result.equals("Successful")) {
                                Intent itn = new Intent();
                                itn.putExtra("email", cpRegisterEmail.getEditText().getText().toString());
                                setResult(200, itn);
                                finish();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            if(error.equals("Email already exists")) {
                                cpRegisterEmail.setError("E-mail já cadastrado");
                                cpRegisterEmail.getEditText().setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Erro inesoerado", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        };
    }

    private boolean fieldsCheck() {

        boolean camposCorretos = true;
        formattedDate = null;

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            Date date = inputFormat.parse(cpRegisterBirthDate.getEditText().getText().toString());
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e("DateParsing", "Erro ao converter a data: " + e.getMessage());
        }


        camposCorretos = validaCampoVazio(cpRegisterName) && validaCampoVazio(cpRegisterEmail) &&
                validaCampoVazio(cpRegisterPassword) && validaCampoVazio(cpRepeatedPassword) &&
                validaCampoVazio(cpRegisterBirthDate);

        if(!cpRegisterPassword.getEditText().getText().toString().equals(cpRepeatedPassword.getEditText().getText().toString())) {
            Toast.makeText(getApplicationContext(), "As senhas não coincidem!", Toast.LENGTH_LONG).show();
            cpRegisterPassword.getEditText().setText("");
            cpRepeatedPassword.getEditText().setText("");
            camposCorretos = false;
        }

        if(formattedDate == null) {
            camposCorretos = false;
            cpRegisterBirthDate.setError("Formato incorreto. Use dd/MM/yyyy");
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