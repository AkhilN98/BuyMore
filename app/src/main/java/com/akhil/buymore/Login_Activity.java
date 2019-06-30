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

import com.akhil.buymore.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {

    private EditText loginNumber, loginPassword;
    private Button loginButton;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        loginNumber = findViewById(R.id.login_number);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.log_inB);
        loadingbar = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
        String number = loginNumber.getText().toString();
        String password = loginPassword.getText().toString();

        if(TextUtils.isEmpty(number)){
            Toast.makeText(this,"Please enter your number....",Toast.LENGTH_SHORT);
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your password....",Toast.LENGTH_SHORT);
        }
        else{
            loadingbar.setTitle("Signing In");
            loadingbar.setMessage("Please Wait while we check your credentials.");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

                LoginAccount(number,password);
        }
    }

    private void LoginAccount(final String number, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(number).exists()){
                    Users userdata = dataSnapshot.child("Users").child(number).getValue(Users.class);

                    if(userdata.getNumber().equals(number)){
                        if(userdata.getPassword().equals(password)){
                            Toast.makeText(Login_Activity.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();

                            Intent intent = new Intent(Login_Activity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            loadingbar.dismiss();
                            Toast.makeText(Login_Activity.this, "Entered Password is Incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(Login_Activity.this, "Number does not exist! Please create new Account", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login_Activity.this,RegisterActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
