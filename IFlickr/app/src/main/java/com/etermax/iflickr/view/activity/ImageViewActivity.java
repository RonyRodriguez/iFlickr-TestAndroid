package com.etermax.iflickr.view.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.etermax.iflickr.R;
import com.etermax.iflickr.tool.AnimTool;
import com.facebook.drawee.view.DraweeView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageViewActivity extends AppCompatActivity {

    private Activity _activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ini();

    }

    public void ini() {
        String url = getIntent().getExtras().getString("url");
        DraweeView draweeView = (DraweeView) findViewById(R.id.my_image_view);

        Picasso.with(this.getApplicationContext())
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .tag("tag")
                .into(draweeView, new Callback() {
                    @Override
                    public void onSuccess() {
                        draweeView.startAnimation(AnimTool.fade_in(_activity));
                    }

                    @Override
                    public void onError() {
                    }
                });

        draweeView.setOnClickListener((view) -> this.finish());
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
