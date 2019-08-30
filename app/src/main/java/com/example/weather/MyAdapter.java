package com.example.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view , parent ,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTemperature;
        TextView txtDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtCity);
            txtTemperature = itemView.findViewById(R.id.txtTemperature);
        }



    }

}
