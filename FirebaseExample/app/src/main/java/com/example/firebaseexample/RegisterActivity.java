package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText email,pass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.umail);
        pass = findViewById(R.id.upass);
        auth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String umail = email.getText().toString();
        String upass = pass.getText().toString();
        if (umail.isEmpty()|upass.isEmpty()){
            Toast.makeText(this, "fill all the details", Toast.LENGTH_SHORT).show();
        }
        else{
            auth.createUserWithEmailAndPassword(umail,upass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(RegisterActivity.this,
                                        MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, "Regitration Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}