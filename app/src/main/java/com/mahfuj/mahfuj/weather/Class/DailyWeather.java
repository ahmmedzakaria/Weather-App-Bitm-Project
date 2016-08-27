package com.mahfuj.mahfuj.weather.Class;

/**
 * Created by mahfu on 4/1/2016.
 */
public class DailyWeather {

    private String dayName;
    private String date;
    private String temperature;
    private String condition;
    private String wind;
    private String humidity;
    private String pleasure;



    private String image;

    public DailyWeather(String dayName, String date, String temperature, String condition, String wind, String humidity, String image, String pleasure) {
        this.dayName = dayName;
        this.date = date;
        this.temperature = temperature;
        this.condition = condition;
        this.wind = wind;
        this.humidity = humidity;
        this.image =image;
        this.pleasure =pleasure;
    }

    public String getPleasure() {
        return pleasure;
    }

    public void setPleasure(String pleasure) {
        this.pleasure = pleasure;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
