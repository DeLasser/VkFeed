package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;

import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

import org.json.JSONException;
import org.json.JSONObject;

public class VKNewsfeedArray extends VKList<VKNewsfeedItem> {
    private VKAuthorArray authors;

    public VKFeedAuthor getAuthor(int sourceId) {
        for (VKFeedAuthor author : authors) {
            if (author.getId() == sourceId) {
                return author;
            }
        }
        return null;
    }

    @Override
    public void fill(JSONObject from, Class<? extends VKNewsfeedItem> clazz) {
        super.fill(from, clazz);
    }

    @Override
    public VKNewsfeedArray parse(JSONObject response) throws JSONException {
        fill(response, VKNewsfeedItem.class);
        authors = new VKAuthorArray();
        try {
            authors = authors.parse(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public VKNewsfeedArray(Parcel in) {
        super(in);
    }

    public VKNewsfeedArray() {
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
