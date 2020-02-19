package com.example.logindemo.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logindemo.ChatActivity;
import com.example.logindemo.HomeFragment;
import com.example.logindemo.Models.ModelPost;
import com.example.logindemo.Models.ModelUser;
import com.example.logindemo.R;
import com.example.logindemo.SecondActivity;
import com.example.logindemo.googlemap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.MyHolder> {

    Context context;
    List<ModelPost> postList;

    public AdapterPost(Context context, List<ModelPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final String uid = postList.get(position).getuId();
        String Pblood = postList.get(position).getpBlood();
        String Pdesc = postList.get(position).getpDesc();
        String PHospital = postList.get(position).getpHospital();
        String Ptitle = postList.get(position).getpTitle();
        String Ptypeh = postList.get(position).getpTypeh();
        String Uemail = postList.get(position).getuEmail();
        String Usurname = postList.get(position).getuSurname();
        final String Uname = postList.get(position).getuName();
        final String Uphone = postList.get(position).getuPhone();
        String Pid = postList.get(position).getpId();
        String Ptimestamp = postList.get(position).getpTimestap();


        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(Ptimestamp));
        String Ptime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        holder.name.setText(Uname);
        holder.surname.setText(Usurname);
        holder.time.setText(Ptime);
        holder.title.setText(Ptitle);
        holder.description.setText(Pdesc);
        holder.llojispitalit.setText(Ptypeh);
        holder.spitali.setText(PHospital);


        holder.morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Coming", Toast.LENGTH_SHORT).show();
            }
        });

        holder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), googlemap.class);
//                myIntent.putExtra("intVariableName", eventsList.get(position).getEvent_id());
                view.getContext().startActivity(myIntent);
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callintent = new Intent(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:" + Uphone));
                if (ActivityCompat.checkSelfPermission(context,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
               //     ActivityCompat.requestPermissions(context.this, new String[]{Manifest.permission.CALL_PHONE});
                    return;
                }
               context.startActivity(callintent);
            }
        });

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentch = new Intent(view.getContext(), ChatActivity.class);
                intentch.putExtra("hisuid",uid);
                view.getContext().startActivity(intentch);
            }
        });
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

                TextView name,surname,time,title,description,llojispitalit,spitali;
                Button direction,chat,call;
                ImageButton morebtn;

            public MyHolder(@NonNull View itemView) {
                super(itemView);

                name= itemView.findViewById(R.id.name1);
                surname= itemView.findViewById(R.id.surname1);
                time= itemView.findViewById(R.id.time1);
                title= itemView.findViewById(R.id.title1);
                description= itemView.findViewById(R.id.desc1);
                llojispitalit= itemView.findViewById(R.id.lloji1);
                spitali= itemView.findViewById(R.id.spitali1);
                direction= itemView.findViewById(R.id.direction1);
                chat= itemView.findViewById(R.id.chat1);
                call= itemView.findViewById(R.id.call1);
                morebtn= itemView.findViewById(R.id.morebutton1);
            }
        }
}