package com.example.trabson;

import static com.example.trabson.Validations.validaCampoVazio;

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

import com.example.trabson.config.RetrofitConfig;

import com.example.trabson.model.Enum.EGender;
import com.example.trabson.model.User;
import com.example.trabson.model.dto.UserDTO;
import com.example.trabson.service.IUserService;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout cpRegisterName, cpRegisterEmail, cpRegisterPassword,
            cpRepeatedPassword,cpRegisterBirthDate;

    private RadioButton rbMale;

    private Button btnRegister;

    private IUserService userService;

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

        Retrofit retrofit = new RetrofitConfig().getUserRetrofit();

        userService = retrofit.create(IUserService.class);

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

                    UserDTO userDTO = new UserDTO(cpRegisterName.getEditText().getText().toString(), cpRegisterEmail.getEditText().getText().toString(),
                            cpRegisterPassword.getEditText().getText().toString(), date,
                            rbMale.isChecked()? EGender.MALE : EGender.FEMALE);

                    Call<User> user =  userService.saveUser(userDTO);

                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                            } else {
                                Log.e("API", "Erro: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {
                            Log.e("API", "Falha: " + throwable.getMessage());
                        }
                    });

                    Intent itn = new Intent();
                    itn.putExtra("email", cpRegisterEmail.getEditText().getText().toString());
                    setResult(200, itn);
                    finish();
                }
            }
        };
    }

    private boolean fieldsCheck() {

        boolean camposCorretos = false;

        camposCorretos = validaCampoVazio(cpRegisterName) && validaCampoVazio(cpRegisterEmail) &&
                validaCampoVazio(cpRegisterPassword) && validaCampoVazio(cpRepeatedPassword) &&
                validaCampoVazio(cpRegisterBirthDate);

        /*if(uDao.findByEmail(cpRegisterEmail.getEditText().getText().toString()) != null) {
            cpRegisterEmail.setError("E-mail já em uso");
            cpRegisterEmail.requestFocus();
            cpRegisterEmail.getEditText().setText("");
            camposCorretos = false;
        }*/

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