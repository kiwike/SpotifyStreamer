package hu.kiwike.spotifystreamer;

/**
 * Created by Patrik on 2015.06.20..
 */
public class MySong {
    private String mName;
    private String mAlbum;
    private String mPictureUrl;
    private String mId;

    public MySong(String name, String id, String album, String pictureUrl) {
        setName(name);
        setId(id);
        setAlbum(album);
        setPictureUrl(pictureUrl);
    }

    public MySong(String name, String id, String album) {
        setName(name);
        setId(id);
        setAlbum(album);
        setPictureUrl(Constants.DEFAULT_THUMBNAIL_URL);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
