
package com.etermax.iflickr.apimodel.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoObject {

    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("stat")
    @Expose
    private String stat;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
