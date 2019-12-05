package com.example.thereeldeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
        ImageButton poster0 = findViewById(R.id.poster0);
        ImageButton poster1 = findViewById(R.id.poster1);
        ImageButton poster2 = findViewById(R.id.poster2);
        ImageButton poster3 = findViewById(R.id.poster3);
        ImageButton poster4 = findViewById(R.id.poster4);
        ImageButton poster5 = findViewById(R.id.poster5);
        ImageButton poster6 = findViewById(R.id.poster6);
        ImageButton poster7 = findViewById(R.id.poster7);
        ImageButton poster8 = findViewById(R.id.poster8);

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
                            for (int i = 0; i < movieArray.length(); i++) {
                                String imageURL = movieArray.getJSONObject(i).get("Poster").toString();
                                if (i == 0) {
                                    Picasso.get().load(imageURL).into(poster0);
                                    poster0.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                } else if (i == 1) {
                                    Picasso.get().load(imageURL).into(poster1);
                                    poster1.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                } else if (i == 2) {
                                    Picasso.get().load(imageURL).into(poster2);
                                    poster2.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                } else if (i == 3) {
                                    Picasso.get().load(imageURL).into(poster3);
                                    poster3.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                } else if (i == 4) {
                                    Picasso.get().load(imageURL).into(poster4);
                                    poster4.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                } else if (i == 5) {
                                    Picasso.get().load(imageURL).into(poster5);
                                    poster5.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                } else if (i == 6) {
                                    Picasso.get().load(imageURL).into(poster6);
                                    poster6.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                } else if (i == 7) {
                                    Picasso.get().load(imageURL).into(poster7);
                                    poster7.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                } else if (i == 8) {
                                    Picasso.get().load(imageURL).into(poster8);
                                    poster8.setTag(movieArray.getJSONObject(i).get("imdbID").toString());
                                }
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
