package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;

import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKAttachments;

import org.json.JSONException;
import org.json.JSONObject;

public class VKFeedAuthor extends VKAttachments.VKApiAttachment implements Identifiable, android.os.Parcelable{
    private String name;
    private String photo_50;

    private VKFeedAuthor(Parcel in) {

    }

    public VKFeedAuthor parse(JSONObject source) throws JSONException {
        if(source.optString("first_name") != null) {
            name = source.optString("first_name") + " " + source.optString("last_name");
        }else {
            name = source.optString("name");
        }
        photo_50 = source.optString("photo_50");
        return this;
    }

    @Override
    public CharSequence toAttachmentString() {
        return null;
    }

    private static final Creator<VKFeedAuthor> CREATOR = new Creator<VKFeedAuthor>() {
        @Override
        public VKFeedAuthor createFromParcel(Parcel in) {
            return new VKFeedAuthor(in);
        }

        @Override
        public VKFeedAuthor[] newArray(int size) {
            return new VKFeedAuthor[size];
        }
    };

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
