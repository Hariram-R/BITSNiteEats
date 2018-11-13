package com.ram.hari.android.bitsniteeats;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText fname,femail,pass1,pass2;
    Button signup;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        femail = findViewById(R.id.EmailField);
        pass1 = findViewById(R.id.PasswordField1);
        pass2 = findViewById(R.id.PasswordField2);
        fname = findViewById(R.id.FirstName);
        signup = findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ufname = fname.getText().toString().trim();
                final String email = femail.getText().toString().trim();
                String password1 = pass1.getText().toString().trim();
                String password2 = pass2.getText().toString().trim();

                // Regex for validation
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String namePattern = "^[\\p{L} .'-]+$";

                if(!password1.equals(password2))
                {
                    Toast.makeText(SignUp.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!email.matches(emailPattern))
                {
                    Toast.makeText(SignUp.this,"Enter valid email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!(ufname).matches(namePattern))
                {
                    Toast.makeText(SignUp.this,"Enter valid name",Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(SignUp.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Intent openSearch = new Intent(SignUp.this,MainActivity.class);
                                    openSearch.putExtra("Email",email);
                                    Toast.makeText(getApplicationContext(),"Signup successful",Toast.LENGTH_SHORT).show();
                                    startActivity(openSearch);
                                    finish();
                                    finishAffinity();
                                }
                                else
                                {
                                    Log.w("createUserfailure", task.getException());
                                    Toast.makeText(SignUp.this, "Password too short (Atleast 6)",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}
