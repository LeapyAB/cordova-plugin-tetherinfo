/*
 * Cordova ZeroConf Plugin
 *
 * ZeroConf plugin for Cordova/Phonegap
 * by Sylvain Brejeon
 */

package se.easyserv.plugin;

import android.net.wifi.WifiManager;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class TetherInfo extends CordovaPlugin implements ServiceListener {

    WifiManager.MulticastLock lock;
    private JmDNS publisher = null;
    private JmDNS browser = null;
    private String hostname = UUID.randomUUID().toString();
    private Map<String, CallbackContext> callbacks = new HashMap<String, CallbackContext>();

    public static final String ACTION_LISTIP = "listip";

    /*@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        WifiManager wifi = (WifiManager) this.cordova.getActivity().getSystemService(android.content.Context.WIFI_SERVICE);
        lock = wifi.createMulticastLock("ZeroConfPluginLock");
        lock.setReferenceCounted(true);
        lock.acquire();

        Log.v("ZeroConf", "Initialized");
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (publisher != null) {
            try {
                publisher.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                publisher = null;
            }
        }
        if (browser != null) {
            try {
                browser.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                browser = null;
                callbacks = null;
            }
        }
        if (lock != null) {
            lock.release();
            lock = null;
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {

        if (ACTION_LISTIP.equals(action)) {

            Log.d("TetherInfo", "ListIP ");

            String ips = getUSBThetheredIP();

            callbackContext.success(ips);

        } else {
            Log.e("TetherInfo", "Invalid action: " + action);
            callbackContext.error("Invalid action: " + action);
            return false;
        }

        return true;
    }


    public static String getUSBThetheredIP() {

        BufferedReader bufferedReader = null;
        String ips="";

        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ip = splitted[0];
                    String mac = splitted[3];
                    if (mac.matches("..:..:..:..:..:..")) {
                        if (mac.matches("00:00:00:00:00:00")) {
                            //Log.d("DEBUG", "Wrong:" + mac + ":" + ip);
                        } else {
                            //Log.d("DEBUG", "Correct:" + mac + ":" + ip);
                            ips = ip;
                            break;
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ips;
    }


    @Override
    public void serviceResolved(ServiceEvent ev) {
        Log.d("TetherInfo", "Resolved");

    }

    @Override
    public void serviceRemoved(ServiceEvent ev) {
        Log.d("TetherInfo", "Removed");

    }

    @Override
    public void serviceAdded(ServiceEvent event) {
        Log.d("TetherInfo", "Added");

    }

}
