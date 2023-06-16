package com.mirea.kt.nika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.kt.nika.NewsList;
import com.mirea.kt.nika.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewResult;

    private String server = "https://android-for-students.ru";
    private String serverPath = "/coursework/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewResult = findViewById(R.id.textViewResult);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();

                HashMap<String, String> map = new HashMap<>();
                map.put("lgn", login);
                map.put("pwd", password);
                map.put("g", "RIBO-02-21");

                HTTPRunnable hTTPRunnable = new HTTPRunnable(server + serverPath, map);
                Thread th = new Thread(hTTPRunnable);
                th.start();

                try {
                    th.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    String responseBody = hTTPRunnable.getResponseBody();

                    try {
                        JSONObject jSONObject = new JSONObject(responseBody);
                        int resultCode = jSONObject.getInt("result_code");
                        String title = jSONObject.getString("title");
                        String task = jSONObject.getString("task");

                        if (resultCode == 1) {
                            // Успешная авторизация
                            // Переход на следующую активность
                            Intent actIntent = new Intent(getApplicationContext(), NewsList.class);
                            startActivity(actIntent);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Неудачная авторизация
                        textViewResult.setText("знакомы?");
                    }
                }
            }
        });
    }
}