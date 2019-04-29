package com.sha.photoviewer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RestrictTo;

import com.github.chrisbanes.photoview.PhotoView;
import com.sha.photoviewer.Options;
import com.sha.photoviewer.util.PicassoUtil;
import com.sha.photoviewer.R;

import java.util.HashSet;
import java.util.List;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PhotosAdapter extends RecyclePagerAdapter<PhotosAdapter.ImageViewHolder> {

    private List<String> data;
    private HashSet<ImageViewHolder> holders;
    private Options options;

    public PhotosAdapter(Options options) {
        this.data = options.urls;
        this.holders = new HashSet<>();
        this.options = options;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zoomable_image, parent, false);

        ImageViewHolder holder = new ImageViewHolder(v);
        holders.add(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public boolean isScaled(int index) {
        for (ImageViewHolder holder : holders) {
            if (holder.position == index) {
                return holder.isScaled;
            }
        }
        return false;
    }

   public void resetScale(int index) {
        for (ImageViewHolder holder : holders) {
            if (holder.position == index) {
                holder.resetScale();
                break;
            }
        }
    }

    public String getUrl(int index) {
        return data.get(index);
    }

    class ImageViewHolder extends ViewHolder {

        private int position = -1;
        private boolean isScaled;
        private PhotoView photoView;
        private View progressBar;

        ImageViewHolder(View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.photo_view);
            progressBar = itemView.findViewById(R.id.progressBar);
            photoView.setZoomable(options.isZoomable);
        }

        void bind(int position) {
            this.position = position;

            show(data.get(position), position);

            photoView.setOnScaleChangeListener(
                    (scaleFactor, focusX, focusY) -> isScaled = photoView.getScale() > 1.0f
            );
        }

        void resetScale() {
            photoView.setScale(1.0f, true);
        }

        private void show(String url, int position) {
            progressBar.setVisibility(View.VISIBLE);

            PicassoUtil.bitmap(
                    url,
                    photoView,
                    bitmap -> {
                        progressBar.setVisibility(View.GONE);
                        if (options.onPhotoLoadedListener != null)
                            options.onPhotoLoadedListener.onLoaded(url, position, bitmap);
                    });
        }
    }

}
