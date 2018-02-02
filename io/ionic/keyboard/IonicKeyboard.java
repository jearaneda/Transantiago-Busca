package io.ionic.keyboard;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

public class IonicKeyboard extends CordovaPlugin {
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if ("close".equals(action)) {
            this.cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) IonicKeyboard.this.cordova.getActivity().getSystemService("input_method");
                    View v = IonicKeyboard.this.cordova.getActivity().getCurrentFocus();
                    if (v == null) {
                        callbackContext.error("No current focus");
                        return;
                    }
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 2);
                    callbackContext.success();
                }
            });
            return true;
        } else if ("show".equals(action)) {
            this.cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    ((InputMethodManager) IonicKeyboard.this.cordova.getActivity().getSystemService("input_method")).toggleSoftInput(0, 1);
                    callbackContext.success();
                }
            });
            return true;
        } else if (!"init".equals(action)) {
            return false;
        } else {
            this.cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    DisplayMetrics dm = new DisplayMetrics();
                    IonicKeyboard.this.cordova.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                    final float density = dm.density;
                    final View rootView = IonicKeyboard.this.cordova.getActivity().getWindow().getDecorView().findViewById(16908290).getRootView();
                    rootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                        int previousHeightDiff = 0;

                        public void onGlobalLayout() {
                            int screenHeight;
                            Rect r = new Rect();
                            rootView.getWindowVisibleDisplayFrame(r);
                            int rootViewHeight = rootView.getRootView().getHeight();
                            int resultBottom = r.bottom;
                            if (VERSION.SDK_INT >= 21) {
                                Display display = IonicKeyboard.this.cordova.getActivity().getWindowManager().getDefaultDisplay();
                                Point size = new Point();
                                display.getSize(size);
                                screenHeight = size.y;
                            } else {
                                screenHeight = rootViewHeight;
                            }
                            int pixelHeightDiff = (int) (((float) (screenHeight - resultBottom)) / density);
                            PluginResult result;
                            if (pixelHeightDiff > 100 && pixelHeightDiff != this.previousHeightDiff) {
                                result = new PluginResult(Status.OK, "S" + Integer.toString(pixelHeightDiff));
                                result.setKeepCallback(true);
                                callbackContext.sendPluginResult(result);
                            } else if (pixelHeightDiff != this.previousHeightDiff && this.previousHeightDiff - pixelHeightDiff > 100) {
                                result = new PluginResult(Status.OK, "H");
                                result.setKeepCallback(true);
                                callbackContext.sendPluginResult(result);
                            }
                            this.previousHeightDiff = pixelHeightDiff;
                        }
                    });
                    PluginResult dataResult = new PluginResult(Status.OK);
                    dataResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(dataResult);
                }
            });
            return true;
        }
    }
}
