package ru.test.mininn.vkfeed.wall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import ru.test.mininn.vkfeed.R;
import ru.test.mininn.vkfeed.apiExtension.VKApiNewsfeed;
import ru.test.mininn.vkfeed.apiExtension.model.VKNewsfeedArray;
import ru.test.mininn.vkfeed.wall.adapter.NewsfeedAdapter;

public class FeedFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsfeedAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new NewsfeedAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        updateNewsfeed();
        return view;
    }

    private void updateNewsfeed() {
        VKApiNewsfeed newsfeed = new VKApiNewsfeed();
        VKRequest request = newsfeed.get(VKParameters.from(VKApiConst.FILTERS, "post"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKNewsfeedArray array = (VKNewsfeedArray) response.parsedModel;
                adapter.add(array);
                adapter.notifyDataSetChanged();
//                recyclerView.invalidate();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }
}
