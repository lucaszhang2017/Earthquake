package com.example.lucas.earthquake;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lucas on 8/25/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

private static final String seprator = "of";



    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);

        }

        Earthquake current = getItem(position);
        TextView nametext = (TextView) listItemView.findViewById(R.id.t1);
        String formatMagnitude = formatMag1(current.getmag());
        nametext.setText(formatMagnitude);
        TextView nametext1 = (TextView) listItemView.findViewById(R.id.t2);
        nametext1.setText(current.getplace());
        // 根据地震时间（以毫秒为单位）创建一个新的Date对象
        Date dateObject = new Date(current.gettime());
        TextView dateView = (TextView) listItemView.findViewById(R.id.t3);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        // find ViewId whose name is time;
        TextView timeview = (TextView) listItemView.findViewById(R.id.t4);
        String formaatedTime = formatTime(dateObject);
        //show the time in this view
        timeview.setText(formaatedTime);

        String originalloacation = current.getplace();
        String primaryLocation;
        String locationOffset;

        if(originalloacation.contains(seprator)) {
            String[] parts = originalloacation.split(seprator);
            locationOffset = parts[0] + seprator;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation= originalloacation;
        }

        TextView plv = (TextView) listItemView.findViewById(R.id.t2);
        plv.setText(locationOffset);

        TextView llv = (TextView) listItemView.findViewById(R.id.t21);
        llv.setText(primaryLocation);






        return listItemView;

    }


    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * 从 Date 对象返回格式化的时间字符串（即 "4:30 PM"）。
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMag1(String mag1) {
        DecimalFormat mag2 = new DecimalFormat("0.0");
        return mag2.format(mag1);
    }



    private int getMagnitudeColor(double mag) {
        int magcolorid;
        int magFloor = (int)Math.floor(mag);
        switch(magFloor) {
            case 0:
            case 1:
                magcolorid = R.color.magnitude1;
                break;
            case 3:
                magcolorid = R.color.magnitude2;
                break;
            case 4:
                magcolorid = R.color.magnitude3;
                break;
            case 5:
                magcolorid = R.color.magnitude5;
                break;
            case 6:
                magcolorid = R.color.magnitude6;
                break;
            case 7:
                magcolorid = R.color.magnitude7;
                break;
            case 8:
                magcolorid = R.color.magnitude7;
                break;
            case 9:
                magcolorid = R.color.magnitude9;
                break;
            default:
                magcolorid = R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(),magcolorid);
    }


}
