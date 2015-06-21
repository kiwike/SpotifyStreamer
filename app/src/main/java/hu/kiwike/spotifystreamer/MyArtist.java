package hu.kiwike.spotifystreamer;

/**
 * Created by Patrik on 2015.06.20..
 */
public class MyArtist {
    private String mName;
    private String mPictureUrl;
    private String mId;


    public MyArtist(String name, String id, String pictureUrl) {
        setName(name);
        setId(id);
        setPictureUrl(pictureUrl);
    }

    public MyArtist(String name, String id) {
        setName(name);
        setId(id);
        setPictureUrl(Constants.DEFAULT_THUMBNAIL_URL);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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
