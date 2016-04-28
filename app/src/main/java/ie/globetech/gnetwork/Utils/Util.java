package ie.globetech.gnetwork.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ie.globetech.gnetwork.application.Consts;

/**
 * Created by Manish on 2/11/2016.
 */
public class Util {

    public static final String DATE_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_yyyy_MM_dd= "yyyy-MM-dd";
    public static final String DATE_dd_MM_yyyy = "dd-MM-yyyy";
    public static final String DATE_dd_MM_yyyy_hh_mm_ss = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_yyyy_MM_dd_hh_mm_ss2 = "yyyy-MM-ddhh:mm:ss";
    public static final String DATE_YYYY_MM_DD = "yyyy-MlM-dd"; //1970-01-01
    public static final String DATE_yyyy_MM_dd_HH_mm_ss  = "yyyy-MM-dd HH:mm:ss";  // 1970-01-01 00:00
    public static final String DATE_yyyy_MM_dd_HH_mmZ  = "yyyy-MM-dd HH:mmZ";  //  1970-01-01 00:00+0000
    public static final String DATE_yyyy_MM_dd_HH_mm_ss_SSSZ  = "yyyy-MM-dd HH:mm:ss.SSSZ"; // 1970-01-01 00:00:00.000+0000
    public static final String DATE_yyyy_MM_dd_T_HH_mm_ss_SSSZ  = "yyyy-MM-dd'T'HH:mm:ss.SSS"; // 1970-01-01T00:00:00.000+0000

    public static final String TIMESTAMP_FORMAT_LONG_DATE = "yyyyMMddHHmmss";
    private static final SimpleDateFormat longDateFormatter = new SimpleDateFormat(TIMESTAMP_FORMAT_LONG_DATE);

    public static final int INTERVAL_ONE_SECOND = 1000;
    public static final int INTERVAL_TWENTY_SECOND = 10000;
    public static final int INTERVAL_TWENTY_SECOND_NEW = 30*1000;

    public static final int INTERVAL_FIFTEEN_SECONDS = INTERVAL_ONE_SECOND * 15;
    public static final int INTERVAL_THIRTY_SECONDS = INTERVAL_ONE_SECOND * 30;

    public static final int INTERVAL_ONE_MINUTE = INTERVAL_ONE_SECOND * 60;
    public static final long INTERVAL_FIFTEEN_MINUTES = INTERVAL_ONE_MINUTE * 15;
    public static final long INTERVAL_TEN_MINNUTES = INTERVAL_ONE_MINUTE * 10;
    public static final long INTERVAL_TWENTY_MINNUTES = INTERVAL_ONE_MINUTE * 20;
    public static final long INTERVAL_FIVE_MINUTES = INTERVAL_ONE_MINUTE * 5;

    // How often to request location updates
    public static final int UPDATE_INTERVAL_IN_SECONDS = 30;
    // A fast interval ceiling
    public static final int FAST_CEILING_IN_SECONDS = 10;
    // The rate in milliseconds at which your app prefers to receive location updates
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = INTERVAL_ONE_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // A fast ceiling of update intervals, used when the app is visible
    public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = INTERVAL_ONE_SECOND * FAST_CEILING_IN_SECONDS;

    public static final String TIMESTAMP_SHORT_DATE = "dd-MM-yyyy";
    private static final SimpleDateFormat shortDateFormatter = new SimpleDateFormat(TIMESTAMP_SHORT_DATE);

    public static String getTodayShortDateString() {
        return shortDateFormatter.format(new Date());
    }

    public static String getDateFormatted(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }

    public static String getDateFormatted4(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }

    public static String getDateFormatted2(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }

    public static String getNow() {
        return longDateFormatter.format(new Date());
    }

    public static String getYesterdayDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_yyyy_MM_dd);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    public static String getYesterdayDateString2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    public static String last_monthDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_dd_MM_yyyy_hh_mm_ss);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        return dateFormat.format(cal.getTime());
    }

    public static int getAppVersion(Context context)
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    public static String getDateFormatted3(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_dd_MM_yyyy_hh_mm_ss);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String getDateFormatted(long miliseconds){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(miliseconds);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_HH_mm_ss, new Date(miliseconds)));
    }


    public static String getDateFormatted2(long miliseconds){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd);
        return sdf.format(miliseconds);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_HH_mm_ss, new Date(miliseconds)));
    }

    private static final SimpleDateFormat timeAndDateFormatter = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
    public static String getString(Timestamp timestamp) {
        return timeAndDateFormatter.format(timestamp.getTime());
    }


    public static Timestamp getCurrentTimestamp() {
        Date now = Calendar.getInstance().getTime();
        return new Timestamp(now.getTime());
    }

    public static String getHumanReadableDifference(long startTime, long endTime) {
        long timeDiff = endTime - startTime;

        return String.format("%02d:%02d:%02d",
                TimeUnit.MICROSECONDS.toHours(timeDiff),
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)),
                TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff)));
    }

    public static String getApplicationVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Should never happen
            e.printStackTrace();
            throw new RuntimeException("Could not get the app version name: " + e);
        }
    }


    public static long getAppLastUpdateTime(Context context) {
        long time=0;
        try{
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(Consts.APPLICATION_PACKAGE, 0);
            String appFile = appInfo.sourceDir;
            time = new File(appFile).lastModified();
        }catch (Exception e){
            e.printStackTrace();
        }

        return  time;
    }

    public static ApplicationInfo getAppInfo(Context context){
        ApplicationInfo app=null;
        try {
            PackageManager pm = context.getPackageManager();
             app = pm.getApplicationInfo(Consts.APPLICATION_PACKAGE, 0);
            //Drawable icon = pm.getApplicationIcon(app);
            //String name = pm.getApplicationLabel(app);
             return app;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getAppInstalledTime(Context context) {
        long time=0;
        try{
            time = context.getPackageManager().getPackageInfo(Consts.APPLICATION_PACKAGE, 0).firstInstallTime;
        }catch (Exception e){
            e.printStackTrace();
        }

        return  time;
    }

}
