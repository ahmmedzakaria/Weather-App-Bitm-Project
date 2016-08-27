package com.mahfuj.mahfuj.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mahfuj.mahfuj.weather.Class.AppController;
import com.mahfuj.mahfuj.weather.Class.DailyWeatherAdapter;
import com.mahfuj.mahfuj.weather.Class.DailyWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DailyWeatherActivity extends AppCompatActivity {

    TextView cityNameTV;
    ListView dailyWeatherList;
    String urlOne ="http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    String urlTwo ="0&APPID=915c0d1693830a14abaf3170090a807d";
    String cityUrl="";
    String mainUrl="";
    private ArrayList<DailyWeather> sixteen = new ArrayList<>();
    DailyWeatherAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        cityUrl = getIntent().getStringExtra("city");


        cityNameTV = (TextView) findViewById(R.id.textView_cityName);
        cityNameTV.setText(cityUrl);
        dailyWeatherList = (ListView) findViewById(R.id.listView_16daysWeather);
         getDataFromWeb();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        adapter = new DailyWeatherAdapter(this, sixteen);
        dailyWeatherList.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void getDataFromWeb() {

        mainUrl =urlOne+cityUrl+cityUrl+urlTwo;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mainUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                for (int i = 0; i <7; i++) {

                    try {

                        JSONObject list = response.getJSONArray("list").getJSONObject(i);

                        long useLong = Long.parseLong(list.optString("dt"))*1000L;
                        String data = new SimpleDateFormat("dd-MM-yyyy").format(useLong);
                        String dataName = new SimpleDateFormat("EEEE").format(useLong);

                        JSONObject temp = response.getJSONArray("list").getJSONObject(i).getJSONObject("temp");


                        double day = Double.parseDouble(temp.optString("day"))-273.15;
                        String temperature =(":     " + String.format("%.2f", day)+(char) 0x00B0 + " C");


                        JSONObject weatherObject = response.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0);
                        String description = weatherObject.getString("description");
                        String icon = weatherObject.getString("icon");

                        double pressureDouble = Double.parseDouble(list.optString("pressure"))/1.3332239;
                        String pleasure =":  " + String.format("%.2f", pressureDouble) + "  mmHg";

                        double humidityDouble = Double.parseDouble(list.optString("humidity"));
                        String humidity =":  " + String.format("%.1f", humidityDouble) + "%";

                        double windDouble = Double.parseDouble(list.optString("speed"));
                        String wind;

                        if(windDouble>=0 && windDouble<1)
                        {
                            wind = ":  Calm " + String.format("%.2f", windDouble) + " m/s";
                        }

                        else if(windDouble>=1 && windDouble<3.5)
                        {
                            wind = ":  Light breeze " + String.format("%.2f", windDouble) + " m/s";
                        }
                        else if(windDouble>=3.5 && windDouble<5)
                        {
                            wind = ":  Gentle breeze " + String.format("%.2f", windDouble) + " m/s";
                        }
                        else
                        {
                            wind = ":  " + String.format("%.2f", windDouble) + " m/s";
                        }
                        DailyWeather dailyWeather = new DailyWeather(dataName, data, temperature, description,wind,humidity,icon,pleasure);
                        sixteen.add(dailyWeather);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                adapter = new DailyWeatherAdapter(getApplicationContext(), sixteen);
                dailyWeatherList.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);



    }
}
