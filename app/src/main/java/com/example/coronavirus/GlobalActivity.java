package com.example.coronavirus;


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
import android.widget.TextView;

import com.example.coronavirus.model.CountryInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class GlobalActivity extends AppCompatActivity {
    private Button button;
    private TextView mTextViewResult;
    private TextView mSortViewResult;
    private RequestQueue mQueue;
    String next = "<font color='#EE0000'>red</font>";


    enum SortingBy {
        Deaths,
        Cases,
        Recovered
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);


        mTextViewResult = findViewById(R.id.textViewglobal);
        Button buttonSortCases = findViewById(R.id.sortCases);
        Button buttonSortDeaths = findViewById(R.id.sortDeaths);
        Button buttonSortRecoveries = findViewById(R.id.sortRecovered);
    //    mSortViewResult = findViewById(R.id.sortView);

        mQueue = Volley.newRequestQueue(this);
        jsonParse2(SortingBy.Cases);
        buttonSortCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewResult.setText(null);
                buttonSortCases.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_cases));
                buttonSortDeaths.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundbutton));
                buttonSortRecoveries.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundbutton));
                jsonParse2(SortingBy.Cases);
            }
        });

        buttonSortDeaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewResult.setText(null);
                buttonSortCases.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundbutton));
                buttonSortDeaths.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_deaths));
                buttonSortRecoveries.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundbutton));
                jsonParse2(SortingBy.Deaths);
            }
        });
        buttonSortRecoveries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewResult.setText(null);
                buttonSortCases.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundbutton));
                buttonSortDeaths.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundbutton));
                buttonSortRecoveries.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_recovered));
                jsonParse2(SortingBy.Recovered);
            }
        });

    }
    private void jsonParse2(SortingBy sortingBy) {
        String url ="https://api.covid19api.com/summary";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DecimalFormat deciFormat = new DecimalFormat();

                        deciFormat.setGroupingSize(3);
                        try {
                            JSONArray jsonArray = response.getJSONArray("Countries");

                            List<CountryInformation> informations = new ArrayList<>();

                            for (int i=0; i < jsonArray.length(); i++){
                                JSONObject global = jsonArray.getJSONObject(i);
                                String country = global.getString("Country");
                                int totalCases = global.getInt("TotalConfirmed");
                                int totalDeaths = global.getInt("TotalDeaths");
                                int totalRecovered = global.getInt("TotalRecovered");

                                CountryInformation countryInformation = new CountryInformation(
                                        country,
                                        totalDeaths,
                                        totalCases,
                                        totalRecovered
                                );

                                informations.add(countryInformation);

                               // mLinearLayoutglobal.append();

                            }

                            sortListByParameter(informations, sortingBy);


                            for(CountryInformation ci: informations){
                                mTextViewResult.append(ci.getCountryName() + ": \n" +
                                        "Cases: " + ci.getCases() + ", Deaths: " + ci.getDeaths() + ", Recovered: " + ci.getRecovered() + "\n\n");
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

    private void sortListByParameter(List<CountryInformation> informations, SortingBy sortingBy) {
        switch(sortingBy){
            case Cases:
                Collections.sort(informations, new CasesComparator());
                break;
            case Deaths:
                Collections.sort(informations, new DeathsComparator());
                break;
            case Recovered:
                Collections.sort(informations, new RecoveriesComparator());
                break;
        }


        Collections.reverse(informations);
    }

}