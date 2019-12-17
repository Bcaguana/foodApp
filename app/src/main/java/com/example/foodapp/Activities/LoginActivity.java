package com.example.foodapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.R;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private HashMap<String, String> loginInfoMap;
    private EditText usernameEditText;
    private EditText passwordEditText;
    public String ser = "hi";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.usernameEditText = findViewById(R.id.usernameEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);

        Intent intent = getIntent();
        usernameEditText.setText(intent.getStringExtra("user"));

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("user", usernameEditText.getText());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    //HashMaps Cannot contain duplicate Keys, so this makes checking if a Username already exists fast
    public boolean checkForDuplicates(String s){
        return loginInfoMap.containsKey(s);
    }

}
