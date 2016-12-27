/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.app.SearchManager.QUERY;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>>{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private EarthquakeAdapter mAdapter;
    private TextView mEmptyView;
    private static final String QUERY = "http://earthquake.usgs.gov/fdsnws/event/1/" +
            "query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, QUERY);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        findViewById(R.id.progcircle).setVisibility(View.GONE);
        mAdapter.clear();
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
        mEmptyView.setText(R.string.no_earthquakes);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        mAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        mEmptyView = (TextView) findViewById(R.id.emptyview);
        earthquakeListView.setEmptyView(mEmptyView);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });


        getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);

    }
}
