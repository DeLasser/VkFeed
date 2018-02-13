package ru.test.mininn.vkfeed.wall.adapter;

import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKAttachments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.test.mininn.vkfeed.R;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedArray;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedItem;

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

    public void bind(VKNewsfeedArray newsfeedArrays, int position) {
        VKNewsfeedItem item = newsfeedArrays.get(position);
        sourceImage.setImageURI(newsfeedArrays.getAuthor(item.getSourceId()).getPhotoUrl());
        sourceName.setText(newsfeedArrays.getAuthor(item.getSourceId()).getName());
        likeCount.setText(String.valueOf(item.getLikesCount()));
        bindImages(item);
        bindText(item, newsfeedArrays);
        bindLikeImage(item);
        bindDate(item);
    }

    private void bindDate(VKNewsfeedItem item) {
        Date date = new Date(item.getDate() * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm");
        this.date.setText("" + dateFormat.format(date));
    }

    private void bindLikeImage(VKNewsfeedItem item) {
        if (item.isUserLikes()) {
            likeImage.setImageDrawable(likeImage.getContext().getResources()
                    .getDrawable(R.drawable.ic_like_pressed));
        } else {
            likeImage.setImageDrawable(likeImage.getContext().getResources()
                    .getDrawable(R.drawable.ic_like));
        }
    }

    private void bindImages(VKNewsfeedItem item) {
        List<VKApiPhoto> list = new ArrayList<>();
        for (VKAttachments.VKApiAttachment attachment : item.getAttachments()){
            if (attachment.getType().equals(VKAttachments.TYPE_PHOTO)){
                list.add((VKApiPhoto) attachment);
                if(list.size() >= 2) {
                    break;
                }
            }
        }
        switch (list.size()) {
            case 0 :
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.GONE);
                break;
            case 1:
                bindImageView(imageView1, list.get(0).photo_604);
                imageView2.setVisibility(View.GONE);
                break;
            case 2:
                bindImageView(imageView1, list.get(0).photo_604);
                bindImageView(imageView2, list.get(1).photo_604);

        }
    }

    private void bindText (VKNewsfeedItem item, VKNewsfeedArray array) {
        if (item.isRepost()) {
            text.setText(item.getText() + text.getContext().getString(R.string.reposted)
                    + array.getAuthor(item.getOwnerId()).getName() + "\n" + item.getRepostedText());
        } else {
            text.setText(item.getText());
        }
    }

    private void bindImageView (final SimpleDraweeView imageView, String imageUrl) {
        if (imageUrl != null) {
            ControllerListener controllerListener = new ControllerListener() {
                @Override
                public void onSubmit(String id, Object callerContext) {

                }

                @Override
                public void onFinalImageSet(String id, @Nullable Object imageInfo, @Nullable Animatable animatable) {
                    if (imageInfo != null) {
                        ImageInfo image = (ImageInfo) imageInfo;
                        imageView.setAspectRatio((float) image.getWidth() / (float) image.getHeight());
                    }
                }

                @Override
                public void onIntermediateImageSet(String id, @Nullable Object imageInfo) {

                }

                @Override
                public void onIntermediateImageFailed(String id, Throwable throwable) {

                }

                @Override
                public void onFailure(String id, Throwable throwable) {

                }

                @Override
                public void onRelease(String id) {

                }
            };
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setControllerListener(controllerListener)
                    .setUri(imageUrl)
                    .build();
            imageView.setController(controller);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

    }
}
