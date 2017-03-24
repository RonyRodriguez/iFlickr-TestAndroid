package com.etermax.iflickr.api;

/**
 * Created by rnet_ on 20/03/2017.
 */

import com.etermax.iflickr.apimodel.collection.PhotoArray;
import com.etermax.iflickr.apimodel.detail.PhotoObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("services/rest")
    Observable<PhotoArray> getRecent(@Query(ApiConfig.API_KEY) String apiKey,
                                     @Query(ApiConfig.FORMAT) String format,
                                     @Query(ApiConfig.NOJSONCALLBACK) String nojsoncallback,
                                     @Query(ApiConfig.PAGE) String page,
                                     @Query(ApiConfig.PER_PAGE) String perpage,
                                     @Query(ApiConfig.METHOD) String method);

    @GET("services/rest")
    Observable<PhotoArray> getSearch(@Query(ApiConfig.API_KEY) String apiKey,
                                     @Query(ApiConfig.FORMAT) String format,
                                     @Query(ApiConfig.NOJSONCALLBACK) String nojsoncallback,
                                     @Query(ApiConfig.PAGE) String page,
                                     @Query(ApiConfig.PER_PAGE) String perpage,
                                     @Query(ApiConfig.METHOD) String method,
                                     @Query(ApiConfig.TEXT) String text);

    @GET("services/rest")
    Observable<PhotoObject> getInfo(@Query(ApiConfig.API_KEY) String apiKey,
                                    @Query(ApiConfig.FORMAT) String format,
                                    @Query(ApiConfig.NOJSONCALLBACK) String nojsoncallback,
                                    @Query(ApiConfig.METHOD) String method,
                                    @Query(ApiConfig.PHOTO_ID) String photo_id,
                                    @Query(ApiConfig.SECRET) String secret);


}
