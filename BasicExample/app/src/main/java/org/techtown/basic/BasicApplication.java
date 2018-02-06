package org.techtown.basic;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 *
 */

public class BasicApplication extends Application {

    public static RequestQueue requestQueue;

    public static String host;
    public static int port;

    @Override
    public void onCreate() {
        super.onCreate();

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    /**
     * Add the input request to the queue to send it
     */
    public static void send(Request request) {
        request.setShouldCache(false);
        requestQueue.add(request);
    }

}
