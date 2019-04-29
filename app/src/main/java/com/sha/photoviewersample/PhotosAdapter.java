package com.sha.photoviewersample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sha.photoviewersample.data.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.Vh>{

    private List<Image> list;

    private ItemSelected callback;

    public interface ItemSelected{
        void call(String url, int position);
    }

    public PhotosAdapter(List<Image> list, ItemSelected callback) {
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(
                LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.item_image,
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        Picasso.get()
                .load(list.get(position).url)
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Vh extends RecyclerView.ViewHolder{
        private ImageView iv;

        Vh(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(v ->
                    callback.call(list.get(getAdapterPosition()).url, getAdapterPosition())
            );
        }


    }
}
