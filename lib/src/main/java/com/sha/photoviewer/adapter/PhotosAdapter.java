package com.sha.photoviewer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.RestrictTo;

import com.github.chrisbanes.photoview.PhotoView;
import com.sha.photoviewer.Options;
import com.sha.photoviewer.R;

import java.util.List;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PhotosAdapter extends RecyclePagerAdapter<PhotosAdapter.ImageViewHolder> {

    private List<Photo> list;
    private Options options;

    public PhotosAdapter(Options options) {
        this.list = Photo.from(options.urls);
        this.options = options;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zoomable_image, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public boolean isScaled(int index) {
        return list.get(index).isScaled;
    }

    public void resetScale(int index) {
        list.get(index).view.setScale(1.0f, true);
    }

    public String getUrl(int index) {
        return list.get(index).url;
    }

    class ImageViewHolder extends ViewHolder {
        private PhotoView photoView;
        private ProgressBar progressBar;

        ImageViewHolder(View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.photo_view);
            progressBar = itemView.findViewById(R.id.progressBar);
            photoView.setZoomable(options.isZoomable);

            photoView.setOnLongClickListener(options.onLongClickListener);
        }

        void bind(int position) {
            Photo item = list.get(position);
            item.view = photoView;
            photoView.setImageDrawable(null);

            show(item.url, position);

            photoView.setOnScaleChangeListener(
                    (scaleFactor, focusX, focusY) -> item.isScaled = scaleFactor > 1.0f
            );
        }

        private void show(String url, int position) {
            progressBar.setVisibility(View.VISIBLE);

            options.imageLoader.load(url, photoView, position, progressBar);

        }
    }

}
