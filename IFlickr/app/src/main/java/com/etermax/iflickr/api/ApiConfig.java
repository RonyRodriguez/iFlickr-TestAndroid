package com.etermax.iflickr.api;

public class ApiConfig {

    public static final String BASE_URL = "https://api.flickr.com/";
    public static final String API_KEY = "api_key";
    public static final String API_KEY_VAL = "4fbc9d35f4e233d41e35d25b93993121";
    public static final String FORMAT = "format";
    public static final String FORMAT_VAL = "json";
    public static final String NOJSONCALLBACK = "nojsoncallback";
    public static final String NOJSONCALLBACK_VAL = "1";
    public static final String PAGE = "page";
    public static final String PER_PAGE = "per_page";
    public static final String PER_PAGE_VAL = "80";
    //
    public static final String TEXT = "text";
    public static final String METHOD = "method";
    public static final String SECRET = "secret";
    public static final String PHOTO_ID = "photo_id";
    //

    //TODO getPublic doesn't work!!!!!!!

    public static final String GET_RECENT = "flickr.photos.getRecent";
    public static final String GET_SEARCH = "flickr.photos.search";
    public static final String GET_INFO = "flickr.photos.getInfo";
    //

    public static String getBuddyIconsURL(int icon_farm, String icon_server, String nsid) {
        return String.format("http://farm%s.staticflickr.com/%s/buddyicons/%s.jpg",
                String.valueOf(icon_farm), icon_server, nsid);
    }

    public static String getPhotoImageURL(int farm_id, String server_id, String id, String secret) {
        return String.format("http://farm%s.staticflickr.com/%s/%s_%s.jpg",
                String.valueOf(farm_id), server_id, id, secret);
    }

}
