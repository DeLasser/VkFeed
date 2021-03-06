package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;
import android.text.TextUtils;

import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKApiModel;

import org.json.JSONException;
import org.json.JSONObject;

public class VKFeedAuthor extends VKApiModel implements Identifiable, android.os.Parcelable {
    private int id;
    private String name;
    private String photo;

    private VKFeedAuthor(Parcel in) {
        id = in.readInt();
        name = in.readString();
        photo = in.readString();
    }

    private VKFeedAuthor() {
    }

    public VKFeedAuthor(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKFeedAuthor parse(JSONObject source) throws JSONException {
        if (!TextUtils.isEmpty(source.optString("first_name"))) {
            id = source.optInt("id");
            name = source.optString("first_name") + " " + source.optString("last_name");
        } else {
            name = source.optString("name");
            id = 0 - source.optInt("id");
        }
        photo = source.optString("photo_100");
        return this;
    }

    public static final Creator<VKFeedAuthor> CREATOR = new Creator<VKFeedAuthor>() {
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(photo);
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photo;
    }
}
