package com.example.charcha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    private TextView tv_forgot_pswd,tv_sign_up;
    private Button btn_login;
    private EditText et_email,et_pswd;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_pswd=findViewById(R.id.et_pswd);
        et_email=findViewById(R.id.et_email);
        btn_login=findViewById(R.id.btn_login);
        tv_sign_up=findViewById(R.id.tv_sign_up);
        tv_forgot_pswd=findViewById(R.id.tv_forgot_pswd);
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=et_email.getText().toString();
                String password=et_pswd.getText().toString();
                if(TextUtils.isEmpty(email))
                    et_email.setError("Arey bhai email toh daal de");
                else if(TextUtils.isEmpty(password))
                    et_pswd.setError("Password kon dalega?");
                else
                    login(email,password);
            }
        });

    }

    private void login(String email, String password) {
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Sorry Login was Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //this is to check whther user is already logged in or not
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null)
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
    }
}