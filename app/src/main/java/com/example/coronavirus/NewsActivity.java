package com.example.coronavirus;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.coronavirus.api.ApiClient;
import com.example.coronavirus.api.ApiInterface;
import com.example.coronavirus.model.Article;
import com.example.coronavirus.model.CountryInformation;
import com.example.coronavirus.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NewsActivity extends AppCompatActivity {

    public static final String API_KEY = "15121d31e4bd4bcb90e6681660b0ed9d";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
 //   private TextView mTextNewsResult;
 //   private RequestQueue mQueue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
      //  adapter = new Adapter(books, NewsActivity.this);
        recyclerView.setAdapter(adapter);

        LoadJson();

/*
        mTextNewsResult = findViewById(R.id.textViewNews);
     //   mSortNewsResult = findViewById(R.id.sortView);
//
        mQueue = Volley.newRequestQueue(this);
        jsonParseNews();*/
    }

    public void LoadJson(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String country = Utils.getCountry();

        Call<News> call;
        call = apiInterface.getNews(country, API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, retrofit2.Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){
                    if (!articles.isEmpty()){
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, NewsActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(NewsActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
            }

                });
        }
    }
    /*
    private void jsonParseNews() {
        String url ="http://newsapi.org/v2/everything?q=coronavirus&from=2020-06-29&sortBy=publishedAt&apiKey=15121d31e4bd4bcb90e6681660b0ed9d";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DecimalFormat deciFormat = new DecimalFormat();

                        deciFormat.setGroupingSize(3);
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                     //       List<CountryInformation> informations = new ArrayList<>();

                            for (int i=0; i < jsonArray.length(); i++){
                                JSONObject global = jsonArray.getJSONObject(i);

                                String title = global.getString("title");
                                String description = global.getString("description");
                                String link = global.getString("url");
                                String content = global.getString("content");

                                // mLinearLayoutglobal.append();
                                mTextNewsResult.append(title + " " + description + " " + link + " " + content + "\n\n");
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
*/


