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
public class TopSongsAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<MySong> mSongs;

    public TopSongsAdapter(Context context, ArrayList<MySong> songs) {
        mContext = context;
        mSongs = songs;
    }

    @Override
    public int getCount() {
        return mSongs.size();
    }

    @Override
    public Object getItem(int i) {
        return mSongs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_topsongs, null);
            holder = new ViewHolder();
            holder.songNameTextView = (TextView) view.findViewById(R.id.list_item_topsongs_title);
            holder.songThumbnailImageView = (ImageView) view.findViewById(R.id.list_item_topsongs_image);
            holder.albumNameTextView = (TextView) view.findViewById(R.id.list_item_topsongs_album);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MySong song = mSongs.get(i);
        String songName = song.getName();
        String albumName = song.getAlbum();
        String pictureUrl = song.getPictureUrl();

        holder.songNameTextView.setText(songName);
        holder.albumNameTextView.setText(albumName);
        Picasso.with(mContext)
                .load(pictureUrl)
                .into(holder.songThumbnailImageView);

        return view;
    }

    private static class ViewHolder {
        ImageView songThumbnailImageView;
        TextView songNameTextView;
        TextView albumNameTextView;
    }
}
