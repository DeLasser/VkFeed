package ru.test.mininn.vkfeed.wall.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.test.mininn.vkfeed.R;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedArray;

public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedViewHolder> {

    private VKNewsfeedArray newsfeedArray;

    public NewsfeedAdapter() {
        this.newsfeedArray = new VKNewsfeedArray();
    }

    public void add(VKNewsfeedArray vkNewsfeedArray) {
        this.newsfeedArray.addAll(vkNewsfeedArray);
    }

    @Override
    public NewsfeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_newsfeed, parent, false);
        return new NewsfeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsfeedViewHolder holder, int position) {
        holder.bind(newsfeedArray, position);
    }

    @Override
    public int getItemCount() {
        return newsfeedArray.size();
    }

    public String getNextFrom() {
        return newsfeedArray.getNextFrom();
    }

    public void clear() {
        newsfeedArray.clear();
        notifyDataSetChanged();
    }
}
