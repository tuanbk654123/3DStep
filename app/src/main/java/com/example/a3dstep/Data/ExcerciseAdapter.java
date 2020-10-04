package com.example.a3dstep.Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a3dstep.R;
import com.facebook.login.widget.ProfilePictureView;

import java.nio.ByteBuffer;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class ExcerciseAdapter extends RecyclerView.Adapter<ExcerciseAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<Excercise> excerciseList;
    String nameFaceBook ;
    String imageUrlFacebook ;
    String idFacebook ;
    public ExcerciseAdapter(Context mCtx, List<Excercise> excerciseList) {
        this.mCtx = mCtx;
        this.excerciseList = excerciseList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_excercise, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Excercise t = excerciseList.get(position);
        SharedPreferences loginPreferences = mCtx.getSharedPreferences("Setting", MODE_PRIVATE);
        //loginPrefsEditor = loginPreferences.edit();
        nameFaceBook = loginPreferences.getString("nameFaceBook", "false");
        imageUrlFacebook = loginPreferences.getString("imageUrlFacebook", "false");
        idFacebook = loginPreferences.getString("idFacebook", "false");
        holder.txtRecyclerviewExcerciseDateName.setText(nameFaceBook);
        holder.txtRecyclerviewExcerciseDateTime.setText(t.getDate_time());
        holder.txtRecyclerviewExcerciseDistance.setText(t.getDistance());
        holder.txtRecyclerviewExcercisePace.setText(t.getPace());
        holder.txtRecyclerviewExcerciseTime.setText(t.getTime());
        holder.txtRecyclerviewExcerciseNoon.setText(t.getNoon());
        holder.imgRecyclerviewExcerciseAvata.setProfileId(idFacebook);

       if(t.getImage() != null){
           Bitmap bmp = BitmapFactory.decodeFile(t.getImage());
           holder.imgRecyclerviewExcerciseMap .setImageBitmap(bmp);
       }
    }

    @Override
    public int getItemCount() {
        return excerciseList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtRecyclerviewExcerciseDateName, txtRecyclerviewExcerciseDateTime,
                txtRecyclerviewExcerciseNoon,txtRecyclerviewExcerciseDistance, txtRecyclerviewExcercisePace,txtRecyclerviewExcerciseTime;
        ImageView imgRecyclerviewExcerciseMap;
        ProfilePictureView imgRecyclerviewExcerciseAvata;
        public TasksViewHolder(View itemView) {
            super(itemView);
            txtRecyclerviewExcerciseDateName = itemView.findViewById(R.id.txtRecyclerviewExcerciseDateName);
            txtRecyclerviewExcerciseDateTime = itemView.findViewById(R.id.txtRecyclerviewExcerciseDateTime);
            txtRecyclerviewExcerciseDistance = itemView.findViewById(R.id.txtRecyclerviewExcerciseDistance);
            txtRecyclerviewExcercisePace = itemView.findViewById(R.id.txtRecyclerviewExcercisePace);
            txtRecyclerviewExcerciseTime = itemView.findViewById(R.id.txtRecyclerviewExcerciseTime);
            txtRecyclerviewExcerciseNoon = itemView.findViewById(R.id.txtRecyclerviewExcerciseNoon);
            imgRecyclerviewExcerciseMap = itemView.findViewById(R.id.imgRecyclerviewExcerciseMap);
            imgRecyclerviewExcerciseAvata = itemView.findViewById(R.id.imgRecyclerviewExcerciseAvata);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*Excercise excercise = excerciseList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
            intent.putExtra("excercise", excercise);
            mCtx.startActivity(intent);*/
        }
    }
}
