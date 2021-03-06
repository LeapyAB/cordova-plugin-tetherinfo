/*
 * Cordova TetherInfo Plugin
 *
 * TetherInfo plugin for Cordova/Phonegap
 * by Jonas Olmstead
 */

package se.easyserv.plugin;

import android.net.wifi.WifiManager;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class TetherInfo extends CordovaPlugin {

    private String hostname = UUID.randomUUID().toString();
    private Map<String, CallbackContext> callbacks = new HashMap<String, CallbackContext>();

    public static final String ACTION_LISTIP = "listip";
    public static final String ACTION_CHECKUSB = "checkusb";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {

        if (ACTION_LISTIP.equals(action)) {

            Log.d("TetherInfo", "ListIP ");

            String ips = getUSBThetheredIP();

            callbackContext.success(ips);

        } else if (ACTION_CHECKUSB.equals(action)) {

            Log.d("TetherInfo", "CheckUSB ");

            Context context = this.cordova.getActivity().getApplicationContext();
            boolean bConnected = isUsbTethered(context);

            callbackContext.success("" + bConnected);

        } else {
            Log.e("TetherInfo", "Invalid action: " + action);
            callbackContext.error("Invalid action: " + action);
            return false;
        }

        return true;
    }

    public static boolean isUsbTethered(Context context) {

        Intent intent = context.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
        return intent.getExtras().getBoolean("rndis");

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
                            ips = ips.length() == 0 ? ip : ips + "," + ip;
                            //break;
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

}
