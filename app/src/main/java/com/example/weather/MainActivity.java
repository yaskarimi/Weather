package com.example.weather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.models.Current;
import com.example.weather.weekModels.Main;
import com.example.weather.weekModels.WeekWeather;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SaxAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    TextView txtCity;
    TextView txtTemperature;
    ImageView imgWeatherMode;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    EditText edtCity;
    Button button;
    Button btnOk;
    String imageUrl;
    final List<Item>items = new ArrayList<>();
    MainActivity mainActivity;
    RecyclerView searchedBefore;
    String selectedCity;
    ConstraintLayout constraintLayout;
    CityNameAdapter cityNameAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mainActivity = this;

        findViews();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicked();
            }
        });



    }


    public void onClicked(){

                Log.d("MYTAG", "data i got : " + edtCity.getText().toString());
                drawerLayout.closeDrawer(Gravity.LEFT);
                loadData();
                edtCity.getText().clear();

    }


    public void findViews() {
        recyclerView = findViewById(R.id.recycler_view);
        txtCity = findViewById(R.id.txtCity);
         txtTemperature = findViewById(R.id.txtTemperature);
        imgWeatherMode= findViewById(R.id.imgWeatherMode);
        drawerLayout = findViewById(R.id.drawer_layout);
        edtCity = findViewById(R.id.edtCity);
        button = findViewById(R.id.openDrawer);
        btnOk = findViewById(R.id.btnOk);
         searchedBefore = findViewById(R.id.searchedBefore);
        constraintLayout = findViewById(R.id.consraint);
    }





    public void loadData() {
        final String cityName = edtCity.getText().toString();


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/weather?q=" + cityName +
                        "&appid=26b61650a2fef275767d892de32e67bf"
                , new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Current res = new Gson().fromJson(response.toString(), Current.class);
                            String imgUrl = "http://openweathermap.org/img/wn/" + res.weather[0].icon + "@2x.png";
                            new DownloadImageTask(imgWeatherMode).execute(imgUrl);
                            int temp = ((int) res.main.temp_max - 272);

                            txtTemperature.setText(Integer.toString(temp));
                            txtCity.setText(cityName.toUpperCase());
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                });
        AsyncHttpClient httpClient = new AsyncHttpClient();





        httpClient.get("https://api.openweathermap.org/data/2.5/forecast?q="+cityName+
                "&appid=26b61650a2fef275767d892de32e67bf" , new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson gson = new Gson();
                final WeekWeather weekWeather = gson.fromJson(response.toString(), WeekWeather.class);
                int max = 0;
                int min = 0;
                List<String> dayNames = new ArrayList<>();


                for (int i =0 ; i<weekWeather.getCnt() ; i ++){
                    final Item item = new Item();

                 max = (weekWeather.getList().get(i).getMain().getTempMax().intValue());
                 min =weekWeather.getList().get(i).getMain().getTempMin().intValue();
                 item.setTempMax(max);
                 item.setTempMin(min);

                    final int finalI = i;

                    String date= weekWeather.getList().get(i).getDtTxt();
                 Log.d("MYTAG" , "this is date: " + date);

                    try {
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);

                        DateFormatSymbols dfs = new DateFormatSymbols();
                        String[] months = dfs.getMonths();
                        String monthName = months[calendar.get(Calendar.MONTH)];


                        dayNames.add(new SimpleDateFormat("EEEE").format(date1));
                        item.setDayOfWeek(dayNames.get(i));
                         monthName = new SimpleDateFormat("MMMM").format(date1);
                         int monthDay = Integer.parseInt(new SimpleDateFormat("dd").format(date1));
                        item.setMonth(monthName);
                        item.setDay(monthDay);


                             new Thread(new Runnable() {
                                 @Override
                                 public void run() {
                                     imageUrl = "http://openweathermap.org/img/wn/"+weekWeather.getList()
                                             .get(0).getWeather().get(0).getIcon()+"@2x.png";
                                     Bitmap icon = null;

                                     try {
                                         icon = Picasso.with(MainActivity.this).load(imageUrl).get();
                                         Log.d("MYTAG" , "this is icon : " + imageUrl);
                                         item.setIcon(icon);
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }

                                 }
                             }).start();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(checkItem(items, item))
                    {
                        items.add(item);
                    }
                    Log.d("MYTAG","this is item" + items);


                }
                final CityNamesSQLiteDataBase sqLiteDataBase =
                        new CityNamesSQLiteDataBase(MainActivity.this,
                                CityNamesSQLiteDataBase.TABLE_NAME, null, 1);
                if (items.size() != 0 && checkCityName(sqLiteDataBase.loadData(), cityName)){
                    sqLiteDataBase.addData(cityName);
                    sqLiteDataBase.getWritableDatabase();

                }
                CityNameAdapter cityNameAdapter = new CityNameAdapter(sqLiteDataBase.loadData() , mainActivity);
                searchedBefore.setAdapter(cityNameAdapter);
                searchedBefore.setLayoutManager(new LinearLayoutManager(mainActivity , RecyclerView.VERTICAL , false));
                recyclerView.setHasFixedSize(true);
                Log.d("MYTAG" , "this is cities in sqlite :" + cityNameAdapter.names );

                final MyAdapter adapter = new MyAdapter(items);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        



    }

     class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
         ImageView bmImage;

         DownloadImageTask(ImageView bmImage) {
             this.bmImage = bmImage;
         }

         protected Bitmap doInBackground(String... urls) {
             Bitmap bmp = null;
             try {
                 InputStream in = new java.net.URL(urls[0]).openStream();
                 bmp = BitmapFactory.decodeStream(in);
             } catch (Exception e) {
                 Log.e("Error", e.getMessage());
                 e.printStackTrace();
             }
             return bmp;
         }

         protected void onPostExecute(Bitmap result) {
             bmImage.setImageBitmap(result);
         }


    }

    public static boolean checkItem(List<Item> items, Item currItem)
    {
        if(items == null  || items.isEmpty())
        {
            return true;

        }
        for (Item index: items) {
            if(index.getDay() == currItem.getDay() && index.getMonth().equalsIgnoreCase(currItem.getMonth()))
                return false;
        }
        return true;
    }

    public static boolean checkCityName(List<String> cities, String cityName)
    {
        if(cities == null  || cities.isEmpty())
        {
            return true;

        }
        for (String index: cities) {
            if(index.equalsIgnoreCase(cityName))
                return false;
        }
        return true;
    }
    void setBackGround(){
        Calendar calendar = Calendar.getInstance();
       int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (edtCity.getText().toString().isEmpty())
            /*constraintLayout.setBackgroundResource(R.mipmap.dusk);*/

           /*if (hour > 19) {
               constraintLayout.setBackgroundResource(R.mipmap.night);

           }*/
           if (hour > 6 && hour < 17)
               constraintLayout.setBackgroundResource(R.mipmap.day);
           else if (hour >= 17 && hour <= 19)
               constraintLayout.setBackgroundResource(R.mipmap.ghurub);


    }


}
