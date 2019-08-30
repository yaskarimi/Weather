package com.example.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    TextView txtCity;
    TextView txtTemperature;
    ImageView imgWeatherMode;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    DrawerLayout drawerLayout;
    String longitude;
    String latitude;
    EditText edtCity;
    Button button;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });
        final CityNamesSQLiteDataBase sqLiteDataBase = new CityNamesSQLiteDataBase(MainActivity.this , "names" , null , 1);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteDataBase.addData(edtCity.getText().toString());
                Log.d("TAG" , "this is data:" + sqLiteDataBase.loadData());
                CityNameAdapter cityNameAdapter = new CityNameAdapter();
                cityNameAdapter.cityNameAdapter(sqLiteDataBase.loadData());



            }
        });






    }


    public void findViews(){
        recyclerView = findViewById(R.id.recycler_view);
        txtCity = findViewById(R.id.txtCity);
        txtTemperature = findViewById(R.id.txtTemperature);
        imgWeatherMode = findViewById(R.id.imgWeatherMode);
        drawerLayout = findViewById(R.id.drawer_layout);
        edtCity = findViewById(R.id.edtCity);
        button = findViewById(R.id.openDrawer);
        btnOk = findViewById(R.id.btnOk);
    }


    public void setRecyclerView(){
        recyclerView.setAdapter(myAdapter);

    }



    public void loadData(){
        final JSONObject object = new JSONObject();
        AsyncHttpClient weatherClient = new AsyncHttpClient();
        AsyncHttpClient longitudeClient = new AsyncHttpClient();



        String cityName = edtCity.getText().toString();
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(currentDate.toString());
        Date date = dateFormat.parse();
        String day          = (String) DateFormat.format("dd",date); // 20
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013

        while (currentDate!=null){
            Calendar calendar = null;
            calendar.set();

        }

        longitudeClient.get("https://api.opencagedata.com/geocode/v1/google-v3-json?address=" +cityName +  "exclude" + [+  ]+" &key=c9de57c867b8494498339ef67ff95be5"
                , new JsonHttpResponseHandler(){
            @Override

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray results = response.getJSONArray("results");
                    JSONObject obj = results.getJSONObject(1);
                    JSONObject location = obj.getJSONObject("location");
                    longitude = location.getString("lng");
                    latitude = location.getString("lat");

                }catch (Exception e){
                    e.getStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });







        String url = "https://api.darksky.net/forecast/8f5ba74a891f8cc15724b0469950ed3f/"+latitude+","+longitude ;
        weatherClient.get(url , new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONObject currently = null;
                try {
                    currently = object.getJSONObject("currently");
                    String summary = currently.getString("summary");
                    String temperature = currently.getString("temperature");
                    txtTemperature.setText(temperature);
                    txtCity.setText(summary);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });





    }

}
