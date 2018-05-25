package com.example.shogoyamada.weatherapirxjava;

import java.util.List;

public class WeatherEntity {

    public String base;
    public List<Weather> weather;

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }
}
