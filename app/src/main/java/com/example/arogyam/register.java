package com.example.arogyam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    private EditText usernameEdt,emailEdt, dobEdt,passwordEdt;
    private Button sendDatabtn;
    private FirebaseAuth mAuth;
    public String android_id;
    public TextView tv;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEdt = findViewById(R.id.id_username);
        emailEdt = findViewById(R.id.id_email);
        dobEdt = findViewById(R.id.id_dob);
        passwordEdt=findViewById(R.id.id_password);
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo").child(android_id);
        tv=findViewById(R.id.login_button);

        tv.setOnClickListener(v->{
            Intent intent2= new Intent(register.this,login.class);
            startActivity(intent2);
        });

        sendDatabtn = findViewById(R.id.button);

        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String dob = dobEdt.getText().toString();
                String password=passwordEdt.getText().toString();

                Long dateValueInLong = System.currentTimeMillis();
                String uhid=dateValueInLong.toString();

                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(dob)) {

                    Toast.makeText(register.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {

                    registerNewUser(email,password);
                    addDatatoFirebase(username, email, dob,uhid);

                }
            }
        });
    }

    private void addDatatoFirebase(String username, String email, String dob, String uhid) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.

                databaseReference.child("userName").setValue(username);
                databaseReference.child("userEmail").setValue(email);
                databaseReference.child("userDob").setValue(dob);
                databaseReference.child("userUhid").setValue(uhid);

                // after adding this data we are showing toast message.
                Toast.makeText(register.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(register.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void registerNewUser(String email, String password) {
        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            Intent intent
                                    = new Intent(register.this,
                                    login.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

    }
}