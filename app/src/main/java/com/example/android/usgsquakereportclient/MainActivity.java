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

    ListView listView;
    ArrayList<EarthQuake> earthQuakes;
    EarthQuakeAdapter adapter;
    public static final String URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=1&maxmag=8&limit=100";
    //public static final String URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        earthQuakes = new ArrayList<>();
        adapter = new EarthQuakeAdapter(this, earthQuakes);
        EarthQuakeAsyncTask earthQuakeAsyncTask = new EarthQuakeAsyncTask(new EarthQuakeAsyncResponse() {
            @Override
            public void processFinish(ArrayList<EarthQuake> earthQuakeList) {
                earthQuakes.clear();
                earthQuakes.addAll(earthQuakeList);
                listView.setAdapter(adapter);
            }
        });
        earthQuakeAsyncTask.execute(URL);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthQuake earthQuakeCurrent = earthQuakes.get(i);
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(earthQuakeCurrent.getUrl())));
            }
        });
    }
}
