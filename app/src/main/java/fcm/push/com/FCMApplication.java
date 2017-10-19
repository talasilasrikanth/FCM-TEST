package fcm.push.com;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * 
 * Displays the information about the Application.
 *
 */
public class FCMApplication extends Application {

    private static final String TAG = "FCMApplication";
    public static String mToken = null;
    private RequestQueue mRequestQueue;
    private static FCMApplication mInstance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseApp.initializeApp(this);
        mToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG,"mToken :: "+mToken);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Log.d(TAG, "Subscribed to news topic");
    }

    public static synchronized FCMApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}