package ru.test.mininn.vkfeed.apiExtension;

import android.util.Log;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.methods.VKApiBase;

import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedArray;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedItem;

public class VKApiNewsfeedLike extends VKApiBase {

    public VKRequest like(VKNewsfeedItem item) {
        VKParameters parameters = VKParameters.from("type", "post", "owner_id", item.getSourceId(), "item_id", item.getPostId());
        if (!item.isUserLikes()) {
            return prepareRequest("add", parameters, VKNewsfeedArray.class);
        } else {
            return prepareRequest("delete", parameters, VKNewsfeedArray.class);
        }
    }

    @Override
    protected String getMethodsGroup() {
        return "likes";
    }
}
