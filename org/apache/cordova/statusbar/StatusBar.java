package org.apache.cordova.statusbar;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.view.Window;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONException;

public class StatusBar extends CordovaPlugin {
    private static final String TAG = "StatusBar";

    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        LOG.m9v(TAG, "StatusBar: initialization");
        super.initialize(cordova, webView);
        this.cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                cordova.getActivity().getWindow().clearFlags(2048);
                StatusBar.this.setStatusBarBackgroundColor(StatusBar.this.preferences.getString("StatusBarBackgroundColor", "#000000"));
            }
        });
    }

    public boolean execute(String action, final CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        boolean statusBarVisible = false;
        LOG.m9v(TAG, "Executing action: " + action);
        final Window window = this.cordova.getActivity().getWindow();
        if ("_ready".equals(action)) {
            if ((window.getAttributes().flags & 1024) == 0) {
                statusBarVisible = true;
            }
            callbackContext.sendPluginResult(new PluginResult(Status.OK, statusBarVisible));
            return true;
        } else if ("show".equals(action)) {
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (VERSION.SDK_INT >= 19) {
                        window.getDecorView().setSystemUiVisibility((window.getDecorView().getSystemUiVisibility() & -1025) & -5);
                    }
                    window.clearFlags(1024);
                }
            });
            return true;
        } else if ("hide".equals(action)) {
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (VERSION.SDK_INT >= 19) {
                        window.getDecorView().setSystemUiVisibility((window.getDecorView().getSystemUiVisibility() | 1024) | 4);
                    }
                    window.addFlags(1024);
                }
            });
            return true;
        } else if (!"backgroundColorByHexString".equals(action)) {
            return false;
        } else {
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        StatusBar.this.setStatusBarBackgroundColor(args.getString(0));
                    } catch (JSONException e) {
                        LOG.m3e(StatusBar.TAG, "Invalid hexString argument, use f.i. '#777777'");
                    }
                }
            });
            return true;
        }
    }

    private void setStatusBarBackgroundColor(String colorPref) {
        if (VERSION.SDK_INT >= 21 && colorPref != null && !colorPref.isEmpty()) {
            Window window = this.cordova.getActivity().getWindow();
            window.clearFlags(67108864);
            window.addFlags(Integer.MIN_VALUE);
            try {
                window.getClass().getDeclaredMethod("setStatusBarColor", new Class[]{Integer.TYPE}).invoke(window, new Object[]{Integer.valueOf(Color.parseColor(colorPref))});
            } catch (IllegalArgumentException e) {
                LOG.m3e(TAG, "Invalid hexString argument, use f.i. '#999999'");
            } catch (Exception e2) {
                LOG.m12w(TAG, "Method window.setStatusBarColor not found for SDK level " + VERSION.SDK_INT);
            }
        }
    }
}
