package ru.test.mininn.vkfeed.wall.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import ru.test.mininn.vkfeed.R;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedArray;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedItem;
import ru.test.mininn.vkfeed.wall.NewsfeedItemActivity;
import ru.test.mininn.vkfeed.wall.NewsfeedItemBinder;

public class NewsfeedViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView sourceImage;
    private TextView sourceName;
    private TextView date;
    private TextView text;
    private SimpleDraweeView imageView1;
    private SimpleDraweeView imageView2;
    private ImageView likeImage;
    private TextView likeCount;

    public NewsfeedViewHolder(View itemView) {
        super(itemView);
        sourceImage = itemView.findViewById(R.id.source_image);
        sourceName = itemView.findViewById(R.id.source_name);
        date = itemView.findViewById(R.id.date);
        text = itemView.findViewById(R.id.text);
        imageView1 = itemView.findViewById(R.id.image_view);
        imageView2 = itemView.findViewById(R.id.image_view2);
        likeImage = itemView.findViewById(R.id.like_image);
        likeCount = itemView.findViewById(R.id.like_count);
    }

    public void bind(final VKNewsfeedArray newsfeedArray, int position) {
        final VKNewsfeedItem item = newsfeedArray.get(position);
        sourceImage.setImageURI(newsfeedArray.getAuthor(item.getSourceId()).getPhotoUrl());
        sourceName.setText(newsfeedArray.getAuthor(item.getSourceId()).getName());
        likeCount.setText(String.valueOf(item.getLikesCount()));
        NewsfeedItemBinder binder = new NewsfeedItemBinder();

        binder.bindImages(item,new SimpleDraweeView[]{imageView1, imageView2});
        binder.bindText(item, newsfeedArray.getAuthor(item.getOwnerId()), text);
        binder.bindLikeImage(item, likeImage, null);
        binder.bindDate(item, date);
        setOnClick(item, newsfeedArray);

    }

    private void setOnClick (final VKNewsfeedItem item, final VKNewsfeedArray newsfeedArray) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewsfeedItemActivity.class);
                intent.putExtra("item", item);
                intent.putExtra("source", newsfeedArray.getAuthor(item.getSourceId()));
                if (item.isRepost()) {
                    intent.putExtra("author", newsfeedArray.getAuthor(item.getOwnerId()));
                }
                v.getContext().startActivity(intent);
            }
        });
    }
}
