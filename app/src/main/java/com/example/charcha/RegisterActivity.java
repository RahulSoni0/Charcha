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

public class RegisterActivity extends AppCompatActivity
{
    private TextView tv_sign_in;
    private EditText et_email,et_pswd;
    private Button btn_sign_up;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog=new ProgressDialog(this);
        et_pswd=findViewById(R.id.et_pswd);
        et_email=findViewById(R.id.et_email);
        tv_sign_in=findViewById(R.id.tv_sign_in);
        btn_sign_up=findViewById(R.id.btn_sign_up);

        mAuth=FirebaseAuth.getInstance();


        tv_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=et_email.getText().toString().trim();
                String password=et_pswd.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                    et_email.setError("Arey bhai email toh daal de");
                else if(TextUtils.isEmpty(password))
                    et_pswd.setError("Password kon dalega?");
                else if(password.length()<6)
                    Toast.makeText(RegisterActivity.this, "Atleast 6 length is needed", Toast.LENGTH_SHORT).show();
                else
                    registerUser(email,password);
            }
        });
    }

    private void registerUser(String email, String password)
    {
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this, "Yup it's Successful", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Sorry Registration process Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}