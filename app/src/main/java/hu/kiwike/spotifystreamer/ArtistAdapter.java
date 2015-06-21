package hu.kiwike.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Patrik on 2015.06.20..
 */
public class ArtistAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<MyArtist> mArtists;

    public ArtistAdapter (Context context, ArrayList<MyArtist> artists) {
        mContext = context;
        mArtists = artists;
    }

    @Override
    public int getCount() {
        return mArtists.size();
    }

    @Override
    public Object getItem(int i) {
        return mArtists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_artist, null);
            holder = new ViewHolder();
            holder.artistNameTextView = (TextView) view.findViewById(R.id.list_item_artist_name);
            holder.artistThumbnailImageView = (ImageView) view.findViewById(R.id.list_item_artist_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MyArtist artist = mArtists.get(i);
        String artistName = artist.getName();
        String pictureUrl = artist.getPictureUrl();

        holder.artistNameTextView.setText(artistName);
        Picasso.with(mContext)
                .load(pictureUrl)
                .into(holder.artistThumbnailImageView);

        return view;
    }

    private static class ViewHolder {
        ImageView artistThumbnailImageView;
        TextView artistNameTextView;
    }
}
