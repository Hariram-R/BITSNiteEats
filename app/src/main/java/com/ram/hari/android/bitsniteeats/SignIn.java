package com.ram.hari.android.bitsniteeats;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    EditText emailEt,password;
    Button done, openSignUp;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEt = findViewById(R.id.EmailField);
        password = findViewById(R.id.PasswordField);
        done = findViewById(R.id.donebtn);
        openSignUp = findViewById(R.id.sign_up);
        mAuth = FirebaseAuth.getInstance();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email= emailEt.getText().toString();
                String pass = password.getText().toString();

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(SignIn.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    Intent launchSearch = new Intent(SignIn.this,MainActivity.class);
                                    launchSearch.putExtra("Email",email);
                                    startActivity(launchSearch);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Email not registered! Please sign up",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        openSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchSignUp = new Intent(SignIn.this, SignUp.class);
                startActivity(launchSignUp);
            }
        });

    }
}
