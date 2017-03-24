package com.etermax.iflickr.viewmodel;

/**
 * Created by rnet_ on 20/03/2017.
 */

import android.util.Log;

import com.etermax.iflickr.api.ApiClientImp;
import com.etermax.iflickr.apimodel.Base.PhotoBase;
import com.etermax.iflickr.apimodel.collection.PhotoArray;
import com.etermax.iflickr.apimodel.detail.PhotoObject;
import com.etermax.iflickr.app.MyApplication;
import com.etermax.iflickr.modeldb.PhotoDetail;
import com.etermax.iflickr.modeldb.PhotoDetailDao;
import com.google.gson.Gson;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class PhotoViewModel {

    private Scheduler scheduler;

    public PhotoViewModel(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Observable<PhotoArray> getRecent(String page) {
        return ApiClientImp.getInstance().getRecent(page).observeOn(scheduler);
    }

    public Observable<PhotoArray> getSearch(String page, String text) {
        return ApiClientImp.getInstance().getSearch(page, text).observeOn(scheduler);
    }

    public Observable<PhotoObject> getInfo(String photo_id, String secret) {
        return ApiClientImp.getInstance().getInfo(photo_id, secret).observeOn(scheduler);
    }

    //DataBase

    public static List<PhotoBase> selectPhotos_db() {
        return MyApplication.getDaoSession().getPhotoBaseDao().loadAll();
    }

    public static Long insertPhotos_db(List<PhotoBase> movieList) {
        Long value = 0L;
        if (movieList.size() > 0) {
            MyApplication.getDaoSession().getPhotoBaseDao().deleteAll();
        }
        for (PhotoBase photo : movieList) {
            value = MyApplication.getDaoSession().getPhotoBaseDao().insert(photo);
        }
        return value;
    }

    public static PhotoDetail selectPhotoDetail_db(String id) {

        Query<PhotoDetail> query = MyApplication.getDaoSession().getPhotoDetailDao().queryBuilder().where(
                PhotoDetailDao.Properties.Id.eq(id)
        ).build();

        PhotoDetail entity = new PhotoDetail();
        try {
            List<PhotoDetail> tmp = query.list();
            if (tmp.size() > 0) {
                entity = tmp.get(0);
            }
        } catch (Exception e) {
            Log.e("DAO ", e.getMessage().toString());
        }
        return entity;
    }

    public static Long insertPhotoDetail_db(PhotoDetail entity) {
        Long value = 0L;
        return MyApplication.getDaoSession().getPhotoDetailDao().insert(entity);
    }


    public static PhotoDetail converApiObject2PhotoDatail(PhotoObject photo) {

        PhotoDetail identity = new PhotoDetail();

        identity.setId(photo.getPhoto().getId());
        identity.setFarm(photo.getPhoto().getFarm());
        identity.setSecret(photo.getPhoto().getSecret());
        identity.setServer(photo.getPhoto().getServer());

        identity.setDescription(photo.getPhoto().getDescription().getContent());

        identity.setNsid(photo.getPhoto().getOwner().getNsid());
        identity.setUsername(photo.getPhoto().getOwner().getUsername());
        identity.setIconfarm(photo.getPhoto().getOwner().getIconfarm());
        identity.setIconserver(photo.getPhoto().getOwner().getIconserver());
        identity.setLocation(photo.getPhoto().getOwner().getLocation());

        identity.setPathAlias(photo.getPhoto().getOwner().getPathAlias());

        return identity;
    }


    public static String getPhotoDetailJson(PhotoDetail entity) {
        Gson gson = new Gson();
        return gson.toJson(entity, PhotoDetail.class);

    }

}
