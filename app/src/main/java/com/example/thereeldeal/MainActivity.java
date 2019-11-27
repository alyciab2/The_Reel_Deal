package com.example.thereeldeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchBox = findViewById(R.id.editText);
        Button searchButton = findViewById(R.id.searchButton);
        Button searchAgain = findViewById(R.id.searchAgain);
        TextView errorMessage = findViewById(R.id.errorMessage);
        Button searchAgainButton = findViewById(R.id.searchAgain);
        errorMessage.setVisibility(View.GONE);
        searchButton.setOnClickListener(unused -> {
            if (searchBox.getText().toString().trim().length() == 0) {
                errorMessage.setVisibility(View.VISIBLE);
            } else {
                populateSearchResults(searchBox.getText().toString().trim().toLowerCase());
                setContentView(R.layout.search_results);
                Button searchDifferent = findViewById(R.id.searchDifferent);
                searchDifferent.setOnClickListener(notclicked -> {
                    startActivity(new Intent(this, MainActivity.class));
                });
            }
        });


    }
    //helper methods
    public void populateSearchResults(String movieName) {
        String underscoreName;
        String[] individualWords = movieName.split(" ");
        for (int i = 0; i < individualWords.length; i++) {
            if (i != individualWords.length - 1) {
                underscoreName = individualWords[i] + "_";
            }
        }

    }
}
