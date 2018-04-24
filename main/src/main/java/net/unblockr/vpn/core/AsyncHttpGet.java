/*
 * Copyright (c) 2012-2018 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package net.unblockr.vpn.core;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncHttpGet extends AsyncTask<String, Void, String> {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    private String m_url;

    public AsyncHttpGet() {
        super();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                //Set methods and timeouts
                urlConnection.setRequestMethod(REQUEST_METHOD);
                urlConnection.setReadTimeout(READ_TIMEOUT);
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                urlConnection.connect();

                return readResponse(urlConnection.getInputStream());
            } catch (Exception E) {
                // BAd JSON
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        }

        return null;
    }

    private String readResponse(InputStream in) throws IOException {
        StringBuffer response = new StringBuffer();
        String inputLine;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }

        return response.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String r) {
        super.onPostExecute(r);
    }
}
