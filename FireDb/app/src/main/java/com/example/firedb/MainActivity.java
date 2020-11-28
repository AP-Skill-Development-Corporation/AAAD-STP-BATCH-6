package com.example.firedb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.firedb.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DatabaseReference dRef;
    StorageReference sRef;
    Uri uri;
    ProgressDialog dialog;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        preferences = getSharedPreferences("Data",MODE_PRIVATE);
        dRef = FirebaseDatabase.getInstance().getReference("StudentDetails");
        sRef = FirebaseStorage.getInstance().getReference().child(UUID.randomUUID().toString());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading.....");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        binding.profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                /* i.setType("application/pdf") */
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Image"),0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            binding.profileimg.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(View view) {
        dialog.show();
        String name = binding.name.getText().toString();
        String mail = binding.mail.getText().toString();
        String roll = binding.roll.getText().toString();
        String phone = binding.phone.getText().toString();
        sRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("roll",roll);
                        editor.commit();
                        MyModel model = new MyModel(url,name,mail,roll,phone);
                        dRef.child(roll).setValue(model);
                        Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void view(View view) {
        startActivity(new Intent(this,ProfileActivity.class));
    }
}