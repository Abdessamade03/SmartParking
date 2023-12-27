package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Security;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText matriculeEditText;
    private Button registerButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        TextView tv = findViewById(R.id.loginRedirectText);

        // Set click listener for the TextView
        tv.setOnClickListener(view -> {
            // Start MainActivity when the TextView is clicked
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
        dbHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.Password);
        matriculeEditText = findViewById(R.id.Matricule);
        registerButton = findViewById(R.id.RegisterButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);


    };
            private void registerUser() {
                String name = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String matricule = matriculeEditText.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !matricule.isEmpty()) {
                    new RegisterTask().execute(name, email, password, matricule);
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }
            }

             class RegisterTask extends AsyncTask<String, Void, String> {
                @Override
                protected String doInBackground(String... params) {
                    String name = params[0];
                    String email = params[1];
                    String password = params[2];
                    String matricule = params[3];

                    try {
                        URL url = new URL("http://192.168.60.242:3000/api/users/add");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoOutput(true);

                        // Create JSON payload
                        String jsonInputString = "{"
                                + "\"name\": \"" + name + "\","
                                + "\"email\": \"" + email + "\","
                                + "\"password\": \"" + password + "\","
                                + "\"matricule\": \"" + matricule + "\""
                                + "}";

                        // Write JSON payload to the request body
                        try (OutputStream os = connection.getOutputStream()) {
                            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                            os.write(input, 0, input.length);
                        }

                        // Get response from the server
                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                                StringBuilder response = new StringBuilder();
                                String responseLine;
                                while ((responseLine = br.readLine()) != null) {
                                    response.append(responseLine.trim());
                                }
                                return response.toString();
                            }
                        } else {
                            Log.e("RegisterTask", "HTTP error: " + connection.getResponseCode());
                            return null;
                        }
                    } catch (Exception e) {
                        Log.e("RegisterTask", "Error:", e);
                        return null;
                    }
                }

                 @Override
                 protected void onPostExecute(String result) {
                     if (result != null && result.equals("success")) {
                         // Registration successful
                         Toast.makeText(RegisterActivity.this, "Registration succeed!", Toast.LENGTH_SHORT).show();
                     } else {
                         // Registration failed
                         Toast.makeText(RegisterActivity.this, "Registration succeed!", Toast.LENGTH_SHORT).show();

                     }
                 }

        }

            ;});}}