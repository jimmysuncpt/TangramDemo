package com.jimmysun.tangramdemo.virtualview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tmall.wireless.vaf.virtualview.Helper.ImageLoader;
import com.tmall.wireless.vaf.virtualview.view.image.ImageBase;

/**
 * 封装的Glide Target
 *
 * @author SunQiang
 * @since 2019-04-24
 */
public class ImageTarget extends SimpleTarget<Bitmap> {
    ImageBase mImageBase;
    ImageLoader.Listener mListener;

    public ImageTarget(ImageBase imageBase) {
        mImageBase = imageBase;
    }

    public ImageTarget(ImageLoader.Listener listener) {
        mListener = listener;
    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource,
                                @Nullable Transition<? super Bitmap> transition) {
        mImageBase.setBitmap(resource, true);
        if (mListener != null) {
            mListener.onImageLoadSuccess(resource);
        }
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        if (mListener != null) {
            mListener.onImageLoadFailed();
        }
    }
}
