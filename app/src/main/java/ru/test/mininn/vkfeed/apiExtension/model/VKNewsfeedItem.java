package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;

import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKAttachments;

import org.json.JSONException;
import org.json.JSONObject;

public class VKNewsfeedItem extends VKAttachments.VKApiAttachment implements Identifiable, android.os.Parcelable {
    private int id;
    private String type;
    private int sourceId;
    private int postId;
    private long date;
    private String text;
    private int likesCount;
    private boolean userLikes;
    private boolean canLike;
    private boolean canPublish;
    private String postType;
    private VKAttachments attachments = new VKAttachments();

    private VKNewsfeedItem(Parcel in) {
    }

    public VKNewsfeedItem(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKNewsfeedItem parse(JSONObject source) throws JSONException {
        id = source.optInt("id");
        sourceId = source.optInt("source_id");
        postId = source.optInt("post_id");
        date = source.optLong("date");
        text = source.optString("text");
        type = source.optString("type");
        JSONObject likes = source.optJSONObject("likes");
        if (likes != null) {
            likesCount = likes.optInt("count");
            userLikes = likes.optInt("user_likes", 0) == 1;
            canLike = likes.optInt("can_like", 0) == 1;
            canPublish = likes.optInt("can_publish", 0) == 1;
        }
        postType = source.optString("post_type");
        attachments.fill(source.optJSONArray("attachments"));
        return this;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getPostId() {
        return postId;
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public boolean isUserLikes() {
        return userLikes;
    }

    public boolean isCanLike() {
        return canLike;
    }

    public boolean isCanPublish() {
        return canPublish;
    }

    public String getPostType() {
        return postType;
    }

    public VKAttachments getAttachments() {
        return attachments;
    }

    private static final Creator<VKNewsfeedItem> CREATOR = new Creator<VKNewsfeedItem>() {
        @Override
        public VKNewsfeedItem createFromParcel(Parcel in) {
            return new VKNewsfeedItem(in);
        }

        @Override
        public VKNewsfeedItem[] newArray(int size) {
            return new VKNewsfeedItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public CharSequence toAttachmentString() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
