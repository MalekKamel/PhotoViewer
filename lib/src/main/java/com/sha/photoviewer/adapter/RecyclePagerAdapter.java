package com.sha.photoviewer.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;


@RestrictTo(RestrictTo.Scope.LIBRARY)
public abstract class RecyclePagerAdapter<VH extends ViewHolder> extends PagerAdapter {

    public abstract int getItemCount();

    public abstract void onBindViewHolder(VH holder, int position);

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    private static final String STATE = RecyclePagerAdapter.class.getSimpleName();

    private SparseArray<RecycleCache> mRecycleTypeCaches = new SparseArray<>();

    private SparseArray<Parcelable> mSavedStates = new SparseArray<>();

    public RecyclePagerAdapter() {
    }

    @Override
    public void destroyItem(@NonNull ViewGroup parent, int position, @NonNull Object object) {
        if (object instanceof ViewHolder) {
            ((ViewHolder) object).detach(parent);
        }
    }

    @Override
    public int getCount() {
        return getItemCount();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public Object instantiateItem(@NonNull ViewGroup parent, int position) {
        int viewType = getItemViewType(position);
        if (mRecycleTypeCaches.get(viewType) == null) {
            mRecycleTypeCaches.put(viewType, new RecycleCache(this));
        }
        ViewHolder viewHolder = mRecycleTypeCaches.get(viewType).getFreeViewHolder(parent, viewType);
        viewHolder.attach(parent, position);
        onBindViewHolder((VH) viewHolder, position);
        viewHolder.onRestoreInstanceState(mSavedStates.get(getItemId(position)));
        return viewHolder;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object instanceof ViewHolder && ((ViewHolder) object).itemView == view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (ViewHolder viewHolder : getAttachedViewHolders()) {
            onNotifyItemChanged(viewHolder);
        }
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            SparseArray<Parcelable> ss = bundle.containsKey(STATE) ? bundle.getSparseParcelableArray(STATE) : null;
            mSavedStates = ss != null ? ss : new SparseArray<>();
        }
        super.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle = new Bundle();
        for (ViewHolder viewHolder : getAttachedViewHolders()) {
            mSavedStates.put(getItemId(viewHolder.mPosition), viewHolder.onSaveInstanceState());
        }
        bundle.putSparseParcelableArray(STATE, mSavedStates);
        return bundle;
    }

    public int getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    protected void onNotifyItemChanged(ViewHolder viewHolder) {
    }

    private List<ViewHolder> getAttachedViewHolders() {
        List<ViewHolder> attachedViewHolders = new ArrayList<>();
        int n = mRecycleTypeCaches.size();
        for (int i = 0; i < n; i++) {
            for (ViewHolder viewHolder : mRecycleTypeCaches.get(mRecycleTypeCaches.keyAt(i)).mCaches) {
                if (viewHolder.mIsAttached) {
                    attachedViewHolders.add(viewHolder);
                }
            }
        }
        return attachedViewHolders;
    }

    private class RecycleCache {

        private final RecyclePagerAdapter mAdapter;

        private final List<ViewHolder> mCaches;

        RecycleCache(RecyclePagerAdapter adapter) {
            mAdapter = adapter;
            mCaches = new ArrayList<>();
        }

        ViewHolder getFreeViewHolder(ViewGroup parent, int viewType) {
            int i = 0;
            ViewHolder viewHolder;
            for (int n = mCaches.size(); i < n; i++) {
                viewHolder = mCaches.get(i);
                if (!viewHolder.mIsAttached) {
                    return viewHolder;
                }
            }
            viewHolder = mAdapter.onCreateViewHolder(parent, viewType);
            mCaches.add(viewHolder);
            return viewHolder;
        }
    }
}