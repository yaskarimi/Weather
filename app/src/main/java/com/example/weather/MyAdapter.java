package com.example.weather;

import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Item>items = new ArrayList<>();


    public MyAdapter(List<Item>items){
        this.items=items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view , parent ,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtDay.setText(String.valueOf(items.get(position).getDay()));
        holder.txtDate.setText(items.get(position).getMonth());
        holder.txtTemperatureMax.setText(String .valueOf(items.get(position).getTempMax() - 272));
        holder.txtTemperatureMin.setText(String.valueOf(items.get(position).getTempMin() - 272));
        holder.txtDayOfWeek.setText(String.valueOf(items.get(position).getDayOfWeek()));
        holder.icon.setImageBitmap(items.get(position).getIcon());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTemperatureMax;
        TextView txtTemperatureMin;
        TextView txtDate;
        TextView txtDay;
        TextView txtDayOfWeek;
        ImageView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTemperatureMax = itemView.findViewById(R.id.txtTemperatureMax);
            txtTemperatureMin = itemView.findViewById(R.id.txtTemperatureMin);
            txtDay=itemView.findViewById(R.id.txtDay);
            txtDayOfWeek=itemView.findViewById(R.id.txtDayOfWeek);
            icon = itemView.findViewById(R.id.imgWeatherMode);
        }



    }


}
