package com.etermax.iflickr.apimodel.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rnet_ on 21/03/2017.
 */

public class PhotoArray {

    @SerializedName("photos")
    @Expose
    private Photos photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    /**
     * No args constructor for use in serialization
     */
    public PhotoArray() {
    }

    /**
     * @param photos
     * @param stat
     */
    public PhotoArray(Photos photos, String stat) {
        super();
        this.photos = photos;
        this.stat = stat;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }


}
