package com.example.logindemo;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    private DatePickerDialog.OnDateSetListener dateSetListener;

    TextView emri,mbiemri,ditelindja,gjaku,vendbanimi,email,celulari;
    Button password;
    ImageView editname,editsurname,editdate,editblood,editphone,editlocation,editemail;
    Spinner grupgjaku;

    ProgressDialog pd;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        emri=view.findViewById(R.id.emri);
        mbiemri=view.findViewById(R.id.mbiemri);
        ditelindja=view.findViewById(R.id.ditelindja);
        gjaku=view.findViewById(R.id.gjaku);
        vendbanimi=view.findViewById(R.id.vendbanimi);
        celulari=view.findViewById(R.id.celulari);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);

        //edit images
        editblood=view.findViewById(R.id.editblood);
        editlocation=view.findViewById(R.id.editlocation);
        editphone=view.findViewById(R.id.editphone);
        editdate=view.findViewById(R.id.editdate);
        editemail=view.findViewById(R.id.editemail);
        editname=view.findViewById(R.id.editname);
        editsurname=view.findViewById(R.id.editsurname);

        pd= new ProgressDialog(getActivity());


        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String name = "" + ds.child("emri").getValue();
                    String surname = "" + ds.child("mbiemri").getValue();
                    String date = "" + ds.child("ditelindja").getValue();
                    String blood = "" + ds.child("gjaku").getValue();
                    String location = "" + ds.child("qyteti").getValue();
                    String email1 = "" + ds.child("email").getValue();
                    String phone = "" + ds.child("celular").getValue();

                    emri.setText(name);
                    mbiemri.setText(surname);
                    gjaku.setText(blood);
                    celulari.setText(phone);
                    vendbanimi.setText(location);
                    email.setText(email1);
                    ditelindja.setText(date);
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pd.setMessage("Duke ndryshuar emrin");
                updatedialog("emri");
            }
        });

        editsurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Duke ndryshuar mbiemrin");
                updatedialog("mbiemri");
            }
        });

        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();

                pd.setMessage("Duke ndryshuar ditelindjen");
                updatedialog("ditelindja");
            }
        });

        editlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Duke ndryshuar vendbanimin");
                updatedialog("qyteti");
            }
        });

        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Duke ndryshuar emailin");
                updatedialog("email");
            }
        });

        editblood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Duke ndryshuar grupin e gjakut");
                updatedialog("gjaku");
            }
        });

        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Duke ndryshuar celularin");
                updatedialog("celular");
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.setMessage("Duke ndryshuar fjalekalimin");
                AlertDialog.Builder builder1=new AlertDialog.Builder(getActivity());
                builder1.setTitle("Ndrysho fjalekalimin");

                LinearLayout linearLayout1 = new LinearLayout(getActivity());
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setPadding(10, 10, 10, 10);

                final EditText editText1 = new EditText(getActivity());
                editText1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText1.setHint("Vendos fjalekalimin e vjeter");

                final EditText editText2 = new EditText(getActivity());
                editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText2.setHint("Vendos fjalekalimin e ri");

                linearLayout1.addView(editText1);
                linearLayout1.addView(editText2);

                builder1.setView(linearLayout1);

                builder1.setPositiveButton("Ndrysho", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if( editText1.getText().toString().equals("") || editText2.getText().toString().equals("") )
                        {
                            Toast.makeText(getActivity(), "Ploteso te gjitha fushat", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (user != null && user.getEmail() != null) {
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(user.getEmail(), editText1.getText().toString().trim());

                                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            String value = editText2.getText().toString().trim();
                                            user.updatePassword(value).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getActivity(), "Fjalekalimi u ndryshua", Toast.LENGTH_SHORT).show();

                                                    } else {
                                                        Toast.makeText(getActivity(), "Fjalekalimi nuk u ndryshua", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            });
                                        } else {
                                            Toast.makeText(getActivity(), "Fjalekalimi i vjeter eshte gabim", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
            }

        });
                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder1.create().show();

            }
        });

        return view;
    }



    private void updatedialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ndrysho " + key);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Vendos " + key);

        if (key.equals("ditelindja")) {
            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    Log.d("", "onDateSet:mm/dd/yyy: " + month + "/" + day + "/" + year);
                    String date = month + "/" + day + "/" + year;
                    //   ditelindja.setText(date);
                    //   ditelindja.setTextColor(Color.WHITE);
                    if (!TextUtils.isEmpty(date)) {
                        pd.show();

                        HashMap<String, Object> result = new HashMap<>();
                        result.put(key, date);

                        databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pd.dismiss();
                                Toast.makeText(getActivity(), "Ndryshimet u ruajten", Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Vendosni " + key, Toast.LENGTH_SHORT).show();
                    }
                }

            };
        }
        else if(key.equals("gjaku"))
        {
            final Spinner gjaku= new Spinner(getActivity());

            List<String> list =new ArrayList<>();
            list.add("0-");
            list.add("0+");
            list.add("A-");
            list.add("A+");
            list.add("B-");
            list.add("B+");
            list.add("AB-");
            list.add("AB+");


            ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gjaku.setAdapter(adapter);

            linearLayout.addView(gjaku);

            builder.setView(linearLayout);

            builder.setPositiveButton("Ndrysho", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String value = gjaku.getSelectedItem().toString();
                    if (!TextUtils.isEmpty(value)) {
                        pd.show();

                        HashMap<String, Object> result = new HashMap<>();
                        result.put(key, value);

                        databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pd.dismiss();
                                Toast.makeText(getActivity(), "Ndryshimet u ruajten", Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Vendosni " + key, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.create().show();


        }
        else if(key.equals("qyteti"))
        {
            final Spinner qyteti= new Spinner(getActivity());

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

            ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bashki);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            qyteti.setAdapter(adapter1);

            linearLayout.addView(qyteti);

            builder.setView(linearLayout);

            builder.setPositiveButton("Ndrysho", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String value = qyteti.getSelectedItem().toString();
                    if (!TextUtils.isEmpty(value)) {
                        pd.show();

                        HashMap<String, Object> result = new HashMap<>();
                        result.put(key, value);

                        databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pd.dismiss();
                                Toast.makeText(getActivity(), "Ndryshimet u ruajten", Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Vendosni " + key, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.create().show();


        }
        else {
            linearLayout.addView(editText);

            builder.setView(linearLayout);

            builder.setPositiveButton("Ndrysho", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String value = editText.getText().toString().trim();
                    if (!TextUtils.isEmpty(value)) {
                        pd.show();

                        HashMap<String, Object> result = new HashMap<>();
                        result.put(key, value);

                        databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pd.dismiss();
                                Toast.makeText(getActivity(), "Ndryshimet u ruajten", Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Vendosni " + key, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.create().show();
        }
    }

}
