package ru.test.mininn.vkfeed.wall.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.List;

import ru.test.mininn.vkfeed.R;

public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<VKApiPhoto> photos;

    public ImagePagerAdapter(Context context,List<VKApiPhoto> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_image, collection, false);
        SimpleDraweeView image = layout.findViewById(R.id.image);
        VKApiPhoto photo = photos.get(position);
        image.setImageURI(photo.photo_604);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
        saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}