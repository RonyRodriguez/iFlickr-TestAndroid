
package com.etermax.iflickr.apimodel.collection;

import com.etermax.iflickr.apimodel.Base.PhotoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("pages")
    @Expose
    private int pages;
    @SerializedName("perpage")
    @Expose
    private int perpage;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("photo")
    @Expose
    private List<PhotoBase> photolist = null;

    /**
     * No args constructor for use in serialization
     */
    public Photos() {
    }

    /**
     * @param total
     * @param page
     * @param pages
     * @param photolist
     * @param perpage
     */
    public Photos(int page, int pages, int perpage, String total, List<PhotoBase> photolist) {
        super();
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photolist = photolist;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<PhotoBase> getPhotoList() {
        return photolist;
    }

    public void setPhotoList(List<PhotoBase> photo) {
        this.photolist = photo;
    }

}
