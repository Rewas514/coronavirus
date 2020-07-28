package com.example.coronavirus;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.coronavirus.model.CountryInformation;
import com.example.coronavirus.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    private ImageButton buttonnew;
    private Button buttonnews;
    private TextView mTextViewCases;
    private TextView mTextViewDeaths;
    private TextView mTextViewRecoveries;
    private TextView mDateTime;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ImageButton) findViewById(R.id.button_global);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGlobalActivity();
            }
        });

        buttonnew = (ImageButton) findViewById(R.id.button_new);
        buttonnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     mTextViewCases.setText(mTextViewDeaths.getText());
                openGraphActivity();
            }
        });

        buttonnews = findViewById(R.id.button_news);
        buttonnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewsActivity();
            }
        });

        mTextViewCases = findViewById(R.id.text_view_cases);
        mTextViewDeaths = findViewById(R.id.text_view_deaths);
        mTextViewRecoveries = findViewById(R.id.text_view_recoveries);
        mDateTime = findViewById(R.id.datetime);
      //  mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
        date();


        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  mTextViewCases.setText(null);
              //  mTextViewDeaths.setText(null);
              //  mTextViewRecoveries.setText(null);
                jsonParse();
                date();
            }

        });
    }

    private void date(){
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        mDateTime.setText("Updated: " + currentDateTimeString);
    }


    private void jsonParse() {
        String url ="https://api.thevirustracker.com/free-api?global=stats";
      //  String url ="https://api.covid19api.com/summary";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DecimalFormat deciFormat = new DecimalFormat();

                        deciFormat.setGroupingSize(3);
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");


                           for (int i=0; i < jsonArray.length(); i++){
                                JSONObject global = jsonArray.getJSONObject(i);
                                int TotalConfirmed = global.getInt("total_cases");
                                int TotalDeaths = global.getInt("total_deaths");
                                int TotalRecovered = global.getInt("total_recovered");

                             //   int NewConfirmed = global.getInt("total_new_cases_today");
                             //   int NewDeaths = global.getInt("total_new_deaths_today");
                             //   int NewRecovered = global.getInt("total_affected_countries");

                               mTextViewCases.setText(deciFormat.format(TotalConfirmed));
                               mTextViewDeaths.setText(deciFormat.format(TotalDeaths));
                               mTextViewRecoveries.setText(deciFormat.format(TotalRecovered));

                             //  mTextViewCases2.setText(deciFormat.format(NewConfirmed));
                             //  mTextViewDeaths2.setText(deciFormat.format(NewDeaths));
                             //  mTextViewRecoveries2.setText(deciFormat.format(NewRecovered));
                           }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                   error.printStackTrace();
            }
        });
        mQueue.add(request);
    }





    public void openGlobalActivity(){
        Intent intent = new Intent(this, GlobalActivity.class);
        startActivity(intent);
    }

    public void openGraphActivity(){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }

    public void openNewsActivity(){
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
    }

}