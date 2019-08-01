package com.example.firebaseblog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPass;
    private Button loginBtn;
    private Button regBtn;
    private ProgressBar progress;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginEmail =  findViewById(R.id.email);
        loginPass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);
        regBtn = findViewById(R.id.newUser);
        progress = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId = loginEmail.getText().toString();
                String passId = loginPass.getText().toString();

                if(!TextUtils.isEmpty(emailId) && !TextUtils.isEmpty(passId)){
                    progress.setVisibility(View.VISIBLE);
                     mAuth.signInWithEmailAndPassword(emailId,passId).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 sendtoMain();

                             }else{
                                 String error = task.getException().toString();
                                 //Log.i("error","getting error");
                                 Toast.makeText(LoginActivity.this,"Error: "+ error, Toast.LENGTH_LONG).show();

                             }
                             progress.setVisibility(View.INVISIBLE);

                         }
                     });

                }else{

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            sendtoMain();
        }
        else{

        }



    }

    private void sendtoMain() {
        Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
