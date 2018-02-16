package ru.test.mininn.vkfeed.wall;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKAttachments;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.test.mininn.vkfeed.R;
import ru.test.mininn.vkfeed.apiExtension.VKApiNewsfeedLike;
import ru.test.mininn.vkfeed.apiExtension.model.VKFeedAuthor;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedItem;
import ru.test.mininn.vkfeed.wall.adapter.ImagePagerAdapter;

public class NewsfeedItemActivity extends Activity {
    private SimpleDraweeView sourceImage;
    private TextView sourceName;
    private TextView date;
    private TextView text;
    private ViewPager viewPager;
    private ImageView likeImage;
    private TextView likeCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed_item);
        initViews();
        bindItem();
    }

    private void bindItem() {
        VKFeedAuthor source = getIntent().getParcelableExtra("source");
        final VKNewsfeedItem item = getIntent().getParcelableExtra("item");
        VKFeedAuthor author = null;

        if (item.isRepost()) {
            author = getIntent().getParcelableExtra("author");
        }
        final NewsfeedItemBinder binder = new NewsfeedItemBinder();
        binder.bindDate(item, date);
        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKApiNewsfeedLike vkApiNewsfeedLike = new VKApiNewsfeedLike();
                vkApiNewsfeedLike.like(item).executeWithListener(new VKRequest.VKRequestListener() {

                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        try {
                            likeCount.setText(response.json.getJSONObject("response").getInt("likes") + "");
                            if (item.isUserLikes()){
                                item.setUserLikes(false);
                            } else {
                                item.setUserLikes(true);
                            }
                            binder.bindLikeImage(item,likeImage,null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        };
        binder.bindLikeImage(item, likeImage, onClickListener);
        binder.bindText(item, author, text);
        sourceImage.setImageURI(source.getPhotoUrl());
        likeCount.setText(String.format("%d", item.getLikesCount()));
        sourceName.setText(source.getName());
        initViewPager(item);
    }

    private void initViewPager(VKNewsfeedItem item) {
        List<VKApiPhoto> list = new ArrayList<>();
        for (VKAttachments.VKApiAttachment attachment : item.getAttachments()){
            if (attachment.getType().equals(VKAttachments.TYPE_PHOTO)){
                list.add((VKApiPhoto) attachment);
            }
        }

        ImagePagerAdapter adapter = new ImagePagerAdapter(this, list);

        viewPager.setAdapter(adapter);
    }
    private  void initViews() {
        sourceImage = findViewById(R.id.source_image);
        sourceName = findViewById(R.id.source_name);
        date = findViewById(R.id.date);
        text = findViewById(R.id.text);
        viewPager = findViewById(R.id.view_pager);
        likeImage = findViewById(R.id.like_image);
        likeCount = findViewById(R.id.like_count);

    }
}
