package ru.test.mininn.vkfeed.apiExtension.model;

import android.util.Log;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VKAuthorArray extends VKList<VKFeedAuthor> {
    private final String GROUPS = "groups";
    private final String PROFILES = "profiles";

    @Override
    public VKAuthorArray parse(JSONObject response) throws JSONException {
        fill(response, VKFeedAuthor.class);
        return this;
    }

    @Override
    public void fill(JSONObject from, Parser<? extends VKFeedAuthor> creator) {
        if (from != null) {
            fill(from.optJSONArray(GROUPS), creator);
            fill(from.optJSONArray(PROFILES), creator);
        }
    }

    @Override
    public void fill(JSONArray from, Parser<? extends VKFeedAuthor> creator) {
        if (from != null) {
            Log.d("///////////////", from.length() + " ");
            for (int i = 0; i < from.length(); i++) {
                try {
                    VKFeedAuthor object = creator.parseObject(from.getJSONObject(i));
                    if (object != null) {
                        this.add(object);
                        Log.d("///////////////", object.getName());
                    }
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }
        }
    }
}
