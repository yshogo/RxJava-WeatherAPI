package com.example.shogoyamada.weatherapirxjava;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("=NETWORK="))
                .build();

        dialog = new ProgressDialog(this);
        dialog.setTitle("読み込み中");
        dialog.show();
        adapter.create(WeatherApi.class).get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherEntity>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                        Log.d("MainActivity", "コンプリート");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Log.e("MainActivity", "Error : " + e.toString());
                    }

                    @Override
                    public void onNext(WeatherEntity weatherEntity) {
                        dialog.dismiss();
                        List<WeatherEntity.Weather> list = weatherEntity.weather;
                        ((TextView)findViewById(R.id.whether)).setText(list.get(0).main);
                    }
                });
    }
}
