package hu.kiwike.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<MyArtist> mArtist;
    SpotifyApi mSpotifyApi;
    SpotifyService mSpotifyService;
    ListView mListView;
    ArtistAdapter mArtistAdapter;
    String prevSearch;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Variables for the adapter
        mArtist = new ArrayList<MyArtist>();
        mListView = (ListView) rootView.findViewById(R.id.listview_artists);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyArtist selectedArtist = mArtist.get(i);
                Intent topSongItem = new Intent(getActivity(), TopSongs.class);
                topSongItem.putExtra(Constants.ARTIST_NAME, selectedArtist.getName());
                topSongItem.putExtra(Constants.ARTIST_ID, selectedArtist.getId());
                startActivity(topSongItem);
            }
        });

        // Searchbox functionality
        EditText editText = (EditText) rootView.findViewById(R.id.search_box);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String searchString = textView.getText().toString();
                    if (!searchString.isEmpty() && !(searchString.toLowerCase().equals(prevSearch))) {
                        if (mArtist != null) {
                            mArtist.clear();
                        }
                        prevSearch = searchString.toLowerCase();
                        FetchArtistTask fetchArtistTask = new FetchArtistTask();
                        fetchArtistTask.execute(searchString);
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public class FetchArtistTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            mSpotifyApi = new SpotifyApi();
            mSpotifyService = mSpotifyApi.getService();

            if (params != null) {
                String artist = params[0];
                ArtistsPager results =  mSpotifyService.searchArtists(artist);
                for (Artist item : results.artists.items) {
                    MyArtist newArtist;
                    try {
                        newArtist = new MyArtist(item.name, item.id, (item.images.get(0)).url);
                    } catch (Exception e) {
                        newArtist = new MyArtist(item.name, item.id);
                        Log.d(TAG, item.name + "(" + item.id + ") has no pictures");
                    }
                    mArtist.add(newArtist);
                }
                return null;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!mArtist.isEmpty()) {
                if (mListView.getAdapter() == null) {
                    mArtistAdapter = new ArtistAdapter(mListView.getContext(), mArtist);
                    mListView.setAdapter(mArtistAdapter);
                } else {
                    mArtistAdapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getActivity(), "No artist was found.\nPlease refine your query.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
