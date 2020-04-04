package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.data.remote.GoogleAPIRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Photo;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungTS on 5/7/2019
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> photos = new ArrayList<>();

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PhotoViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_photo, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder photoViewHolder, int i) {
        photoViewHolder.bind(photos.get(i));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    protected class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.item_photo);

            if (getAdapterPosition() == photos.size() - 1) {
                imgPhoto.setPadding(0, 0, 0, 0);
                imgPhoto.requestLayout();
            }
        }

        void bind(Photo photo) {
            String url = GoogleAPIRemoteDataSource.getUrlPhoto(
                    CommonUtils.dpToPx(120),
                    true,
                    photo.getPhotoReference());

            GlideUtils
                    .loadImageWrapContent(
                            itemView.getContext(),
                            imgPhoto,
                            url,
                            CommonUtils.dpToPx(120)
                            );
        }
    }

}
