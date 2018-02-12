package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;

import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKAttachments;

import org.json.JSONException;
import org.json.JSONObject;

public class VKFeedAuthor extends VKApiModel implements Identifiable, android.os.Parcelable{
    private int id;
    private String name;
    private String photo_50;

    private VKFeedAuthor(Parcel in) {

    }

    private VKFeedAuthor() {
    }

    public VKFeedAuthor(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKFeedAuthor parse(JSONObject source) throws JSONException {
        if(!TextUtils.isEmpty(source.optString("first_name"))) {
            id = source.optInt("id");
            name = source.optString("first_name") + " " + source.optString("last_name");
        }else {
            name = source.optString("name");
            id = 0 - source.optInt("id");
            Log.d("???????????????", id + name);
        }
        photo_50 = source.optString("photo_50");
        return this;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo_50;
    }
}
