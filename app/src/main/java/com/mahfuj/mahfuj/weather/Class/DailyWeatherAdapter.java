package com.mahfuj.mahfuj.weather.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahfuj.mahfuj.weather.R;

import java.util.ArrayList;

/**
 * Created by rafay on 3/29/2016.
 */
public class DailyWeatherAdapter extends ArrayAdapter {

    private ArrayList<DailyWeather> weather;
    private Context context;

    private ImageView weatherIcon;
    private TextView showDayTV;
    private TextView showDateTV;
    private TextView showTemperatureTV;
    private TextView showConditionTV;
    private TextView showWindTV;
    private TextView showPressureTV;
    private TextView showHumidityTV;

    public DailyWeatherAdapter(Context context, ArrayList<DailyWeather> weather) {
        super(context, R.layout.row_view_weather,weather);
        this.weather = weather;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = layoutInflater.inflate(R.layout.row_view_weather,null);

        weatherIcon = (ImageView) rowView.findViewById(R.id.imageView_16daysWeather);
        showDayTV = (TextView) rowView.findViewById(R.id.textView_dayName);
        showDateTV = (TextView) rowView.findViewById(R.id.textView_date);
        showTemperatureTV = (TextView) rowView.findViewById(R.id.textView_temperature);
        showConditionTV = (TextView) rowView.findViewById(R.id.textView_precision);
        showWindTV = (TextView) rowView.findViewById(R.id.textView_wind);
        showPressureTV = (TextView) rowView.findViewById(R.id.textView_pressure);
        showHumidityTV = (TextView) rowView.findViewById(R.id.textView_humidity);


        if(weather.get(position).getImage().equals("01d")||weather.get(position).getImage().equals("01n"))
        {
            if(weather.get(position).getImage().equals("01d"))
            {
                weatherIcon.setImageResource(R.drawable.clear_d);
            }
            else if(weather.get(position).getImage().equals("01n"))
            {
                weatherIcon.setImageResource(R.drawable.clear_n);
            }

        }
        else if(weather.get(position).getImage().equals("02d")||weather.get(position).getImage().equals("02n"))
        {
            if(weather.get(position).getImage().equals("02d"))
            {
                weatherIcon.setImageResource(R.drawable.few_cloud_d);
            }
            else if(weather.get(position).getImage().equals("02n"))
            {
                weatherIcon.setImageResource(R.drawable.few_cloud_n);
            }
        }
        else if(weather.get(position).getImage().equals("03d")||weather.get(position).getImage().equals("03n"))
        {
            weatherIcon.setImageResource(R.drawable.scattered_cloud);
        }
        else if(weather.get(position).getImage().equals("04d")||weather.get(position).getImage().equals("04n"))
        {
            if(weather.get(position).getImage().equals("04d"))
            {
                weatherIcon.setImageResource(R.drawable.broken_cloud_d);
            }
            else if(weather.get(position).getImage().equals("04n"))
            {
                weatherIcon.setImageResource(R.drawable.broken_cloud_n);
            }
        }
        else if(weather.get(position).getImage().equals("09d")||weather.get(position).getImage().equals("09n"))
        {
            if(weather.get(position).getImage().equals("09d"))
            {
                weatherIcon.setImageResource(R.drawable.shower_rain_d);
            }
            else if(weather.get(position).getImage().equals("09n"))
            {
                weatherIcon.setImageResource(R.drawable.shower_rain_n);
            }
        }
        else if(weather.get(position).getImage().equals("10d")||weather.get(position).getImage().equals("10n"))
        {
            if(weather.get(position).getImage().equals("10d"))
            {
                weatherIcon.setImageResource(R.drawable.rain_d);
            }
            else if(weather.get(position).getImage().equals("10n"))
            {
                weatherIcon.setImageResource(R.drawable.rain_n);
            }
        }
        else if(weather.get(position).getImage().equals("11d")||weather.get(position).getImage().equals("11n"))
        {
            weatherIcon.setImageResource(R.drawable.thunderstorm);
        }

        else if(weather.get(position).getImage().equals("13d")||weather.get(position).getImage().equals("13n"))
        {
            weatherIcon.setImageResource(R.drawable.snow);
        }
        else if(weather.get(position).getImage().equals("50d")||weather.get(position).getImage().equals("50n"))
        {
            if(weather.get(position).getImage().equals("50d"))
            {
                weatherIcon.setImageResource(R.drawable.mist_d);
            }
            else if(weather.get(position).getImage().equals("50n"))
            {
                weatherIcon.setImageResource(R.drawable.mist_n);
            }
        }
        else{
            weatherIcon.setImageResource(R.drawable.no);
        }
        showDayTV.setText(weather.get(position).getDayName());
        showDateTV.setText(weather.get(position).getDate());
        showHumidityTV.setText(weather.get(position).getHumidity());
        showConditionTV.setText(weather.get(position).getCondition());
        showTemperatureTV.setText(weather.get(position).getTemperature());
        showWindTV.setText(weather.get(position).getWind());
        showPressureTV.setText(weather.get(position).getPleasure());


        return rowView;
    }
}
