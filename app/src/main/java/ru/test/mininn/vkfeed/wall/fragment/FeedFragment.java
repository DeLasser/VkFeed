package ru.test.mininn.vkfeed.wall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isPaginationTimeout = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        initViews(view);
        initPagination();
        bindData();
        updateNewsfeed(true);
        return view;
    }

    private void bindData() {
        adapter = new NewsfeedAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateNewsfeed(true);
            }
        });
    }

    private void initPagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition >= adapter.getItemCount() - 10 && !isPaginationTimeout) {
                    updateNewsfeed(false);
                }
            }
        });
    }

    private void updateNewsfeed(boolean refresh) {
        VKApiNewsfeed newsfeed = new VKApiNewsfeed();
        VKParameters parameters;
        isPaginationTimeout = true;
        if (refresh) {
            parameters = VKParameters.from(VKParameters.from(VKApiConst.FILTERS, "post"));
            adapter.clear();
        } else {
            parameters = VKParameters.from(VKParameters.from(VKApiConst.FILTERS, "post",
                    "start_from", adapter.getNextFrom()));
        }
        VKRequest request = newsfeed.get(parameters);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKNewsfeedArray array = (VKNewsfeedArray) response.parsedModel;
                adapter.add(array);
                adapter.notifyDataSetChanged();
                isPaginationTimeout = false;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }
}
