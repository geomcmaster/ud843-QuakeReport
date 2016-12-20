package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
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

import android.graphics.drawable.GradientDrawable;

import static com.example.android.quakereport.R.id.date;
import static com.example.android.quakereport.R.id.magnitude;
import static com.example.android.quakereport.R.id.time;

/**
 * Created by Geoff on 12/18/2016.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the Word object located at this position in the list
        Earthquake currentQuake = getItem(position);

        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentQuake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        DecimalFormat decFormatter = new DecimalFormat("0.0");
        decFormatter.setDecimalSeparatorAlwaysShown(true);
        magnitude.setText(decFormatter.format(currentQuake.getMagnitude()));

        TextView offsetView = (TextView) listItemView.findViewById(R.id.offset);
        TextView primaryView = (TextView) listItemView.findViewById(R.id.primary);
        String[] placeText = currentQuake.getPlace().split(LOCATION_SEPARATOR);
        String offset;
        String primary;
        if (placeText.length > 1) {
            offset = placeText[0] + LOCATION_SEPARATOR;
            primary = placeText[1];
        } else {
            offset = getContext().getString(R.string.near_the) + " ";
            primary = placeText[0];
        }
        offsetView.setText(offset);
        primaryView.setText(primary);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        Date date = new Date(currentQuake.getDate());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String formattedDate = dateFormatter.format(date);
        String formattedTime = timeFormatter.format(date);
        dateView.setText(formattedDate);
        timeView.setText(formattedTime);


        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int intMag = (int) Math.floor(magnitude);
        switch (intMag) {
            case 0:
            case 1:
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case 2:
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case 3:
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case 4:
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case 5:
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case 6:
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case 7:
                return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case 8:
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case 9:
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
    }
}
