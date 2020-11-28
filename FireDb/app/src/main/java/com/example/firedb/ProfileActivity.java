package com.example.firedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firedb.databinding.ActivityProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    DatabaseReference reference;
    SharedPreferences preferences;
    MyModel myModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        preferences = getSharedPreferences("Data",MODE_PRIVATE);
        reference = FirebaseDatabase.getInstance().getReference("StudentDetails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    myModel = dataSnapshot.getValue(MyModel.class);
                    binding.tv.setText(myModel.getName());
                    binding.tv1.setText(myModel.getEmail());
                    binding.tv2.setText(myModel.getRoll());
                    binding.tv3.setText(myModel.getPhone());
                    Glide.with(ProfileActivity.this)
                            .load(myModel.getIlink())
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.iv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void update(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_update,null,false);
        final EditText name = v.findViewById(R.id.name);
        final EditText mail = v.findViewById(R.id.mail);
        final EditText roll = v.findViewById(R.id.roll);
        final EditText phone = v.findViewById(R.id.phone);
        builder.setView(v);
        builder.setCancelable(false);

        name.setText(myModel.getName());
        mail.setText(myModel.getEmail());
        roll.setText(myModel.getRoll());
        roll.setEnabled(false);
        phone.setText(myModel.getPhone());

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",name.getText().toString());
                map.put("email",mail.getText().toString());
                map.put("phone",phone.getText().toString());
                reference.child(roll.getText().toString()).updateChildren(map);
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

    public void del(View view) {
        String roll = preferences.getString("roll",null);
        reference.child(roll).removeValue();
        Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
    }
}