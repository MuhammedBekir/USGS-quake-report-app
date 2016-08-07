package com.example.android.usgsquakereportclient;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<EarthQuake> earthQuakes;
    private EarthQuakeAdapter adapter;
    private static final String url = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        earthQuakes = new ArrayList<>();
        adapter = new EarthQuakeAdapter(this, earthQuakes);
        EarthQuakeAsyncTask earthQuakeAsyncTask = new EarthQuakeAsyncTask(new EarthQuakeAsyncResponse() {
            @Override
            public void processFinish(ArrayList<EarthQuake> earthQuakes) {
                earthQuakes.clear();
                earthQuakes.addAll(earthQuakes);
                listView.setAdapter(adapter);
            }
        });
        earthQuakeAsyncTask.execute(url);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthQuake earthQuakeCurrent = earthQuakes.get(i);
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(earthQuakeCurrent.getUrl())));
            }
        });
    }
}
