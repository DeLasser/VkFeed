package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;

import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

import org.json.JSONException;
import org.json.JSONObject;

public class VKNewsfeedArray extends VKList<VKNewsfeedItem> {
    private VKAuthorArray authors;
    private String nextFrom;

    public VKFeedAuthor getAuthor(int sourceId) {
        for (VKFeedAuthor author : authors) {
            if (author.getId() == sourceId) {
                return author;
            }
        }
        return null;
    }


    private VKAuthorArray getAllAuthors() {
        return authors;
    }

    public void setNextFrom(String nextFrom) {
        this.nextFrom = nextFrom;
    }

    @Override
    public void fill(JSONObject from, Class<? extends VKNewsfeedItem> clazz) {
        super.fill(from, clazz);
    }

    @Override
    public VKNewsfeedArray parse(JSONObject response) throws JSONException {
        fill(response, VKNewsfeedItem.class);
        authors = new VKAuthorArray();
        authors = authors.parse(response);
        fillNextFrom(response);
        return this;
    }

    private void fillNextFrom(JSONObject response) {
        if (response.has("response")) {
            JSONObject object = response.optJSONObject("response");
            nextFrom = object.optString("next_from");
        }
    }

    public boolean addAll(VKNewsfeedArray vkNewsfeedArray) {
        if (authors == null) {
            authors = new VKAuthorArray();
        }
        authors.addAll(vkNewsfeedArray.getAllAuthors());
        this.setNextFrom(vkNewsfeedArray.getNextFrom());
        this.getNextFrom();
        return super.addAll(vkNewsfeedArray);
    }

    public String getNextFrom() {
        return nextFrom;
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
