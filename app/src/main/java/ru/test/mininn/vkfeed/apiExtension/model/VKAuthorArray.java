package ru.test.mininn.vkfeed.apiExtension.model;

import com.vk.sdk.api.model.VKList;

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
}
