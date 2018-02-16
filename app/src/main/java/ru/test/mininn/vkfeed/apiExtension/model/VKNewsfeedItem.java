package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;

import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKAttachments;

import org.json.JSONException;
import org.json.JSONObject;

public class VKNewsfeedItem extends VKAttachments.VKApiAttachment implements Identifiable, android.os.Parcelable {
    private int id;
    private String type;
    private boolean repost = false;
    private int sourceId;
    private int ownerId;
    private int postId;
    private long date;
    private String text;
    private String repostedText;
    private int likesCount;
    private boolean userLikes;
    private boolean canLike;
    private boolean canPublish;
    private String postType;
    private VKAttachments attachments = new VKAttachments();

    private VKNewsfeedItem(Parcel in) {
        id = in.readInt();
        type = in.readString();
        repost = in.readInt() > 0;
        sourceId = in.readInt();
        ownerId = in.readInt();
        postId = in.readInt();
        date = in.readLong();
        text = in.readString();
        repostedText = in.readString();
        likesCount = in.readInt();
        userLikes = in.readInt() > 0;
        canLike = in.readInt() > 0;
        canPublish = in.readInt() > 0;
        postType = in.readString();
        attachments = in.readParcelable(VKAttachments.class.getClassLoader());
    }

    public VKNewsfeedItem(JSONObject from) throws JSONException {
        parse(from);
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void setUserLikes(boolean userLikes) {
        this.userLikes = userLikes;
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
        if (source.has("copy_history")){
            JSONObject repostedSource = source.getJSONArray("copy_history").getJSONObject(0);
            repost = true;
            ownerId = repostedSource.getInt("owner_id");
            repostedText = repostedSource.getString("text");
            attachments.fill(repostedSource.optJSONArray("attachments"));
        }
        return this;
    }

    public boolean isRepost() {
        return repost;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getRepostedText() {
        return repostedText;
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

    public static final Creator<VKNewsfeedItem> CREATOR = new Creator<VKNewsfeedItem>() {
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
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeInt(repost ? 1 : 0);
        dest.writeInt(sourceId);
        dest.writeInt(ownerId);
        dest.writeInt(postId);
        dest.writeLong(date);
        dest.writeString(text);
        dest.writeString(repostedText);
        dest.writeInt(likesCount);
        dest.writeInt(userLikes ? 1 : 0);
        dest.writeInt(canLike ? 1 : 0);
        dest.writeInt(canPublish ? 1 : 0);
        dest.writeString(postType);
        dest.writeParcelable(attachments,0);

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
