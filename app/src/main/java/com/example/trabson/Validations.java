package com.example.trabson;

import com.google.android.material.textfield.TextInputLayout;

public class Validations {

    public static boolean validateEmptyField(TextInputLayout campo) {
        String valor = campo.getEditText().getText().toString();

        if(valor.isEmpty()) {
            campo.setError("Campo não pode ser vazio");
            campo.requestFocus();
            return false;
        }
        campo.setError(null);
        return true;
    }

    public static boolean validateSize(TextInputLayout campo) {
        String valor = campo.getEditText().getText().toString();

        if(valor.length() > campo.getCounterMaxLength() ) {
            campo.setError("Limite maximo de caracteres é: " + campo.getCounterMaxLength());
            campo.requestFocus();
            return false;
        }

        campo.setError(null);
        return true;
    }

}
