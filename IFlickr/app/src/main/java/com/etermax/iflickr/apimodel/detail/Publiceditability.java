
package com.etermax.iflickr.apimodel.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Publiceditability {

    @SerializedName("cancomment")
    @Expose
    private int cancomment;
    @SerializedName("canaddmeta")
    @Expose
    private int canaddmeta;

    public int getCancomment() {
        return cancomment;
    }

    public void setCancomment(int cancomment) {
        this.cancomment = cancomment;
    }

    public int getCanaddmeta() {
        return canaddmeta;
    }

    public void setCanaddmeta(int canaddmeta) {
        this.canaddmeta = canaddmeta;
    }

}
