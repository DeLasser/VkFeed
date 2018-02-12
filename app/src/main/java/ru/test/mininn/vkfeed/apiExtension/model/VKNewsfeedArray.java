package ru.test.mininn.vkfeed.apiExtension.model;

import android.os.Parcel;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class VKNewsfeedArray extends VKList<VKNewsfeedItem> {
    private final String GROPS = "groups";
    private final String PROFILES = "profiles";
    private Map<Integer, VKFeedAuthor> authors;

    public VKFeedAuthor getFeefauthor (int sourceId) {
        return authors.get(sourceId);
    }

    @Override
    public VKNewsfeedArray parse(JSONObject response) throws JSONException {
        fill(response, VKNewsfeedItem.class);
        return this;
    }

    @Override
    public void fill(JSONObject from, Class<? extends VKNewsfeedItem> clazz) {
        super.fill(from, clazz);
        fillMap(from, VKFeedAuthor.class);
    }

    @Override
    public void fill(JSONArray from, Parser<? extends VKNewsfeedItem> creator) {
        super.fill(from, creator);
    }

    private void fillAuthors(JSONArray from, Parser<? extends VKFeedAuthor> creator, String jsonArrayTag) {
        if(from != null) {
            for(int i = 0; i < from.length(); i++) {
                try {
                    JSONObject jsonObject = from.getJSONObject(i);
                    VKFeedAuthor object = creator.parseObject(from.getJSONObject(i));
                    if(object != null) {
                        if (jsonArrayTag == GROPS) {
                            authors.put(jsonObject.getInt("id") * (-1), object);
                        } else {
                            authors.put(jsonObject.getInt("id"), object);
                        }
                    }
                } catch (Exception e) {
                    if (VKSdk.DEBUG)
                        e.printStackTrace();
                }
            }
        }
    }

    private void fillMap(JSONObject from, Class<? extends VKFeedAuthor> creator) {
        if(from != null) {
            fillAuthors(from.optJSONArray(GROPS), new ReflectParser<VKFeedAuthor>(creator), GROPS);
            fillAuthors(from.optJSONArray(PROFILES), new ReflectParser<VKFeedAuthor>(creator), PROFILES);
        }
    }

    @SuppressWarnings("unused")
    public VKNewsfeedArray() {
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
