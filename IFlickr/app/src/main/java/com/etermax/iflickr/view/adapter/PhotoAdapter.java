package com.etermax.iflickr.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etermax.iflickr.R;
import com.etermax.iflickr.api.ApiConfig;
import com.etermax.iflickr.apimodel.Base.PhotoBase;
import com.etermax.iflickr.tool.AnimTool;
import com.etermax.iflickr.view.activity.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rnet_ on 20/03/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private final boolean isCard;
    private List<PhotoBase> list;
    private Context context;
    private final MainActivity activity;


    public PhotoAdapter(Context context, List<PhotoBase> list, boolean isCard) {
        this.list = list;
        this.context = context;
        activity = (MainActivity) context;
        this.isCard = isCard;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView tile_picture;
        //
        public ImageView card_image;
        public CircleImageView card_image_owner;
        public TextView card_owner;
        public TextView card_location;

        public MyViewHolder(View view) {
            super(view);
            if (isCard) {
                card_image = (ImageView) view.findViewById(R.id.card_image);
                card_image_owner = (CircleImageView) view.findViewById(R.id.card_image_owner);
                card_owner = (TextView) view.findViewById(R.id.card_owner);
                card_location = (TextView) view.findViewById(R.id.card_location);
            } else {
                tile_picture = (ImageView) view.findViewById(R.id.tile_picture);
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;


        if (isCard) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tile, parent, false);
        }

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(PhotoAdapter.MyViewHolder holder, int position) {


        final PhotoBase photo = list.get(position);

        String url;

        if (isCard) {

            holder.card_owner.setText(photo.getUsername());
            holder.card_owner.startAnimation(AnimTool.bounce(context));

            holder.card_location.setText(photo.getLocation());
            holder.card_owner.startAnimation(AnimTool.bounce(context));

            url = ApiConfig.getBuddyIconsURL(photo.getIconfarm(), photo.getIconserver(), photo.getOwner());

            Picasso.with(holder.card_image_owner.getContext().getApplicationContext())
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .tag("tag")
                    .into(holder.card_image_owner, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.card_image_owner.startAnimation(AnimTool.rotate(context));
                        }

                        @Override
                        public void onError() {

                        }
                    });

            url = ApiConfig.getPhotoImageURL(photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret());

            Picasso.with(holder.card_image.getContext().getApplicationContext())
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .tag("tag")
                    .into(holder.card_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.card_image.startAnimation(AnimTool.bounce(context));
                        }

                        @Override
                        public void onError() {

                        }
                    });

            holder.card_image.setOnClickListener(v -> {
                activity.onclick_photoDetaiil(photo.getId(), photo.getSecret());
            });

        } else {

            url = ApiConfig.getPhotoImageURL(photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret());

            Picasso.with(holder.tile_picture.getContext().getApplicationContext())
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .tag("tag")
                    .into(holder.tile_picture, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.tile_picture.startAnimation(AnimTool.fade_in(context));
                        }

                        @Override
                        public void onError() {
                        }
                    });

            holder.tile_picture.setOnClickListener(v -> {

                activity.onclick_photoDetaiil(photo.getId(), photo.getSecret());

            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}