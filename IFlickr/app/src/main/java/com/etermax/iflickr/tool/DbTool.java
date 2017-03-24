package com.etermax.iflickr.tool;

import com.etermax.iflickr.apimodel.Base.PhotoBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnet_ on 21/03/2017.
 */

public class DbTool {

    public static List<PhotoBase> findPhoto(List<PhotoBase> list, String text) {
        List<PhotoBase> lm = new ArrayList<>();
        text = text.toLowerCase();
        for (PhotoBase movie : list) {
            if (movie.getTitle().toLowerCase().contains(text)) {
                lm.add(movie);
            }
        }
        return lm;
    }

}
