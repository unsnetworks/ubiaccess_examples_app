package org.ubiaccess.world;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.ubiaccess.common.AppHelper;
import org.ubiaccess.world.data.CountryResult;
import org.ubiaccess.world.data.CountryResultData;
import org.ubiaccess.world.data.ResponseData;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;
    EditText GNPInput;

    TextView logOutput;

    ListView countryListView;
    CountryListAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = (EditText) findViewById(R.id.nameInput);
        GNPInput = (EditText) findViewById(R.id.GNPInput);

        logOutput = (TextView) findViewById(R.id.logOutput);

        countryListView = (ListView)findViewById(R.id.countryListView);
        countryAdapter = new CountryListAdapter(this);

        countryListView.setAdapter(countryAdapter);
        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryResult result = countryAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), "Item selected : " + result.name, Toast.LENGTH_LONG).show();
            }
        });


        Button clearLogButton = (Button) findViewById(R.id.clearLogButton);
        clearLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutput.setText("");
                countryAdapter.clear();
            }
        });

        Button readCountryButton = (Button) findViewById(R.id.readCountryButton);
        readCountryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestReadCountry();
            }
        });

        Button updateCountryButton = (Button) findViewById(R.id.updateCountryButton);
        updateCountryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUpdateCountry();
            }
        });

    }

    public void requestReadCountry() {
        final String name = nameInput.getText().toString();

        final String url = AppHelper.baseUrl + "/examples/readCountry";
        final String requestCode = "101";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("readCountry response -> " + response);

                        Gson gson = new Gson();
                        ResponseData responseData = gson.fromJson(response, ResponseData.class);
                        if (responseData.code == 200) {
                            processReadCountryResponse(response);
                        } else {
                            processResponseError(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("Error in readCountry -> " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                params.put("requestCode", requestCode);
                params.put("name", name);

                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
        println("readCountry request added to queue.");

    }

    public void processReadCountryResponse(String response) {
        try {
            Gson gson = new Gson();

            CountryResultData countryData = gson.fromJson(response, CountryResultData.class);
            println("count of result records : " + countryData.result.size());

            if (countryData.result.size() > 0) {
                println("readCountry success.");

                CountryResult countryResult = countryData.result.get(0);
                println("name -> " + countryResult.name);
                println("continent -> " + countryResult.continent);
                println("population -> " + countryResult.population);
                println("GDP -> " + countryResult.GNP);

                countryAdapter.setItems(countryData.result);
                countryAdapter.notifyDataSetChanged();

            } else {
                println("No record found.");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void requestUpdateCountry() {
        final String name = nameInput.getText().toString();
        final String GNP = GNPInput.getText().toString();

        final String url = AppHelper.baseUrl + "/examples/updateCountry";
        final String requestCode = "101";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("updateCountry response -> " + response);

                        Gson gson = new Gson();
                        ResponseData responseData = gson.fromJson(response, ResponseData.class);
                        if (responseData.code == 200) {
                            println("updateCountry success.");
                        } else {
                            processResponseError(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("Error in updateCountry -> " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                params.put("requestCode", requestCode);
                params.put("name", name);
                params.put("GNP", GNP);

                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
        println("updateCountry request added to queue.");

    }


    public void processResponseError(String result) {
        println("processResponseError called : " + result);

    }


    public void println(String data) {
        logOutput.append(data + "\n");
    }
}
