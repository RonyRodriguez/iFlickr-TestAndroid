package com.etermax.iflickr.api;

/**
 * Created by rnet_ on 20/03/2017.
 */

import com.etermax.iflickr.apimodel.collection.PhotoArray;
import com.etermax.iflickr.apimodel.detail.PhotoObject;

import io.reactivex.Observable;

public interface ApiClient {

    Observable<PhotoArray> getRecent(String page);

    Observable<PhotoArray> getSearch(String page, String text);

    Observable<PhotoObject> getInfo(String photo_id, String secret);
}
