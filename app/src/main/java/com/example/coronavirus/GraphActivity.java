package com.example.coronavirus;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.coronavirus.model.CountryInformation;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series1, series2, series3;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        getDataAndDrawGraph();


    }

    private void drawGraph(ArrayList<CountryInformation> data) {

        GraphView graph = findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();
        series3 = new LineGraphSeries<>();

        ArrayList<CountryInformation> lithuaniaData = new ArrayList<>();

        for (CountryInformation ci: data){
            if(ci.getCountryName().equals("LT")){
                lithuaniaData.add(ci);
            }
        }

        // TODO: min date and max date
        int totalDays = lithuaniaData.size();

        for(int i = 0; i < totalDays; i++){

            CountryInformation ci = lithuaniaData.get(totalDays - i - 1);

            double date = i; // TODO
            int cases = ci.getCases();
            int deaths = ci.getDeaths();
            int recovered = ci.getRecovered();
            series1.appendData(new DataPoint(date, cases), true, 100);
            series2.appendData(new DataPoint(date, deaths), true, 100);
            series3.appendData(new DataPoint(date, recovered), true, 100);

        }

        series1.setColor(Color.BLUE);
        series2.setColor(Color.RED);
        series3.setColor(Color.GREEN);
        graph.addSeries(series1);
        graph.addSeries(series2);
        graph.addSeries(series3);
    }

    private void getDataAndDrawGraph() {
        String url ="https://thevirustracker.com/timeline/map-data.json";
        mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DecimalFormat deciFormat = new DecimalFormat();

                        deciFormat.setGroupingSize(3);
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            ArrayList<CountryInformation> retrievedData = ReadDataIntoArray(jsonArray);

                            System.out.println(retrievedData.get(0).getCountryName());

                            drawGraph(retrievedData);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(request);

    }

    private ArrayList<CountryInformation> ReadDataIntoArray(JSONArray jsonArray) throws JSONException {
        ArrayList<CountryInformation> data = new ArrayList<CountryInformation>();

        for (int i=0; i < jsonArray.length(); i++){
            JSONObject global = jsonArray.getJSONObject(i);

            CountryInformation ci = new CountryInformation();

            ci.setCases(global.getInt("cases"));
            ci.setDeaths(global.getInt("deaths"));
            //ci.setRecovered(global.getInt("recovered"));
            ci.setCountryName(global.getString("countrycode"));
            ci.setDate(global.getString("date"));

            data.add(ci);
        }

        return data;
    }
}
