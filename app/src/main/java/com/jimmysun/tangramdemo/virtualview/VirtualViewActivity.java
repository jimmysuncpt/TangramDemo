package com.jimmysun.tangramdemo.virtualview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.jimmysun.tangramdemo.R;
import com.jimmysun.tangramdemo.Utils;
import com.tmall.wireless.vaf.framework.VafContext;
import com.tmall.wireless.vaf.framework.ViewManager;
import com.tmall.wireless.vaf.virtualview.Helper.ImageLoader;
import com.tmall.wireless.vaf.virtualview.core.IContainer;
import com.tmall.wireless.vaf.virtualview.event.EventData;
import com.tmall.wireless.vaf.virtualview.event.EventManager;
import com.tmall.wireless.vaf.virtualview.event.IEventProcessor;
import com.tmall.wireless.vaf.virtualview.view.image.ImageBase;

import org.json.JSONObject;

public class VirtualViewActivity extends AppCompatActivity {
    private static final String TAG = "VirtualViewActivity";

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_view);
        mLinearLayout = findViewById(R.id.layout);
        initVirtualView();
    }

    private void initVirtualView() {
        VafContext vafContext = new VafContext(getApplicationContext());
        vafContext.setImageLoaderAdapter(new ImageLoader.IImageLoaderAdapter() {
            @Override
            public void bindImage(String uri, ImageBase imageBase, int reqWidth, int reqHeight) {
                if (Utils.isValidContextForGlide(VirtualViewActivity.this)) {
                    RequestBuilder requestBuilder =
                            Glide.with(VirtualViewActivity.this).asBitmap().load(uri);
                    if (reqWidth > 0 || reqHeight > 0) {
                        requestBuilder.submit(reqWidth, reqHeight);
                    }
                    ImageTarget imageTarget = new ImageTarget(imageBase);
                    requestBuilder.into(imageTarget);
                }
            }

            @Override
            public void getBitmap(String uri, int reqWidth, int reqHeight,
                                  ImageLoader.Listener lis) {
                if (Utils.isValidContextForGlide(VirtualViewActivity.this)) {
                    RequestBuilder requestBuilder =
                            Glide.with(VirtualViewActivity.this).asBitmap().load(uri);
                    if (reqWidth > 0 || reqHeight > 0) {
                        requestBuilder.submit(reqWidth, reqHeight);
                    }
                    ImageTarget imageTarget = new ImageTarget(lis);
                    requestBuilder.into(imageTarget);
                }
            }
        });
        ViewManager viewManager = vafContext.getViewManager();
        viewManager.init(getApplicationContext());
//        viewManager.loadBinBufferSync(VVTEST.BIN);
//        viewManager.loadBinFileSync("file:///android_asset/VVTest.out");
        viewManager.loadBinBufferSync(Utils.getAssetsFile(this, "VVTest.out"));
        viewManager.loadBinBufferSync(Utils.getAssetsFile(this, "ViewPager.out"));
        vafContext.getEventManager().register(EventManager.TYPE_Click, new IEventProcessor() {
            @Override
            public boolean process(EventData data) {
                Toast.makeText(VirtualViewActivity.this, data.mVB.getAction(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        vafContext.getEventManager().register(EventManager.TYPE_Exposure, new IEventProcessor() {
            @Override
            public boolean process(EventData data) {
                Log.d(TAG, "Exposure process: " + data.mVB.getViewCache().getComponentData());
                return true;
            }
        });
        // VVTest
        View container = vafContext.getContainerService().getContainer("VVTest", true);
        mLinearLayout.addView(container);
        IContainer iContainer = (IContainer) container;
        JSONObject jsonObject = Utils.getJSONDataFromAsset(this, "vvtest.json");
        if (jsonObject != null) {
            iContainer.getVirtualView().setVData(jsonObject);
        }
        // ViewPager
        container = vafContext.getContainerService().getContainer("ViewPager", true);
        mLinearLayout.addView(container);
        iContainer = (IContainer) container;
        jsonObject = Utils.getJSONDataFromAsset(this, "view_pager.json");
        if (jsonObject != null) {
            iContainer.getVirtualView().setVData(jsonObject);
        }
    }
}
