package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    EditText mail,pass;
    FirebaseAuth auth;
    GoogleSignInClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }

        /*Google sign in code*/
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this,gso);
        /*Google sign in code*/
    }

    public void login(View view) {
        String umail = mail.getText().toString();
        String upass = pass.getText().toString();
        if (umail.isEmpty()|upass.isEmpty()){
            Toast.makeText(this, "Fill all the details",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            auth.signInWithEmailAndPassword(umail,upass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.i("APSSDC","Login Successful");
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                finish();
                            }else{
                                Log.i("APSSDC","Login Failed");
                            }
                        }
                    });
        }
    }

    public void register(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void reset(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this)
                .inflate(R.layout.activity_reset,null,false);
        builder.setView(v);
        builder.setCancelable(false);
        final EditText umail = v.findViewById(R.id.email);
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = umail.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(MainActivity.this, "can't be empty",
                            Toast.LENGTH_SHORT).show();
                }else{
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(MainActivity.this,
                                    new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "Reset mail sent", Toast.LENGTH_SHORT).show();
                                                dialogInterface.dismiss();
                                            }else{
                                                Toast.makeText(MainActivity.this, "Reset Failed",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseGsign(account.getIdToken());
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    private void firebaseGsign(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this,
                                    HomeActivity.class));
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void gsign(View view) {
        Intent i = client.getSignInIntent();
        startActivityForResult(i,0);
    }
}