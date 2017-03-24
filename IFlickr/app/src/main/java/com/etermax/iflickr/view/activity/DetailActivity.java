/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.etermax.iflickr.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.etermax.iflickr.R;
import com.etermax.iflickr.api.ApiConfig;
import com.etermax.iflickr.modeldb.PhotoDetail;
import com.etermax.iflickr.tool.AnimTool;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    final public Activity _activity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        TextView placeOwner = (TextView) findViewById(R.id.card_owner);
        TextView placeLocation = (TextView) findViewById(R.id.card_location);
        ImageView image = (ImageView) findViewById(R.id.image);

        CircleImageView card_image_owner = (CircleImageView) findViewById(R.id.card_image_owner);

        //TODO Object

        Gson gson = new Gson();

        String json = getIntent().getExtras().getString("PhotoDetailJSON");
        PhotoDetail photo = gson.fromJson(json, PhotoDetail.class);

        placeOwner.setText(photo.getUsername());
        placeOwner.startAnimation(AnimTool.bounce(_activity));

        placeLocation.setText(photo.getLocation());
        placeLocation.startAnimation(AnimTool.bounce(_activity));

        HtmlTextView html_text = (HtmlTextView) findViewById(R.id.html_text);
        html_text.setHtml(photo.getDescription(),
                new HtmlResImageGetter(html_text));


        final String url = ApiConfig.getPhotoImageURL(photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret());

        Picasso.with(image.getContext().getApplicationContext())
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .tag("tag")
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        image.startAnimation(AnimTool.bounce(_activity));
                    }

                    @Override
                    public void onError() {
                    }
                });

        image.setOnClickListener(v -> {
            Intent intent = new Intent(_activity, ImageViewActivity.class);
            intent.putExtra("url", url);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        String url2 = ApiConfig.getBuddyIconsURL(photo.getIconfarm(), photo.getIconserver(), photo.getNsid());

        Picasso.with(card_image_owner.getContext().getApplicationContext())
                .load(url2)
                .config(Bitmap.Config.RGB_565)
                .tag("tag")
                .into(card_image_owner, new Callback() {
                    @Override
                    public void onSuccess() {
                        card_image_owner.startAnimation(AnimTool.rotate(_activity));
                    }

                    @Override
                    public void onError() {
                    }
                });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
