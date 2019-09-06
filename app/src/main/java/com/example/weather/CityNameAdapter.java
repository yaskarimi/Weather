package com.example.weather;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CityNameAdapter extends RecyclerView.Adapter<CityNameAdapter.CitiesViewHolder>  {

    List<String> names = new ArrayList<>();
    RecyclerView recyclerView;
    MainActivity mainActivity;


    public  CityNameAdapter(List<String> names ,MainActivity mainActivity ){
        this.names = names;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_recycler_view , parent , false);
        CitiesViewHolder viewHolder = new CitiesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CitiesViewHolder holder, int position) {
        String name = names.get(position);
        holder.txtCityName.setText(name);

        holder.txtCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cityName = holder.txtCityName.getText().toString();
                mainActivity.edtCity.setText(cityName);
                mainActivity.onClicked();
            }
        });


    }


    @Override
    public int getItemCount() {
        return names.size();
    }




    class CitiesViewHolder extends RecyclerView.ViewHolder {
        TextView txtCityName;


         public CitiesViewHolder(@NonNull View itemView) {
             super(itemView);
             txtCityName = itemView.findViewById(R.id.txtCityNames);
         }
     }

}
