package com.etermax.iflickr.api;

/**
 * Created by rnet_ on 20/03/2017.
 */

import com.etermax.iflickr.apimodel.collection.PhotoArray;
import com.etermax.iflickr.apimodel.detail.PhotoObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientImp implements ApiClient {

    private ApiService service;

    private static ApiClientImp _instance;

    public static ApiClientImp getInstance() {
        if (_instance == null) {
            _instance = new ApiClientImp();
        }
        return _instance;
    }

    private ApiClientImp() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConfig.BASE_URL)
                .build();
        service = retrofit.create(ApiService.class);
    }

    @Override
    public Observable<PhotoArray> getRecent(String page) {
        return service.getRecent(
                ApiConfig.API_KEY_VAL,
                ApiConfig.FORMAT_VAL,
                ApiConfig.NOJSONCALLBACK_VAL,
                page,
                ApiConfig.PER_PAGE_VAL,
                ApiConfig.GET_RECENT).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<PhotoArray> getSearch(String page, String text) {
        return service.getSearch(
                ApiConfig.API_KEY_VAL,
                ApiConfig.FORMAT_VAL,
                ApiConfig.NOJSONCALLBACK_VAL,
                page,
                ApiConfig.PER_PAGE_VAL,
                ApiConfig.GET_SEARCH,
                text)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<PhotoObject> getInfo(String photo_id, String secret) {
        return service.getInfo(
                ApiConfig.API_KEY_VAL,
                ApiConfig.FORMAT_VAL,
                ApiConfig.NOJSONCALLBACK_VAL,
                ApiConfig.GET_INFO,
                photo_id, secret)
                .subscribeOn(Schedulers.io());
    }

}
