package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;

import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

import org.json.JSONException;
import org.json.JSONObject;

public class VKNewsfeedArray extends VKList<VKNewsfeedItem> {
    @Override
    public VKNewsfeedArray parse(JSONObject response) throws JSONException {
        fill(response, VKNewsfeedItem.class);
        return this;
    }

    @SuppressWarnings("unused")
    public VKNewsfeedArray() {
    }

    @Override
    public void fill(JSONObject from, Parser<? extends VKNewsfeedItem> creator) {
        super.fill(from, creator);
    }

    public VKNewsfeedArray(Parcel in) {
        super(in);
    }

    public static Creator<VKPostArray> CREATOR = new Creator<VKPostArray>() {
        public VKPostArray createFromParcel(Parcel source) {
            return new VKPostArray(source);
        }

        public VKPostArray[] newArray(int size) {
            return new VKPostArray[size];
        }
    };
}
