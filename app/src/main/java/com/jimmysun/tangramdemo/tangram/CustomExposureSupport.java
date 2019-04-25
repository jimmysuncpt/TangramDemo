package com.jimmysun.tangramdemo.tangram;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.ExposureSupport;

/**
 * 自定义曝光事件
 *
 * @author SunQiang
 * @since 2019-04-22
 */
public class CustomExposureSupport extends ExposureSupport {
    private static final String TAG = "CustomExposureSupport";

    public CustomExposureSupport() {
        setOptimizedMode(true);
    }

    @Override
    public void onExposure(@NonNull Card card, int offset, int position) {
        Log.d(TAG, "onExposure: card=" + card.getClass().getSimpleName() + ", offset=" + offset + ", position=" + position);
    }

    @Override
    public void defaultExposureCell(@NonNull View targetView, @NonNull BaseCell cell, int type) {
        Log.d(TAG, "defaultExposureCell: targetView=" + targetView.getClass().getSimpleName() + ", pos=" + cell.pos + ", type=" + type);
    }

    @Override
    public void defaultTrace(@NonNull View targetView, @NonNull BaseCell cell, int type) {
        Log.d(TAG, "defaultTrace: targetView=" + targetView.getClass().getSimpleName() + ", pos=" + cell.pos + ", type=" + type);
    }
}
