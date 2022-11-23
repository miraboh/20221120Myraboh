package com.fiona.a20221120_myrabohuche_nycschools;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fiona.a20221120_myrabohuche_nycschools.data.models.School;
import com.fiona.a20221120_myrabohuche_nycschools.presentation.adapter.NYCSchoolsAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {
    String JSON_URL = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
    private ArrayList schools=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadTutorialList();

    }

    private void loadTutorialList() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loader);
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            //we have the array named school inside the object
                            //so here we are getting that json array

                            JSONArray schoolsArray = new JSONArray(response);



                            //now looping through all the elements of the json array
                            for (int i = 0; i < schoolsArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject schoolObject = schoolsArray.getJSONObject(i);

                                //creating a school object and giving them the values from json object


                                School school=new School(schoolObject.getString("dbn"),schoolObject.getString("school_name"));





                                //adding the tutorial to tutoriallist
                                schools.add(school);
                            }

                            //creating custom adapter object
                            //adding the adapter to recyclerview


                            RecyclerView view1=findViewById(R.id.yiyii);
                            view1.setHasFixedSize(true);
                            view1.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                            view1.setItemAnimator(new DefaultItemAnimator());
                            NYCSchoolsAdapter adapter = new NYCSchoolsAdapter(MainActivity.this,schools);
                            view1.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })

        {




        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


}