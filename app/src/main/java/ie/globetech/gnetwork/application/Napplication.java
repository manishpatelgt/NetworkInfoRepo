package ie.globetech.gnetwork.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Manish on 2/11/2016.
 */
public class Napplication extends Application {

    private static Napplication applicationInstance;
    private static final String TAG = "NetworkInfo";
    private static final Object lockObject = new Object();
    private static Context applicationContext = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Application singleton
        applicationInstance = this;
        applicationContext = getApplicationContext();
        preferences = applicationContext.getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

    private static SharedPreferences preferences;
    private static SharedPreferences worldPreferences;

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static SharedPreferences getPublicPreferences() {
        return worldPreferences;
    }
}
