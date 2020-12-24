package com.example.applicationapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView  weather, temp, humidity, pressure;
    EditText editCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle arguments = getIntent().getExtras();

        weather = (TextView) findViewById(R.id.weather);
        temp = (TextView) findViewById(R.id.temp);
        humidity = (TextView) findViewById(R.id.humidity);
        pressure = (TextView) findViewById(R.id.pressure);
        weather.setText(arguments.get("weather").toString());
        temp.setText(arguments.get("temp").toString());
        humidity.setText(arguments.get("humidity").toString());
        pressure.setText(arguments.get("pressure").toString());

        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                Intent intent = new Intent(this, MainActivity.class);
                editCity = (EditText) findViewById(R.id.editCity);
                String city = editCity.getText().toString();
                intent.putExtra("city",city);
                intent.putExtra("weather", weather.getText().toString());
                intent.putExtra("temp", temp.getText().toString());
                intent.putExtra("humidity", humidity.getText().toString());
                intent.putExtra("pressure", pressure.getText().toString());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}