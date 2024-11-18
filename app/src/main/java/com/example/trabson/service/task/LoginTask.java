package com.example.trabson.service.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.trabson.model.dto.InputLoginDTO;
import com.example.trabson.model.dto.UserDTO;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginTask extends AsyncTask<InputLoginDTO, Void, UserDTO> {


    @Override
    protected UserDTO doInBackground(InputLoginDTO... dados) {

//        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        StringBuilder result = new StringBuilder();

        HttpsURLConnection httpURLConnection = null;

        try {

            httpURLConnection = (HttpsURLConnection) new URL("").openConnection();

            httpURLConnection.setRequestMethod("POST");

            /*httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");*/

            httpURLConnection.setRequestProperty("Authorization", "Basic xpto123==");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            String param = "grant_type=password&email="+ dados[0].getEmail() +"&password="+ dados[0].getPassword();

            Log.i("parametro", param);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(param);
            wr.flush();
            wr.close();

            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        UserDTO userDTO = new Gson().fromJson(result.toString(), UserDTO.class);

        return userDTO;
    }
}
