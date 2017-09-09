package com.example.lucas.earthquake;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static com.example.lucas.earthquake.MainActivity.LOG_TAG;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {



    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static Earthquake fetchEarthquakeData(String requestUrl) {
        // Create a Url object
        URL url = creatUrl(requestUrl);

        String jsonResponse = null;
        try {
            // Perform HTTP request to the URL and receive a JSON response back;
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error closing input stream",e);
        }

        // Extract relevant fields from the JSON response and create an Earthquake object
        Earthquake earthquake1 = extractFeatureFromJson(jsonResponse);

        // retrun the EarthquakeData
        return  earthquake1;


    }

    /**
     * Return a new URL object from the given string URL
     */
    private static URL creatUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Error with creating URL",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // if the URL is null, then return early.
        if(url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputstream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // if the request is successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputstream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputstream);
            } else {
                Log.e(LOG_TAG,"Error reponse code:" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem retrieving the earthquake JSON results.",e);
        } finally {
            if (urlConnection != null ) {
                urlConnection.disconnect();
            }
            if(inputstream != null) {
                inputstream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the inpiutStream into a string which contains the whole JSON reponse from the server
     */

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a Earthquake object by parsing out information about the first earthquake from  the input earthquakeJson string.
     */

    private static Earthquake extractFeatureFromJson(String earthquakejson)
    {
        try {
            JSONObject base = new JSONObject(earthquakejson);
            JSONArray featureArray = base.getJSONArray("feature");

            if(featureArray.length()>0) {
                JSONObject c = featureArray.getJSONObject(0);
                JSONObject properties = c.getJSONObject("properties");

                String mag1 = properties.getString("mag");
                String place1 = properties.getString("place");
                String time1 = properties.getString("time");
                String Url = properties.getString("url");

                return new Earthquake(mag1,place1,time1,Url);
            }
        } catch (JSONException e)  {
            Log.e(LOG_TAG, "Problem parsing the ", e);
        }
        return null;
    }



}
