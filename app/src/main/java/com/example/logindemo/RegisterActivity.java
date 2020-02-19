package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText username,email,password,mbiemri,numerc;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button register;
    private TextView userlogin,ditelindja;
    private FirebaseAuth firebaseAuth;
    String name,password1,email1,birthdate1,surname,gjaku,qyteti,celular;
    private Spinner dropdownmenu;
    private Spinner qytete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupIUViews();

       // ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,SPINNERLIST);
        //MaterialBetterSpinner betterSpinner=findViewById(R.id.android_material_design_spinner);
        // betterSpinner.setAdapter(arrayAdapter);

        List<String> list =new ArrayList<>();
        list.add("0-");
        list.add("0+");
        list.add("A-");
        list.add("A+");
        list.add("B-");
        list.add("B+");
        list.add("AB-");
        list.add("AB+");


       ArrayAdapter<String> adapter =new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_item, list);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      dropdownmenu.setAdapter(adapter);

        List<String> bashki =new ArrayList<>();
        bashki.add("Belsh");
        bashki.add("Berat");
        bashki.add("Bulqize");
        bashki.add("Cerrik");
        bashki.add("Delvine");
        bashki.add("Devoll");
        bashki.add("Diber");
        bashki.add("Divjake");
        bashki.add("Dropull");
        bashki.add("Durres");
        bashki.add("Elbasan");
        bashki.add("Fier");
        bashki.add("Finiq");
        bashki.add("Fushë-Arrëz");
        bashki.add("Gjirokastër");
        bashki.add("Gramsh");
        bashki.add("Tiranë");

        ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(RegisterActivity.this, R.layout.city_spinner_item, bashki);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qytete.setAdapter(adapter1);

        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                {
                    String e_mail = email.getText().toString().trim();
                    String pass= password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(e_mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                senduserdata();
                                Toast.makeText(RegisterActivity.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Failed Registration", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        ditelindja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(RegisterActivity.this,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                Log.d("","onDateSet:mm/dd/yyy: "+ month + "/"+ day + "/"+ year);
                String date= month+ "/"+day+ "/"+year;
                ditelindja.setText(date);
                ditelindja.setTextColor(Color.WHITE);
            }
        };

    }

    private void setupIUViews(){
        username= (EditText)findViewById(R.id.editText);
        email= (EditText)findViewById(R.id.editText4);
        password= (EditText)findViewById(R.id.editText5);
        register= (Button)findViewById(R.id.button);
        userlogin= (TextView)findViewById(R.id.textView3);
        dropdownmenu=(Spinner)findViewById(R.id.spinner2);
        qytete=(Spinner)findViewById(R.id.spinner);
        mbiemri=(EditText)findViewById(R.id.editText8);
        numerc=(EditText)findViewById(R.id.editText9);
        ditelindja=(TextView)findViewById(R.id.textView5);
    }

    private Boolean validate(){
        Boolean result=false;

         name= username.getText().toString();
         password1= password.getText().toString();
         email1= email.getText().toString();
         birthdate1=ditelindja.getText().toString();
         surname=mbiemri.getText().toString();
         gjaku=dropdownmenu.getSelectedItem().toString();
         qyteti=qytete.getSelectedItem().toString();
         celular=numerc.getText().toString();

        if (name.isEmpty() || password1.isEmpty() || email1.isEmpty() || birthdate1.isEmpty() || surname.isEmpty() || gjaku.isEmpty() || qyteti.isEmpty() || celular.isEmpty() )
        {
            Toast.makeText(this, "Ju lutem plotesoni te gjitha te dhenat", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }
        return result;
    }

    private void senduserdata(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref= firebaseDatabase.getReference("Users");
        UserProfile userProfile = new UserProfile(birthdate1,email1,name,surname,gjaku,qyteti,celular);
        myref.child(firebaseAuth.getUid()).setValue(userProfile);

    }


}
