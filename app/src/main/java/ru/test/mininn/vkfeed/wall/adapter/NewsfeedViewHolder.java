package ru.test.mininn.vkfeed.wall.adapter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKAttachments;

import java.util.ArrayList;
import java.util.List;

import ru.test.mininn.vkfeed.R;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedArray;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedItem;

public class NewsfeedViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView sourceImage;
    private TextView sourceName;
    private TextView date;
    private TextView text;
    private ViewPager imagePager;
    private ImageView likeImage;
    private TextView likeCount;

    public NewsfeedViewHolder(View itemView) {
        super(itemView);
        sourceImage = itemView.findViewById(R.id.source_image);
        sourceName = itemView.findViewById(R.id.source_name);
        date = itemView.findViewById(R.id.date);
        text = itemView.findViewById(R.id.text);
        imagePager = itemView.findViewById(R.id.view_pager);
        likeImage = itemView.findViewById(R.id.like_image);
        likeCount = itemView.findViewById(R.id.like_count);
    }

    public void bind(VKNewsfeedArray newsfeedArrays, int position) {
        VKNewsfeedItem item = newsfeedArrays.get(position);
        sourceImage.setImageURI(newsfeedArrays.getAuthor(item.getSourceId()).getPhotoUrl());
        sourceName.setText(newsfeedArrays.getAuthor(item.getSourceId()).getName());
        text.setText(item.getText());
        likeCount.setText(String.valueOf(item.getLikesCount()));

        List<VKApiPhoto> list = new ArrayList<>();
        for (VKAttachments.VKApiAttachment attachment : item.getAttachments()){
            if (attachment.getType().equals(VKAttachments.TYPE_PHOTO)){
                list.add((VKApiPhoto) attachment);
                if(list.size() >= 2) {
                    break;
                }
            }
        }
        if (list.size() != 0) {
            ImagePagerAdapter pagerAdapter = new ImagePagerAdapter(sourceImage.getContext(), list);
            imagePager.setAdapter(pagerAdapter);
        } else {
            imagePager.setVisibility(View.GONE);
        }
    }
}
