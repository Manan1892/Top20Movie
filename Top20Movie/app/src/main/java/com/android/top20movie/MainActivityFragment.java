package com.android.top20movie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private AsyncTask<Void,Void,String[]> task;
    private ArrayList<String> movieTitles = new ArrayList<>();


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewGroup = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView)viewGroup.findViewById(R.id.listView);

        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getJSONData();

        return viewGroup;
    }

    public void refreshList () {
        listView.setAdapter(null);
        getJSONData();
    }

    private void getJSONData() {

        movieTitles.clear();
        final String url = "http://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=683a55ab4b5e51a5a7165e8cd0d2debf:17:75156286&thousand-best=Y";
        Log.d("url", url);

        task = new AsyncTask<Void,Void,String[]>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String[] doInBackground(Void... params) {
                JsonObjectRequest request = new JsonObjectRequest(url,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(final JSONObject response) {
                                Log.d("json_response", response.toString());
                                JSONArray movies = response.optJSONArray("results");
                                for (int i = 0; i<movies.length(); i++) {
                                    JSONObject movie = movies.optJSONObject(i);
                                    movieTitles.add(movie.optString("display_title"));
                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle api call errors here
                            }
                        }
                );
                request.setRetryPolicy(new DefaultRetryPolicy(15000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getInstance(getActivity()).getRequestQueue().add(request);
                return new String[0];
            }

            @Override
            protected void onPostExecute(String[] strings) {
                super.onPostExecute(strings);
                setAdapter();
            }
        };
        task.execute();
    }

    private void setAdapter() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                movieTitles );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), movieTitles.get(position),Toast.LENGTH_LONG).show();
    }
}
