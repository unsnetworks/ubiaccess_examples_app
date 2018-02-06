package org.techtown.basic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.techtown.basic.data.ResultData;
import org.techtown.basic.data.ResponseInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic example to show how to send a request to UbiAccess Server and process the response
 * Volley and Gson are used
 *
 * @author Mike
 */
public class MainActivity extends AppCompatActivity {
    TextView logOutput;

    EditText hostInput;
    EditText portInput;

    EditText requestCodeInput;
    EditText idInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOutput = (TextView) findViewById(R.id.logOutput);
        hostInput = (EditText) findViewById(R.id.hostInput);
        portInput = (EditText) findViewById(R.id.portInput);
        requestCodeInput = (EditText) findViewById(R.id.requestCodeInput);
        idInput = (EditText) findViewById(R.id.idInput);

        Button requestButton = (Button) findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });

    }

    public void request() {
        // set server info using user input host and port
        setServerInfo();

        // get requestCode and id value
        final String requestCode = requestCodeInput.getText().toString();
        final String id = idInput.getText().toString();

        // request URL
        String url = "http://" + BasicApplication.host + ":" + BasicApplication.port + "/examples/json";

        // make a request
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("RESPONSE -> " + response);

                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("Error occurred -> " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();

                params.put("requestCode", requestCode);
                params.put("id", id);

                return params;
            }
        };

        BasicApplication.send(request);
        println("Request sent.");

    }

    public void processResponse(String response) {
        Gson gson = new Gson();

        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        println("code : " + info.code);

        if (info.code == 200) {
            ResultData resultData = gson.fromJson(response, ResultData.class);
            println("result : " + resultData.result);
        }

    }

    /**
     * Set server info using input host and port information
     */
    public boolean setServerInfo() {
        String host = hostInput.getText().toString();
        String portStr = portInput.getText().toString();

        if (host.length() < 1) {
            Toast.makeText(getApplicationContext(), "Enter server host first.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (portStr.length() < 1) {
            Toast.makeText(getApplicationContext(), "Enter server port first.", Toast.LENGTH_LONG).show();
            return false;
        }

        int port = 0;
        try {
            port = Integer.parseInt(portStr);
        } catch(Exception e) {
            e.printStackTrace();
        }

        BasicApplication.host = host;
        BasicApplication.port = port;

        return true;
    }

    public void println(String data) {
        logOutput.append(data + "\n");
    }

}
