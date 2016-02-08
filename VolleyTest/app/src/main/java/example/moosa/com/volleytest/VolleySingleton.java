package example.moosa.com.volleytest;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Moosa on 5/23/2015.
 */
public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;


    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(MyApplication.getContext());

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().totalMemory() / 1024) / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getmInstance() {

        if (mInstance == null) {

            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
