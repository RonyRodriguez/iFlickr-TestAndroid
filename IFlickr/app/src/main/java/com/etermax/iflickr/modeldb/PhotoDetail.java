package com.etermax.iflickr.modeldb;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by rnet_ on 20/03/2017.
 */

@Entity
public class PhotoDetail {

    @Id
    private Long _id;
    //
    private String id;
    private String secret;
    private String server;
    private int farm;
    private String description;
    //
    private String nsid;
    private String username;
    private String realname;
    private String location;
    private String iconserver;
    private int iconfarm;
    private String pathAlias;
    @Generated(hash = 674562807)
    public PhotoDetail(Long _id, String id, String secret, String server, int farm,
            String description, String nsid, String username, String realname,
            String location, String iconserver, int iconfarm, String pathAlias) {
        this._id = _id;
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.description = description;
        this.nsid = nsid;
        this.username = username;
        this.realname = realname;
        this.location = location;
        this.iconserver = iconserver;
        this.iconfarm = iconfarm;
        this.pathAlias = pathAlias;
    }
    @Generated(hash = 1090013119)
    public PhotoDetail() {
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
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getNsid() {
        return this.nsid;
    }
    public void setNsid(String nsid) {
        this.nsid = nsid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRealname() {
        return this.realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
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
    public String getPathAlias() {
        return this.pathAlias;
    }
    public void setPathAlias(String pathAlias) {
        this.pathAlias = pathAlias;
    }






}

