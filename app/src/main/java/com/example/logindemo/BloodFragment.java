package com.example.logindemo;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class BloodFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    DatabaseReference dbreference;
    FirebaseUser user;

    Spinner gjaku,lloji,spitali;

    EditText titulli,pershkrimi;

    Button posto;

    String name,surname,uid,email,phone;

    ProgressDialog pd;

    public BloodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_blood, container, false);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        email=user.getEmail();
        uid=user.getUid();

        pd= new ProgressDialog(getActivity());

        gjaku=view.findViewById(R.id.spinner2);
        lloji=view.findViewById(R.id.spinner3);
        spitali=view.findViewById(R.id.spinner4);

        titulli=view.findViewById(R.id.title);
        pershkrimi=view.findViewById(R.id.description);
        posto=view.findViewById(R.id.upload);

        dbreference=FirebaseDatabase.getInstance().getReference("Users");
        Query query =dbreference.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    name= "" + ds.child("emri").getValue();
                    surname= "" + ds.child("mbiemri").getValue();
                    email= "" + ds.child("email").getValue();
                    phone= "" + ds.child("celular").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        String tipetegjakut[]={"A-","A+","B-","B+","AB-","AB+","0-","0+"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinnerbloodfragment,tipetegjakut);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gjaku.setAdapter(adapter);

        final String llojetEspitaleve[]={"Spital Universitar","Spital rajonal","Spital bashkiak","Spital privat"};

        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinnerbloodfragment,llojetEspitaleve);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lloji.setAdapter(adapter1);

        final String spitaluni[]={"Qendra Spitalore Universitare, “Nënë Tereza","Spitali Universitar, “Shefqet Ndroqi”","Spitali Universitar Obstetrik-Gjinekollogjik, “Mbretëresha Geraldinë”","Spitali Universitar Obstetrik-Gjinekollogjik, “Koço Gliozheni"};
        final String spitalrajon[]={"Berat","Durres","Diber","Elbasan","Fier","Gjirokaster","Korce","Kukes","Lezhe","Shkoder","Vlore"};
        final String spitalbashki[]={"Bulqize","Devoll","Delvine","Gramsh","Has","Kavaje","Kolonje","Kruje","Kucove","Lac","Librazhd"};
        final String spitalprivat[]={"Spitali Amerikan Nr.1","Spitali Amerikan Nr.2","Spitali Amerikan Nr.3","Spitali Amerikan dega Fier","Spitali Amerikan dega Durres","Hygeia Hospital Tirana","German Hospital","Spitali i Zemrës","“AKS”, Berat","“Villa Maria”, Spitali Europian"};

        lloji.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String itemselected = llojetEspitaleve[position];
                if(position==0)
                {
                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(),R.layout.spinnerbloodfragment,spitaluni);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spitali.setAdapter(adapter2);
                }
                else if(position==1)
                {
                    ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(),R.layout.spinnerbloodfragment,spitalrajon);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spitali.setAdapter(adapter3);
                }
                else if(position==2)
                {
                    ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(),R.layout.spinnerbloodfragment,spitalbashki);
                    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spitali.setAdapter(adapter4);
                }
                else if(position==3)
                {
                    ArrayAdapter<String> adapter5=new ArrayAdapter<String>(getActivity(),R.layout.spinnerbloodfragment,spitalprivat);
                    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spitali.setAdapter(adapter5);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        posto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= titulli.getText().toString().trim();
                String descp= pershkrimi.getText().toString().trim();
                String blood=gjaku.getSelectedItem().toString();
                String typeh=lloji.getSelectedItem().toString();
                String hospital=spitali.getSelectedItem().toString();

                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(descp) || TextUtils.isEmpty(blood) || TextUtils.isEmpty(typeh) || TextUtils.isEmpty(hospital))
                {
                    Toast.makeText(getActivity(),"Ploteso te gjitha fushat e kerkuara",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(new Intent (getActivity(), MainActivity.class));
                    uploaddata(title,descp,blood,typeh,hospital);
                }
            }
        });

        return view;


    }

    private void uploaddata(String title, String descp, String blood, String typeh, String hospital) {

        pd.setMessage("Postimi eshte duke u publikuar");
        pd.show();

        String timestamp= String.valueOf(System.currentTimeMillis());

        String filePathandName= "Posts/" + "post_" + timestamp;

        HashMap<Object, String> hashMap= new HashMap<>();
        hashMap.put("uId",uid);
        hashMap.put("uName",name);
        hashMap.put("uSurname",surname);
        hashMap.put("uEmail",email);
        hashMap.put("uPhone",phone);
        hashMap.put("pId",timestamp);
        hashMap.put("pTitle",title);
        hashMap.put("pDesc",descp);
        hashMap.put("pBlood",blood);
        hashMap.put("pTypeh",typeh);
        hashMap.put("pHospital",hospital);
        hashMap.put("pTimestap",timestamp);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Posts");
        ref.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(getActivity(),"Postimi u publikua",Toast.LENGTH_SHORT).show();
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(),"Ndodhi nje gabim. Postimi nuk u publikua",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
