package com.example.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CityNameAdapter extends RecyclerView.Adapter<CityNameAdapter.CitiesViewHolder> {

    List<String> names = new ArrayList<>();

    public void cityNameAdapter(List<String> names){
        this.names = names;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_recycler_view , parent , false);
        CitiesViewHolder viewHolder = new CitiesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {

        String name = names.get(position);
        holder.txtCityName.setText(name);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

     class CitiesViewHolder extends RecyclerView.ViewHolder {
        TextView txtCityName;


         public CitiesViewHolder(@NonNull View itemView) {
             super(itemView);
             txtCityName = itemView.findViewById(R.id.txtCityNames);
         }
     }
}
