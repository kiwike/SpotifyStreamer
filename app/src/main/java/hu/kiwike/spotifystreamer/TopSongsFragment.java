package hu.kiwike.spotifystreamer;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopSongsFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    String artistID;
    String artistName;
    SpotifyApi mSpotifyApi;
    SpotifyService mSpotifyService;
    ListView mListView;
    ArrayList<MySong> mTopSongs;
    TopSongsAdapter mTopSongsAdapter;

    public TopSongsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_songs, container, false);

        artistID = getActivity().getIntent().getStringExtra(Constants.ARTIST_ID);
        artistName = getActivity().getIntent().getStringExtra(Constants.ARTIST_NAME);

        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setSubtitle(artistName);

        mTopSongs = new ArrayList<MySong>();
        mListView = (ListView) rootView.findViewById(R.id.listview_topsongs);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mTopSongs.clear();
        FetchSongTask fetchSongTask = new FetchSongTask();
        fetchSongTask.execute();
    }

    public class FetchSongTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mSpotifyApi = new SpotifyApi();
            mSpotifyService = mSpotifyApi.getService();

            Map param = new HashMap<String, String>();
            param.put("country", "HU");
            Tracks artistTopTracks = mSpotifyService.getArtistTopTrack(artistID, param);
            for (Track item : artistTopTracks.tracks) {
                Log.d(TAG, item.name + " - " + item.album.name);
                MySong newSong;
                try {
                    newSong = new MySong(item.name,
                            item.id,
                            item.album.name,
                            item.album.images.get(0).url);
                } catch (Exception e) {
                    newSong = new MySong(item.name,
                            item.id,
                            item.album.name);
                }
                mTopSongs.add(newSong);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mTopSongs != null) {
                if (mTopSongsAdapter == null) {
                    mTopSongsAdapter = new TopSongsAdapter(getActivity(), mTopSongs);
                    mListView.setAdapter(mTopSongsAdapter);
                } else {
                    mTopSongsAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
