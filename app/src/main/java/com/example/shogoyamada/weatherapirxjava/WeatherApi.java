package com.example.shogoyamada.weatherapirxjava;


import retrofit.http.GET;
import rx.Observable;

public interface WeatherApi {

    String API_KEY = "b6625cf8cacf050284c58c63bcc269a2";
    @GET("/data/2.5/weather?q=London,uk&appid=" + API_KEY)
    public Observable<WeatherEntity> get();
}
