package ie.globetech.gnetwork.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ie.globetech.gnetwork.R;
import ie.globetech.gnetwork.Utils.Util;
import ie.globetech.gnetwork.data.PreferencesHelper;
import ie.globetech.gnetwork.services.AccessService;
import ie.globetech.gnetwork.services.CDUSSDService;

public class MainActivity extends AppCompatActivity {

    private static final Logger logger = LoggerFactory.getLogger("c*.e*.a*.MainActi*");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private TextView textVersion,textUpdateTime,textinstalledTime,textDataUsage,textTotalDataUsage,textTotalBal,textTotalBalLeft;
    private List<ApplicationInfo> packages;
    private PackageManager pm;
    private EventBroadcastReceiver eventBroadcastReceiver;
    private IntentFilter intentFilter;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupReceivers();

        textTotalBalLeft = (TextView) findViewById(R.id.totalLeftData);
        textTotalDataUsage = (TextView) findViewById(R.id.totalUsage);
        textTotalBal = (TextView) findViewById(R.id.totalBal);
        textVersion = (TextView) findViewById(R.id.title);
        textUpdateTime = (TextView) findViewById(R.id.updateTime);
        textinstalledTime = (TextView) findViewById(R.id.installTime);
        textDataUsage  = (TextView) findViewById(R.id.total);

        //logger.debug("AppVersion: " + Util.getAppVersion(getApplicationContext()));

        long lastUpdate = Util.getAppLastUpdateTime(getApplicationContext());
        //logger.debug("App Update Date: "+Util.getDateFormatted(lastUpdate));

        long installTime = Util.getAppInstalledTime(getApplicationContext());
        //logger.debug("App Installed Date: " + Util.getDateFormatted(installTime));

        textVersion.setText("Version: " + Util.getAppVersion(getApplicationContext()));
        textinstalledTime.setText("Install Time: "+Util.getDateFormatted(installTime));
        textUpdateTime.setText("Updated Time: "+Util.getDateFormatted(lastUpdate));

        ApplicationInfo appInfo = Util.getAppInfo(getApplicationContext());
        int UID = appInfo.uid; // appUid = android.os.Process.myUid();

        // internet usage for particular app(sent and received) by WiFi or GPRS
        long received = TrafficStats.getUidRxBytes(UID);
        long send =  TrafficStats.getUidTxBytes(UID);
        long total = received + send;

        //total = total+ PreferencesHelper.getLastTotalUsage();

        logger.debug("Total usage: " + total);
        textDataUsage.setText("App Total Usage: " + Util.getFileSize(total));

        logger.debug("Last usage saved: " + PreferencesHelper.getLastTotalUsage());
        logger.debug("total usage diff: " + Util.getFileSize(total - PreferencesHelper.getLastTotalUsage()));
        PreferencesHelper.setLastTotalUsage(total);

        pm = getPackageManager();
        packages = pm.getInstalledApplications(0);
        //new getTotalUsageTask().execute();

        // internet usage for particular app(sent and received)
       /*long mobUpload = TrafficStats.getMobileTxBytes();
        long mobDown = TrafficStats.getMobileRxBytes();
        long mobTotal = mobUpload + mobDown;
        logger.debug("Total usage of GPRS: " + mobTotal);*/

        //Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        //startActivity(intent);
        //Intent srvIntent = new Intent(MainActivity.this, CDUSSDService.class);
        //startService(srvIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(eventBroadcastReceiver, intentFilter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (eventBroadcastReceiver != null)
            unregisterReceiver(eventBroadcastReceiver);
    }

    private void setupReceivers() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(AccessService.ACTION_BALANCE);
        eventBroadcastReceiver = new EventBroadcastReceiver();
    }

    private class EventBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String s = intent.getAction();
                if (s.equals(AccessService.ACTION_BALANCE)) {
                    message = intent.getStringExtra(AccessService.EXTRA_MESG);
                    logger.debug("message: "+message);

                    if(message.contains("Bal")){
                        textTotalBal.setText(""+message);
                    }else if(message.contains("2G Data") || message.contains("3G Data")){
                        textTotalBalLeft.setText(""+message);
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class getTotalUsageTask extends AsyncTask<Void, Void, Long> {

        long totalData=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(Void... aurl) {
            try {
                //logger.debug("total apps::: "+packages.size());
                for (ApplicationInfo packageInfo : packages) {
                    // get the UID for the selected app
                    int UID = packageInfo.uid;
                    String package_name = packageInfo.packageName;
                    ApplicationInfo app = null;
                    try {
                        app = pm.getApplicationInfo(package_name, 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String name = (String) pm.getApplicationLabel(app);
                    //logger.debug("name: "+name);
                    long received = TrafficStats.getUidRxBytes(UID);
                    long send = TrafficStats.getUidTxBytes(UID);
                    long total = received + send;
                    //logger.debug("Total usage: "+total);
                    if(total>0)
                    {
                        totalData += total;
                        //logger.debug("totalData: "+totalData);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return totalData;
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            // System.out.println("response: "+result);
            try{
                //result = result + PreferencesHelper.getLastTotalUsage2();
                //logger.debug("totalData: "+totalData);
                textTotalDataUsage.setText("Total Usage: " + Util.getFileSize(result));
                //PreferencesHelper.setLastTotalUsage2(result);
            }catch(Exception s){
                s.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            ApplicationInfo appInfo = Util.getAppInfo(getApplicationContext());
            int UID = appInfo.uid;
            // internet usage for particular app(sent and received)
            long received = TrafficStats.getUidRxBytes(UID);
            long send =  TrafficStats.getUidTxBytes(UID);
            long total = received + send;
            //total = total+ PreferencesHelper.getLastTotalUsage();

            logger.debug("Total usage: " + total);
            textDataUsage.setText("App Total Usage: " + Util.getFileSize(total));

            //PreferencesHelper.setLastTotalUsage(total);
            return true;
        }else if(id == R.id.action_total){
            new getTotalUsageTask().execute();
            return true;
        }else if(id == R.id.action_bal){

            /*String encodedHash = Uri.encode("#");
            String encodedHash2 = Uri.encode("*");

            Intent launchCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode("*123#")));
            launchCall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchCall.addFlags(Intent.FLAG_FROM_BACKGROUND);
            startActivity(launchCall);*/

            startService(new Intent(this, AccessService.class));
            //dailNumber("123");

            //startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+encodedHash2+"123"+ encodedHash)));
            //startActivityForResult(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+encodedHash2+"123"+ encodedHash)), 1);
            return true;
        }else if(id == R.id.action_data){
            startService(new Intent(this, AccessService.class));
            //dailNumber2("123","10");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dailNumber(String code) {
        String ussdCode = "*" + code + Uri.encode("#");
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }

    private void dailNumber2(String code,String code2) {
        String ussdCode = "*" + code +"*"+ code+Uri.encode("#");
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        logger.debug("USSD: " + requestCode + " " + resultCode + " " + data);
    }
}
