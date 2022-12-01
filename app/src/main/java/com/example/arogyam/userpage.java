package com.example.arogyam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userpage extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    private TextView username,email, dob, uhid;
    private Button setAlarm1,getLoc;
    public String android_id1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        android_id1 = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        setAlarm1=findViewById(R.id.setalarm);
        getLoc=findViewById(R.id.userloc);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("UserInfo").child(android_id1);

        username = findViewById(R.id.up_username);
        email=findViewById(R.id.up_email);
        dob=findViewById(R.id.up_dob);
        uhid=findViewById(R.id.up_uhid);

        getdata();

        setAlarm1.setOnClickListener(v->{
            Intent intent3= new Intent(userpage.this,adddoctor.class);
            startActivity(intent3);
        });
        getLoc.setOnClickListener(v->{
            Intent intent4= new Intent(userpage.this,MapsActivity.class);
            startActivity(intent4);
        });
    }

    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                username.setText(snapshot.child("userName").getValue().toString());
                dob.setText(snapshot.child("userDob").getValue().toString());
                email.setText(snapshot.child("userEmail").getValue().toString());
                uhid.setText(snapshot.child("userUhid").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(userpage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();

            }
        });

    }
}