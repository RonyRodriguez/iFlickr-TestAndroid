
package com.etermax.iflickr.apimodel.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class People {

    @SerializedName("haspeople")
    @Expose
    private int haspeople;

    public int getHaspeople() {
        return haspeople;
    }

    public void setHaspeople(int haspeople) {
        this.haspeople = haspeople;
    }

}
