package com.fiona.a20221120_myrabohuche_nycschools;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fiona.a20221120_myrabohuche_nycschools.data.models.SchoolEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class SatActivity extends AppCompatActivity {
    String JSON_URL = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json";
    String school_name,dbn;
    SchoolEntity schoolEntity;
    TextView nullifier;
    TextView schoolname,num_of_sat_test_takers,sat_critical_reading_avg_score,sat_math_avg_score,sat_writing_avg_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sat_activity);

        Intent intent=getIntent();
        school_name=intent.getExtras().getString("school_name");
        dbn=intent.getExtras().getString("dbn");

        nullifier=findViewById(R.id.nullvalueidentifier);

        nullifier.setText(school_name+ " entity currently not available");
        schoolname=findViewById(R.id.schoolname);
        //Display school name
        schoolname.setText(school_name);


        num_of_sat_test_takers=findViewById(R.id.num_of_sat_test_takers);
        sat_critical_reading_avg_score=findViewById(R.id.sat_critical_reading_avg_score);
        sat_math_avg_score=findViewById(R.id.sat_math_avg_score);
        sat_writing_avg_score=findViewById(R.id.sat_writing_avg_score);


        //parse dbn value to get specified data
        String DATA=JSON_URL+"?dbn="+dbn;

        loadTutorialList(DATA);

    }

    private void loadTutorialList(String DATA) {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loader);
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DATA,
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
                                JSONObject schoolentity = schoolsArray.getJSONObject(i);

                                //creating a schoolentity object and giving them the values from json object


                                 schoolEntity=new SchoolEntity(schoolentity.getString("dbn"),schoolentity.getString("school_name"),
                                        schoolentity.getString("num_of_sat_test_takers"),schoolentity.getString("sat_critical_reading_avg_score"),schoolentity.getString("sat_math_avg_score"),schoolentity.getString("sat_writing_avg_score"));

                            }


                            if (schoolEntity!=null){
                                //Display screen and show results
                                ScrollView scrollView=findViewById(R.id.scroll);
                                scrollView.setVisibility(View.VISIBLE);

                                //Check for nullable values before initialization

                                if (schoolEntity.getSat_critical_reading_avg_score()!=null) {
                                    sat_critical_reading_avg_score.setText(schoolEntity.getSat_critical_reading_avg_score());
                                }

                                if (schoolEntity.getSat_math_avg_score()!=null) {
                                    sat_math_avg_score.setText(schoolEntity.getSat_math_avg_score());

                                }
                                if (schoolEntity.getSat_writing_avg_score()!=null) {
                                    sat_writing_avg_score.setText(schoolEntity.getSat_writing_avg_score());
                                }

                                if(schoolEntity.getNum_of_sat_test_takers()!=null){
                                    num_of_sat_test_takers.setText(schoolEntity.getNum_of_sat_test_takers());

                                }
                            }else {
                                nullifier.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(SatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })

        {




        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SatActivity.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0,0);


    }

}