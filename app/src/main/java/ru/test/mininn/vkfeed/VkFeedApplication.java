package ru.test.mininn.vkfeed;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.vk.sdk.VKSdk;

public class VkFeedApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        Fresco.initialize(this);
    }
}
