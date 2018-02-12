package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;

import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKAttachments;

import org.json.JSONException;
import org.json.JSONObject;

public class VKNewsfeedItem extends VKAttachments.VKApiAttachment implements Identifiable, android.os.Parcelable {
    public int id;
    public String type;
    public int source_id;
    public int post_id;
    public long date;
    public String text;
    public int likes_count;
    public boolean user_likes;
    public boolean can_like;
    public boolean can_publish;
    public String post_type;
    public VKAttachments attachments = new VKAttachments();

    private VKNewsfeedItem(Parcel in) {
    }

    public VKNewsfeedItem(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKNewsfeedItem parse(JSONObject source) throws JSONException {
        id = source.optInt("id");
        source_id = source.optInt("source_id");
        post_id = source.optInt("post_id");
        date = source.optLong("date");
        text = source.optString("text");
        type = source.optString("type");
        JSONObject likes = source.optJSONObject("likes");
        if (likes != null) {
            likes_count = likes.optInt("count");
            user_likes = likes.optInt("user_likes", 0) == 1;
            can_like = likes.optInt("can_like", 0) == 1;
            can_publish = likes.optInt("can_publish", 0) == 1;
        }
        post_type = source.optString("post_type");
        attachments.fill(source.optJSONArray("attachments"));
        return this;
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
    public CharSequence toAttachmentString() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

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
}
