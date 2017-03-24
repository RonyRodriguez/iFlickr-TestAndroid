package com.etermax.iflickr.apimodel.Base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by rnet_ on 21/03/2017.
 */

@Entity
public class PhotoBase {

    @Id
    private Long _id;


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private int farm;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("ispublic")
    @Expose
    private int ispublic;
    @SerializedName("isfriend")
    @Expose
    private int isfriend;
    @SerializedName("isfamily")
    @Expose
    private int isfamily;
    //
    private String username;
    private String iconserver;
    private int iconfarm;
    private String location;
    private String taken;
    @Generated(hash = 344804112)
    public PhotoBase(Long _id, String id, String owner, String secret,
            String server, int farm, String title, int ispublic, int isfriend,
            int isfamily, String username, String iconserver, int iconfarm,
            String location, String taken) {
        this._id = _id;
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.ispublic = ispublic;
        this.isfriend = isfriend;
        this.isfamily = isfamily;
        this.username = username;
        this.iconserver = iconserver;
        this.iconfarm = iconfarm;
        this.location = location;
        this.taken = taken;
    }
    @Generated(hash = 1625021277)
    public PhotoBase() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOwner() {
        return this.owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getSecret() {
        return this.secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public String getServer() {
        return this.server;
    }
    public void setServer(String server) {
        this.server = server;
    }
    public int getFarm() {
        return this.farm;
    }
    public void setFarm(int farm) {
        this.farm = farm;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getIspublic() {
        return this.ispublic;
    }
    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }
    public int getIsfriend() {
        return this.isfriend;
    }
    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }
    public int getIsfamily() {
        return this.isfamily;
    }
    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getIconserver() {
        return this.iconserver;
    }
    public void setIconserver(String iconserver) {
        this.iconserver = iconserver;
    }
    public int getIconfarm() {
        return this.iconfarm;
    }
    public void setIconfarm(int iconfarm) {
        this.iconfarm = iconfarm;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getTaken() {
        return this.taken;
    }
    public void setTaken(String taken) {
        this.taken = taken;
    }
   

    



}
