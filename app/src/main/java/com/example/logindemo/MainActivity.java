package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private int counter=5;
    private TextView userreg;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name =(EditText)findViewById(R.id.editText2);
        Password =(EditText)findViewById(R.id.editText3);
        Login =(Button)findViewById(R.id.btnLogin);
        userreg = (TextView)findViewById(R.id.textView2);
        forgotpass=(TextView)findViewById(R.id.textView4);

        firebaseAuth= FirebaseAuth.getInstance();

        progressDialog= new ProgressDialog(this);


        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Name.getText().toString().equals("") || Password.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this,"Ploteso te gjitha fushat",Toast.LENGTH_SHORT).show();
                }
                else {
                    validate(Name.getText().toString(), Password.getText().toString());
                }
            }
        });

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null)
        {
            finish();
            startActivity(new Intent (MainActivity.this, SecondActivity.class));
        }

        userreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
    }

    private void validate(String username, String password)
    {
        progressDialog.setMessage("Prisni nje moment");
        progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        startActivity(new Intent(MainActivity.this,SecondActivity.class));
                        finish();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Vendos kredincialet e sakta", Toast.LENGTH_SHORT).show();
                        counter--;
                        if(counter==0)
                        {
                            Login.setEnabled(false);
                        }
                    }
                }
            });
    }
}
