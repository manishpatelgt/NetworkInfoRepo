package ie.globetech.gnetwork.data;

import android.content.SharedPreferences;

import ie.globetech.gnetwork.application.Napplication;

/**
 * Created by Manish on 2/11/2016.
 */
public class PreferencesHelper {

    private static final String PREF_KEY_TOTAL_USAGE = "TotalUsage";
    private static final String PREF_KEY_TOTAL_USAGE2 = "TotalUsage2";

    public static long getLong(String key, long defValue) {
        return Napplication.getPreferences().getLong(key, defValue);
    }

    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = Napplication.getPreferences().edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static long getLastTotalUsage2() {
        return getLong(PREF_KEY_TOTAL_USAGE2, Long.parseLong("0"));
    }

    public static void setLastTotalUsage2(long usage) {
        putLong(PREF_KEY_TOTAL_USAGE2, usage);
    }
    public static long getLastTotalUsage() {
        return getLong(PREF_KEY_TOTAL_USAGE, Long.parseLong("0"));
    }

    public static void setLastTotalUsage(long usage) {
        putLong(PREF_KEY_TOTAL_USAGE, usage);
    }

}
