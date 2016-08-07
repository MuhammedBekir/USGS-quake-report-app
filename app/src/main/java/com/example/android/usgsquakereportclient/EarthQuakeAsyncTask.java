package com.example.android.usgsquakereportclient;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class EarthQuakeAsyncTask extends AsyncTask<String, Void, ArrayList<EarthQuake>> {

    private EarthQuakeAsyncResponse mEarthQuakeAsyncResponse = null;

    public EarthQuakeAsyncTask(EarthQuakeAsyncResponse earthQuakeAsyncResponse) {
        mEarthQuakeAsyncResponse = earthQuakeAsyncResponse;
    }

    @Override
    protected ArrayList<EarthQuake> doInBackground(String... strings) {
        String jsonResponse = "";
        URL url = EarthQuakeExtractData.getURL(strings[0]);
        try {
            jsonResponse = EarthQuakeExtractData.makeHttpRequest(url);
        }
        catch (IOException e) {
            Log.e("EarthQuakeAsyncTask", "Error establishing Connection!!!");
        }
        ArrayList<EarthQuake> earthQuakes = EarthQuakeExtractData.extractFromJson(jsonResponse);
        return earthQuakes;
    }

    @Override
    protected void onPostExecute(ArrayList<EarthQuake> earthQuakes) {
        if (earthQuakes == null) {
            return;
        }
        mEarthQuakeAsyncResponse.processFinish(earthQuakes);
    }

}
