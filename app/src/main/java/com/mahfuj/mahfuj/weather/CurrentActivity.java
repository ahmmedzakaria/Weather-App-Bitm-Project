package com.mahfuj.mahfuj.weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mahfuj.mahfuj.weather.Class.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentActivity extends AppCompatActivity {

    TextView currentTimeTV;
    TextView currentTempTV;
    TextView currentWeatherTV;
    TextView currentPressureTV;
    TextView currentSunriseTV;
    TextView currentSunSetTV;
    ImageView imageViewIcon;
    TextView currentHumidityTV;
    TextView currentCityTV;

    //String url="http://api.openweathermap.org/data/2.5/weather?q=Dhaka&APPID=915c0d1693830a14abaf3170090a807d";
    String url="";
    String cityName="";
    static final String FILE_NAME = "url.txt";
    static final String City_NAME = "city.txt";
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
        activity = this;
        if(!isNetworkAvailable())
        {

            AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
            myAlert.setMessage("No internet Connection")
                    .setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
            .create();
            myAlert.show();

        }
        else
        {
            if(readFromFile().isEmpty())
            {
                startActivity(new Intent(this, PopUpActivity.class));

            }
            else
            {
                url= readFromFile();
                cityName=readFromFileCity();
                getDataFromWeb();

            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentTimeTV = (TextView) findViewById(R.id.textViewTimeCurrent);
        currentTempTV =(TextView) findViewById(R.id.textViewTemperatureCurrent);
        currentWeatherTV = (TextView) findViewById(R.id.textViewWeatherNameCurrent);
        currentPressureTV =(TextView) findViewById(R.id.textViewPressureCurrent);
        currentHumidityTV =(TextView) findViewById(R.id.textViewHumidityCurrent);
        currentSunriseTV = (TextView) findViewById(R.id.textViewSunRiseCurrent);
        currentSunSetTV =(TextView) findViewById(R.id.textViewSunSetCurrent);
        currentCityTV =(TextView) findViewById(R.id.textViewCityNameCurrent);
        imageViewIcon =(ImageView) findViewById(R.id.imageViewIconCurrent);

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void getDataFromWeb() {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject city = response.getJSONObject("main");
                    JSONArray weatherName = response.getJSONArray("weather");
                    JSONObject sys = response.getJSONObject("sys");

                    currentCityTV.setText(response.optString("name"));
                    cityName = response.optString("name");
                    writeToFileCity(cityName);
                    double temperature=Double.parseDouble(city.optString("temp"));
                    temperature= temperature-273.15;
                    currentTempTV.setText(":  " + String.format("%.2f", temperature) +(char) 0x00B0 + " C");

                    String wName = weatherName.getJSONObject(0).optString("description");
                    currentWeatherTV.setText(wName);
                    String icon = weatherName.getJSONObject(0).optString("icon");
                    if(icon.equals("01d")||icon.equals("01n"))
                    {
                        if(icon.equals("01d"))
                        {
                            imageViewIcon.setImageResource(R.drawable.clear_d);
                        }
                        else if(icon.equals("01n"))
                        {
                            imageViewIcon.setImageResource(R.drawable.clear_n);
                        }

                    }
                    else if(icon.equals("02d")||icon.equals("02n"))
                    {
                        if(icon.equals("02d"))
                        {
                            imageViewIcon.setImageResource(R.drawable.few_cloud_d);
                        }
                        else if(icon.equals("02n"))
                        {
                            imageViewIcon.setImageResource(R.drawable.few_cloud_n);
                        }
                    }
                    else if(icon.equals("03d")||icon.equals("03n"))
                    {
                        imageViewIcon.setImageResource(R.drawable.scattered_cloud);
                    }
                    else if(icon.equals("04d")||icon.equals("04n"))
                    {
                        if(icon.equals("04d"))
                        {
                            imageViewIcon.setImageResource(R.drawable.broken_cloud_d);
                        }
                        else if(icon.equals("04n"))
                        {
                            imageViewIcon.setImageResource(R.drawable.broken_cloud_n);
                        }
                    }
                    else if(icon.equals("09d")||icon.equals("09n"))
                    {
                        if(icon.equals("09d"))
                        {
                            imageViewIcon.setImageResource(R.drawable.shower_rain_d);
                        }
                        else if(icon.equals("09n"))
                        {
                            imageViewIcon.setImageResource(R.drawable.shower_rain_n);
                        }
                    }
                    else if(icon.equals("10d")||icon.equals("10n"))
                    {
                        if(icon.equals("10d"))
                        {
                            imageViewIcon.setImageResource(R.drawable.rain_d);
                        }
                        else if(icon.equals("10n"))
                        {
                            imageViewIcon.setImageResource(R.drawable.rain_n);
                        }
                    }
                    else if(icon.equals("11d")||icon.equals("11n"))
                    {
                        imageViewIcon.setImageResource(R.drawable.thunderstorm);
                    }

                    else if(icon.equals("13d")||icon.equals("13n"))
                    {
                        imageViewIcon.setImageResource(R.drawable.snow);
                    }
                    else if(icon.equals("50d")||icon.equals("50n"))
                    {
                        if(icon.equals("50d"))
                        {
                            imageViewIcon.setImageResource(R.drawable.mist_d);
                        }
                        else if(icon.equals("50n"))
                        {
                            imageViewIcon.setImageResource(R.drawable.mist_n);
                        }
                    }
                    else{
                        imageViewIcon.setImageResource(R.drawable.no);
                    }

                    double pleasure =Double.parseDouble(city.optString("pressure"))/1.3332239;
                    currentPressureTV.setText(":  " + String.format("%.2f", pleasure) + "  mmHg");
                    double humidity =Double.parseDouble(city.optString("humidity"));
                    currentHumidityTV.setText(":  " + String.format("%.1f", humidity) + "%");
                    long  sunRise = Long.parseLong(sys.optString("sunrise")) *1000L;

                    Date riseDate = new Date(sunRise);
                    String rise = new SimpleDateFormat("hh:mm aa").format(riseDate);
                    currentSunriseTV.setText(":  " + rise);

                    long  sunSet = Long.parseLong(sys.optString("sunset")) *1000L;
                    Date setDate = new Date(sunSet);
                    String set = new SimpleDateFormat("hh:mm aa").format(setDate);
                    currentSunSetTV.setText(":  " + set);

                    Date d=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                    String currentDateTimeString = sdf.format(d);

                    currentTimeTV.setText(":   " + currentDateTimeString);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    private String readFromFile() {

        String text = "";


        try {

            InputStream inputStream = openFileInput(FILE_NAME);



            if(inputStream == null)
            {



            }
            else if (inputStream != null) {

                InputStreamReader reader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(reader);

                String receivedString = "";

                StringBuilder builder = new StringBuilder();

                while ((receivedString = bufferedReader.readLine()) != null) {
                    builder.append(receivedString);

                }

                inputStream.close();
                text = builder.toString();
            }


        } catch (FileNotFoundException e) {
            //Toast.makeText(CurrentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();


        } catch (IOException e) {
           // Toast.makeText(CurrentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return text;
    }
    private void writeToFileCity(String data) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(City_NAME, MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e) {

            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private String readFromFileCity() {

        String text = "";


        try {

            InputStream inputStream = openFileInput(City_NAME);




            if (inputStream != null) {

                InputStreamReader reader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(reader);

                String receivedString = "";

                StringBuilder builder = new StringBuilder();

                while ((receivedString = bufferedReader.readLine()) != null) {
                    builder.append(receivedString);

                }

                inputStream.close();
                text = builder.toString();
            }


        } catch (FileNotFoundException e) {
            //Toast.makeText(CurrentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();


        } catch (IOException e) {
           // Toast.makeText(CurrentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return text;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,PopUpActivity.class);
            startActivity(intent);
            //todo: vai implement dialog here
        }
        else if(id == R.id.sixteen_days_weather)
        {
            Intent intent = new Intent(this,DailyWeatherActivity.class);
            intent.putExtra("city",cityName);
            startActivity(intent);
        }
        else if(id == R.id.refresh)
        {
            Intent intent = new Intent(this,CurrentActivity.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

}
