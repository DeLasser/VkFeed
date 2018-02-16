package ru.test.mininn.vkfeed.wall;

import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
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
import ru.test.mininn.vkfeed.apiExtension.model.VKFeedAuthor;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedItem;

public class NewsfeedItemBinder {

    public void bindDate(VKNewsfeedItem item, TextView view) {
        Date date = new Date(item.getDate() * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm");
        view.setText("" + dateFormat.format(date));
    }

    public void bindLikeImage(VKNewsfeedItem item, ImageView view, View.OnClickListener listener) {
        if (item.isUserLikes()) {
            view.setImageDrawable(view.getContext().getResources()
                    .getDrawable(R.drawable.ic_like_pressed));
        } else {
            view.setImageDrawable(view.getContext().getResources()
                    .getDrawable(R.drawable.ic_like));
        }
        if (listener != null) {
            view.setOnClickListener(listener);
        }
    }

    public void bindImages(VKNewsfeedItem item, SimpleDraweeView[] views) {
        List<VKApiPhoto> list = new ArrayList<>();
        for (VKAttachments.VKApiAttachment attachment : item.getAttachments()) {
            if (attachment.getType().equals(VKAttachments.TYPE_PHOTO)) {
                list.add((VKApiPhoto) attachment);
                if (list.size() >= 2) {
                    break;
                }
            }
        }
        switch (list.size()) {
            case 0:
                views[0].setVisibility(View.GONE);
                views[1].setVisibility(View.GONE);
                break;
            case 1:
                bindImageView(views[0], list.get(0).photo_604);
                views[1].setVisibility(View.GONE);
                break;
            case 2:
                bindImageView(views[0], list.get(0).photo_604);
                bindImageView(views[1], list.get(1).photo_604);

        }
    }

    public void bindText(VKNewsfeedItem item, VKFeedAuthor author, TextView view) {
        if (item.isRepost()) {
            view.setText(item.getText() + view.getContext().getString(R.string.reposted)
                    + author.getName() + "\n" + item.getRepostedText());
        } else {
            view.setText(item.getText());
        }
    }

    public void bindImageView(final SimpleDraweeView imageView, String imageUrl) {
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
