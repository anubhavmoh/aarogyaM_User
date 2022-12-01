package com.example.arogyam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        
        Button login_button = (Button) findViewById(R.id.login);

        login_button.setOnClickListener(v->{
            Intent intent= new Intent(MainActivity.this,login.class);
            startActivity(intent);
        });
        Button register_button = (Button) findViewById(R.id.register);

        register_button.setOnClickListener(v->{
            Intent intent1= new Intent(MainActivity.this,register.class);
            startActivity(intent1);
        });
    }
}