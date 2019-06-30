package com.akhil.buymore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button register_b;
    EditText input_name, input_number, input_password;
    ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_b = findViewById(R.id.sign_UpB);
        input_name = findViewById(R.id.register_name);
        input_number = findViewById(R.id.register_number);
        input_password = findViewById(R.id.register_password);
        loadingbar = new ProgressDialog(this);

        register_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String name = input_name.getText().toString();
        String number = input_number.getText().toString();
        String password = input_password.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter your name....",Toast.LENGTH_SHORT);
        }
        else if(TextUtils.isEmpty(number)){
            Toast.makeText(this,"Please enter your number....",Toast.LENGTH_SHORT);
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your password....",Toast.LENGTH_SHORT);
        }
        else{
            loadingbar.setTitle("Create Account");
            loadingbar.setMessage("Please Wait while we check your credentials.");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            ValidatePhoneNumber(name, number,password);
        }
    }

    private void ValidatePhoneNumber(final String name, final String number, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(number).exists())){

                    HashMap<String, Object> userDataModel = new HashMap<>();
                    userDataModel.put("number",number);
                    userDataModel.put("password",password);
                    userDataModel.put("name",name);

                    RootRef.child("Users").child(number).updateChildren(userDataModel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Congrats your account has been created", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this,Login_Activity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        loadingbar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Error Occurred while creating account! Please Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(RegisterActivity.this, "This" + number + " already exists!", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please Try with another number", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
