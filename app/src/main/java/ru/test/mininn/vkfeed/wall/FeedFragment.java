package ru.test.mininn.vkfeed.wall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import ru.test.mininn.vkfeed.R;
import ru.test.mininn.vkfeed.apiExtension.VKApiNewsfeed;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedArray;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedItem;

public class FeedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        VKApiNewsfeed newsfeed = new VKApiNewsfeed();
        VKRequest request = newsfeed.get(VKParameters.from(VKApiConst.FILTERS,"post"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKNewsfeedArray array = new VKNewsfeedArray();
                try {
                    array = (VKNewsfeedArray) response.parsedModel;
                    for(VKNewsfeedItem item : array) {
                        log(item.text);
                    }
                } catch (Exception e) {
                    log(e.toString());
                }
                log(array.size() + " count");
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                log(error.errorReason);
                log(error.errorMessage);
                log(error.toString());
                log(error.apiError.errorMessage);
                log(error.errorCode +"");
            }
        });
        return view;
    }

    private void log(String string) {
        if (string == null) {
            return;
        }
        Log.d("ssfdsgfdsgfdhdf", string);
    }
}
