package ru.test.mininn.vkfeed.apiExtension;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.methods.VKApiBase;

import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedArray;

public class VKApiNewsfeed extends VKApiBase{

    public VKRequest get(VKParameters params) {
            return prepareRequest("get", params, VKNewsfeedArray.class);
    }

    @Override
    protected String getMethodsGroup() {
        return "newsfeed";
    }
}
