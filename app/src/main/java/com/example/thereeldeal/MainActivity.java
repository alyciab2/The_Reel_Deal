package com.example.thereeldeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.json.JSONArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

        EditText searchBox = findViewById(R.id.editText);
        Button searchButton = findViewById(R.id.searchButton);
        Button searchAgain = findViewById(R.id.searchAgain);
        TextView errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);
        searchButton.setOnClickListener(unused -> {
            if (searchBox.getText().toString().trim().length() == 0) {
                errorMessage.setVisibility(View.VISIBLE);
            } else {
                populateSearchResults(searchBox.getText().toString().trim().toLowerCase());

            }
        });
    }
    //helper methods
    public void populateSearchResults(String movieName) {
        String underscoreName = "";
        String[] individualWords = movieName.split(" ");
        for (int i = 0; i < individualWords.length; i++) {
            if (individualWords.length == 1) {
                underscoreName = movieName;
            }
            if (i != individualWords.length - 1) {
                underscoreName = individualWords[i] + "_";
            }
        }
        setContentView(R.layout.search_results);
        LinearLayout searchResults = findViewById(R.id.searchScrollLayout);
        Button searchDifferent = findViewById(R.id.searchDifferent);
        searchDifferent.setOnClickListener(notclicked -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        //TextView test = findViewById(R.id.test);
        String urlFirst = "https://www.omdbapi.com/?s=";
        String urlSecond = "&apikey=f5c82c9f";

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, urlFirst + underscoreName + urlSecond, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.toString().equals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}")) {
                            setContentView(R.layout.activity_main);
                            TextView errorMessage = findViewById(R.id.errorMessage);
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                        try {
                            JSONArray movieArray = response.getJSONArray("Search");
                            for (int i = 0; i < 1; i++) {
                                setContentView(R.layout.chunk_search_result);
                                View infoChunk = getLayoutInflater().inflate(R.layout.chunk_search_result, searchResults, false);
                                TextView movieTitle = findViewById(R.id.movieSearchTitle);
                                ImageView movieImage = findViewById(R.id.movieSearchImage);
                                Button moreInfo = findViewById(R.id.moreInfo);
                                movieTitle.setText(movieArray.getJSONObject(i).get("Title").toString());
                                String imageURL = movieArray.getJSONObject(i).get("Poster").toString();
                                Picasso.get().load(imageURL).into(movieImage);
                                searchResults.addView(infoChunk);
                                //setContentView(R.layout.search_results);
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                        //JsonElement JsonResult = new JsonParser().parse(response.toString());
                        //test.setText(JsonResult.getAsJsonObject().get("Search").getAsString());
                        /*
                        JsonArray movieArray = JsonResult.get("Search").getAsJsonArray();
                        for (JsonElement movieInfo : movieArray) {
                            test.setVisibility(View.GONE);
                            setContentView(R.layout.chunk_search_result);
                            View infoChunk = getLayoutInflater().inflate(R.layout.chunk_search_result, searchResults, false);
                            TextView movieTitle = findViewById(R.id.movieSearchTitle);
                            ImageView movieImage = findViewById(R.id.movieSearchImage);
                            Button moreInfo = findViewById(R.id.moreInfo);
                            JsonObject movie = movieInfo.getAsJsonObject();
                            test.setText(movie.get("Title").getAsString());
                            searchResults.addView(infoChunk);
                            setContentView(R.layout.search_results);
                        } */


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //test.setText("Something went wrong. Please check your connection.");
                    }
                });
        queue.add(request);

    }
}
