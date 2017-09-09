package com.example.lucas.earthquake;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        earthquakeTask task = new earthquakeTask();
        task.execute(USGS_REQUEST_URL);

    }

    private class earthquakeTask extends AsyncTask<String,Void,Earthquake> {
        protected Earthquake doInBackground(String...urls) {
            if(urls.length < 1 || urls[0] == null) {
                return null;
            }
            Earthquake result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }
        protected void onPostExecute(Earthquake result) {
            if(result == null) {
                return;
            }
            updateUi(result);
        }
    }

    private void updateUi(Earthquake earthquake2) {
        TextView title = (TextView) findViewById(R.id.t1);
        title.setText(earthquake2.getmag());
        TextView title1 = (TextView) findViewById(R.id.t2);
        title1.setText(earthquake2.getplace());
        TextView title2 = (TextView) findViewById(R.id.t3);
        title2.setText(earthquake2.gettime());
    }

}
