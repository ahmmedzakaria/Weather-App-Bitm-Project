package com.mahfuj.mahfuj.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PopUpActivity extends AppCompatActivity {
    private TextView cityTV;
    static final String FILE_NAME = "url.txt";
    static final String City_NAME = "city.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_pop);
        cityTV = (TextView) findViewById(R.id.tvCity);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.95 ),(int)(height*.333));

    }
    public void  save(View view)
    {
        Intent intent = new Intent(this,CurrentActivity.class);
        String  url = cityTV.getText().toString();
        if(url.equals(""))
        {
            Toast.makeText(this,"enter city name",Toast.LENGTH_LONG).show();
        }
        else
        {
            String city =url;
            intent.putExtra("city",city);

            url="http://api.openweathermap.org/data/2.5/weather?q="+url+"&APPID=915c0d1693830a14abaf3170090a807d";
            writeToFile(url);
            writeToFileCity(city);
            CurrentActivity.activity.finish();
            startActivity(intent);
            finish();

        }



    }
    public  void  cancel(View view)
    {
        if(readFromFile().isEmpty())
        {
            CurrentActivity.activity.finish();
            finish();

        }
        else
        {
            finish();
        }
    }
    private void writeToFile(String data) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILE_NAME, MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e) {

            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void writeToFileCity(String data) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(City_NAME, MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e) {

           // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
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
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();


        } catch (IOException e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return text;
    }
}
