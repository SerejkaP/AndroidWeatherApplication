package com.example.applicationapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.parsers.SAXParser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView city, weather, temp, humidity, pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent arguments = getIntent();
        if (arguments == null) {
            return;
        } else {
            city = (TextView) findViewById(R.id.city);
            weather = (TextView) findViewById(R.id.weather);
            temp = (TextView) findViewById(R.id.temp);
            humidity = (TextView) findViewById(R.id.humidity);
            pressure = (TextView) findViewById(R.id.pressure);
            city.setText(arguments.getStringExtra("city"));
            if (city.getText().toString().isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка работы приложения", Toast.LENGTH_SHORT);
                toast.show();
                find_weather("Омск");
            }
            weather.setText(arguments.getStringExtra("weather"));
            temp.setText(arguments.getStringExtra("temp"));
            humidity.setText(arguments.getStringExtra("humidity"));
            pressure.setText(arguments.getStringExtra("pressure"));

            find_weather(arguments.getStringExtra("city"));
        }

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    public void find_weather(String name){
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+name+"&lang=ru&units=metric&appid=bdbc5bdbacc53ed46987f68f0ef37398";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String tempStr = String.valueOf(main_object.getDouble("temp"));
                    String pressureStr = String.valueOf(main_object.getDouble("pressure"));
                    String humidityStr = String.valueOf(main_object.getDouble("humidity"));
                    String weatherStr = object.getString("description");
                    city.setText(name);
                    weather.setText(weatherStr);
                    temp.setText(tempStr);
                    pressure.setText(pressureStr);
                    humidity.setText(humidityStr);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                Intent intent = new Intent(this, SecondActivity.class);

                intent.putExtra("weather",weather.getText().toString());
                intent.putExtra("temp",temp.getText().toString());
                intent.putExtra("pressure",pressure.getText().toString());
                intent.putExtra("humidity",humidity.getText().toString());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}