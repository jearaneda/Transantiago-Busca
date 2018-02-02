package org.apache.cordova.inappbrowser;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.Config;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaHttpAuthHandler;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginManager;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled"})
public class InAppBrowser extends CordovaPlugin {
    private static final String CLEAR_ALL_CACHE = "clearcache";
    private static final String CLEAR_SESSION_CACHE = "clearsessioncache";
    private static final String EXIT_EVENT = "exit";
    private static final String HARDWARE_BACK_BUTTON = "hardwareback";
    private static final String HIDDEN = "hidden";
    private static final String LOAD_ERROR_EVENT = "loaderror";
    private static final String LOAD_START_EVENT = "loadstart";
    private static final String LOAD_STOP_EVENT = "loadstop";
    private static final String LOCATION = "location";
    protected static final String LOG_TAG = "InAppBrowser";
    private static final String MEDIA_PLAYBACK_REQUIRES_USER_ACTION = "mediaPlaybackRequiresUserAction";
    private static final String NULL = "null";
    private static final String SELF = "_self";
    private static final String SHOULD_PAUSE = "shouldPauseOnSuspend";
    private static final String SYSTEM = "_system";
    private static final String ZOOM = "zoom";
    private CallbackContext callbackContext;
    private boolean clearAllCache = false;
    private boolean clearSessionCache = false;
    private InAppBrowserDialog dialog;
    private EditText edittext;
    private boolean hadwareBackButton = true;
    private WebView inAppWebView;
    private boolean mediaPlaybackRequiresUserGesture = false;
    private boolean openWindowHidden = false;
    private boolean shouldPauseInAppBrowser = false;
    private boolean showLocationBar = true;
    private boolean showZoomControls = true;

    class C00332 implements Runnable {
        C00332() {
        }

        public void run() {
            InAppBrowser.this.dialog.show();
        }
    }

    class C00364 implements Runnable {

        class C00351 extends WebViewClient {
            C00351() {
            }

            public void onPageFinished(WebView view, String url) {
                if (InAppBrowser.this.dialog != null) {
                    InAppBrowser.this.dialog.dismiss();
                    InAppBrowser.this.dialog = null;
                }
            }
        }

        C00364() {
        }

        public void run() {
            WebView childView = InAppBrowser.this.inAppWebView;
            if (childView != null) {
                childView.setWebViewClient(new C00351());
                childView.loadUrl("about:blank");
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("type", InAppBrowser.EXIT_EVENT);
                    InAppBrowser.this.sendUpdate(obj, false);
                } catch (JSONException e) {
                    LOG.m0d(InAppBrowser.LOG_TAG, "Should never happen");
                }
            }
        }
    }

    public class InAppBrowserClient extends WebViewClient {
        EditText edittext;
        CordovaWebView webView;

        public InAppBrowserClient(CordovaWebView webView, EditText mEditText) {
            this.webView = webView;
            this.edittext = mEditText;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            Intent intent;
            if (url.startsWith("tel:")) {
                try {
                    intent = new Intent("android.intent.action.DIAL");
                    intent.setData(Uri.parse(url));
                    InAppBrowser.this.cordova.getActivity().startActivity(intent);
                    return true;
                } catch (ActivityNotFoundException e) {
                    LOG.m3e(InAppBrowser.LOG_TAG, "Error dialing " + url + ": " + e.toString());
                }
            } else if (url.startsWith("geo:") || url.startsWith("mailto:") || url.startsWith("market:") || url.startsWith("intent:")) {
                try {
                    intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(url));
                    InAppBrowser.this.cordova.getActivity().startActivity(intent);
                    return true;
                } catch (ActivityNotFoundException e2) {
                    LOG.m3e(InAppBrowser.LOG_TAG, "Error with " + url + ": " + e2.toString());
                }
            } else {
                if (url.startsWith("sms:")) {
                    try {
                        String address;
                        intent = new Intent("android.intent.action.VIEW");
                        int parmIndex = url.indexOf(63);
                        if (parmIndex == -1) {
                            address = url.substring(4);
                        } else {
                            address = url.substring(4, parmIndex);
                            String query = Uri.parse(url).getQuery();
                            if (query != null && query.startsWith("body=")) {
                                intent.putExtra("sms_body", query.substring(5));
                            }
                        }
                        intent.setData(Uri.parse("sms:" + address));
                        intent.putExtra("address", address);
                        intent.setType("vnd.android-dir/mms-sms");
                        InAppBrowser.this.cordova.getActivity().startActivity(intent);
                        return true;
                    } catch (ActivityNotFoundException e22) {
                        LOG.m3e(InAppBrowser.LOG_TAG, "Error sending sms " + url + ":" + e22.toString());
                    }
                }
                return false;
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            String newloc = "";
            if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("file:")) {
                newloc = url;
            } else {
                LOG.m3e(InAppBrowser.LOG_TAG, "Possible Uncaught/Unknown URI");
                newloc = "http://" + url;
            }
            if (!newloc.equals(this.edittext.getText().toString())) {
                this.edittext.setText(newloc);
            }
            try {
                JSONObject obj = new JSONObject();
                obj.put("type", InAppBrowser.LOAD_START_EVENT);
                obj.put("url", newloc);
                InAppBrowser.this.sendUpdate(obj, true);
            } catch (JSONException e) {
                LOG.m3e(InAppBrowser.LOG_TAG, "URI passed in has caused a JSON error.");
            }
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().flush();
            } else {
                CookieSyncManager.getInstance().sync();
            }
            try {
                JSONObject obj = new JSONObject();
                obj.put("type", InAppBrowser.LOAD_STOP_EVENT);
                obj.put("url", url);
                InAppBrowser.this.sendUpdate(obj, true);
            } catch (JSONException e) {
                LOG.m0d(InAppBrowser.LOG_TAG, "Should never happen");
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            try {
                JSONObject obj = new JSONObject();
                obj.put("type", InAppBrowser.LOAD_ERROR_EVENT);
                obj.put("url", failingUrl);
                obj.put("code", errorCode);
                obj.put("message", description);
                InAppBrowser.this.sendUpdate(obj, true, Status.ERROR);
            } catch (JSONException e) {
                LOG.m0d(InAppBrowser.LOG_TAG, "Should never happen");
            }
        }

        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            PluginManager pluginManager = null;
            try {
                pluginManager = (PluginManager) this.webView.getClass().getMethod("getPluginManager", new Class[0]).invoke(this.webView, new Object[0]);
            } catch (NoSuchMethodException e) {
                LOG.m0d(InAppBrowser.LOG_TAG, e.getLocalizedMessage());
            } catch (IllegalAccessException e2) {
                LOG.m0d(InAppBrowser.LOG_TAG, e2.getLocalizedMessage());
            } catch (InvocationTargetException e3) {
                LOG.m0d(InAppBrowser.LOG_TAG, e3.getLocalizedMessage());
            }
            if (pluginManager == null) {
                try {
                    pluginManager = (PluginManager) this.webView.getClass().getField("pluginManager").get(this.webView);
                } catch (NoSuchFieldException e4) {
                    LOG.m0d(InAppBrowser.LOG_TAG, e4.getLocalizedMessage());
                } catch (IllegalAccessException e22) {
                    LOG.m0d(InAppBrowser.LOG_TAG, e22.getLocalizedMessage());
                }
            }
            if (pluginManager == null || !pluginManager.onReceivedHttpAuthRequest(this.webView, new CordovaHttpAuthHandler(handler), host, realm)) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }
        }
    }

    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("open")) {
            this.callbackContext = callbackContext;
            final String url = args.getString(0);
            String t = args.optString(1);
            if (t == null || t.equals("") || t.equals(NULL)) {
                t = SELF;
            }
            final String target = t;
            final HashMap<String, Boolean> features = parseFeature(args.optString(2));
            LOG.m0d(LOG_TAG, "target = " + target);
            final CallbackContext callbackContext2 = callbackContext;
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    String result = "";
                    if (InAppBrowser.SELF.equals(target)) {
                        LOG.m0d(InAppBrowser.LOG_TAG, "in self");
                        Boolean shouldAllowNavigation = null;
                        if (url.startsWith("javascript:")) {
                            shouldAllowNavigation = Boolean.valueOf(true);
                        }
                        if (shouldAllowNavigation == null) {
                            try {
                                shouldAllowNavigation = (Boolean) Config.class.getMethod("isUrlWhiteListed", new Class[]{String.class}).invoke(null, new Object[]{url});
                            } catch (NoSuchMethodException e) {
                                LOG.m0d(InAppBrowser.LOG_TAG, e.getLocalizedMessage());
                            } catch (IllegalAccessException e2) {
                                LOG.m0d(InAppBrowser.LOG_TAG, e2.getLocalizedMessage());
                            } catch (InvocationTargetException e3) {
                                LOG.m0d(InAppBrowser.LOG_TAG, e3.getLocalizedMessage());
                            }
                        }
                        if (shouldAllowNavigation == null) {
                            try {
                                PluginManager pm = (PluginManager) InAppBrowser.this.webView.getClass().getMethod("getPluginManager", new Class[0]).invoke(InAppBrowser.this.webView, new Object[0]);
                                shouldAllowNavigation = (Boolean) pm.getClass().getMethod("shouldAllowNavigation", new Class[]{String.class}).invoke(pm, new Object[]{url});
                            } catch (NoSuchMethodException e4) {
                                LOG.m0d(InAppBrowser.LOG_TAG, e4.getLocalizedMessage());
                            } catch (IllegalAccessException e22) {
                                LOG.m0d(InAppBrowser.LOG_TAG, e22.getLocalizedMessage());
                            } catch (InvocationTargetException e32) {
                                LOG.m0d(InAppBrowser.LOG_TAG, e32.getLocalizedMessage());
                            }
                        }
                        if (Boolean.TRUE.equals(shouldAllowNavigation)) {
                            LOG.m0d(InAppBrowser.LOG_TAG, "loading in webview");
                            InAppBrowser.this.webView.loadUrl(url);
                        } else if (url.startsWith("tel:")) {
                            try {
                                LOG.m0d(InAppBrowser.LOG_TAG, "loading in dialer");
                                Intent intent = new Intent("android.intent.action.DIAL");
                                intent.setData(Uri.parse(url));
                                InAppBrowser.this.cordova.getActivity().startActivity(intent);
                            } catch (ActivityNotFoundException e5) {
                                LOG.m3e(InAppBrowser.LOG_TAG, "Error dialing " + url + ": " + e5.toString());
                            }
                        } else {
                            LOG.m0d(InAppBrowser.LOG_TAG, "loading in InAppBrowser");
                            result = InAppBrowser.this.showWebPage(url, features);
                        }
                    } else if (InAppBrowser.SYSTEM.equals(target)) {
                        LOG.m0d(InAppBrowser.LOG_TAG, "in system");
                        result = InAppBrowser.this.openExternal(url);
                    } else {
                        LOG.m0d(InAppBrowser.LOG_TAG, "in blank");
                        result = InAppBrowser.this.showWebPage(url, features);
                    }
                    PluginResult pluginResult = new PluginResult(Status.OK, result);
                    pluginResult.setKeepCallback(true);
                    callbackContext2.sendPluginResult(pluginResult);
                }
            });
        } else if (action.equals("close")) {
            closeDialog();
        } else if (action.equals("injectScriptCode")) {
            jsWrapper = null;
            if (args.getBoolean(1)) {
                jsWrapper = String.format("(function(){prompt(JSON.stringify([eval(%%s)]), 'gap-iab://%s')})()", new Object[]{callbackContext.getCallbackId()});
            }
            injectDeferredObject(args.getString(0), jsWrapper);
        } else if (action.equals("injectScriptFile")) {
            if (args.getBoolean(1)) {
                jsWrapper = String.format("(function(d) { var c = d.createElement('script'); c.src = %%s; c.onload = function() { prompt('', 'gap-iab://%s'); }; d.body.appendChild(c); })(document)", new Object[]{callbackContext.getCallbackId()});
            } else {
                jsWrapper = "(function(d) { var c = d.createElement('script'); c.src = %s; d.body.appendChild(c); })(document)";
            }
            injectDeferredObject(args.getString(0), jsWrapper);
        } else if (action.equals("injectStyleCode")) {
            if (args.getBoolean(1)) {
                jsWrapper = String.format("(function(d) { var c = d.createElement('style'); c.innerHTML = %%s; d.body.appendChild(c); prompt('', 'gap-iab://%s');})(document)", new Object[]{callbackContext.getCallbackId()});
            } else {
                jsWrapper = "(function(d) { var c = d.createElement('style'); c.innerHTML = %s; d.body.appendChild(c); })(document)";
            }
            injectDeferredObject(args.getString(0), jsWrapper);
        } else if (action.equals("injectStyleFile")) {
            if (args.getBoolean(1)) {
                jsWrapper = String.format("(function(d) { var c = d.createElement('link'); c.rel='stylesheet'; c.type='text/css'; c.href = %%s; d.head.appendChild(c); prompt('', 'gap-iab://%s');})(document)", new Object[]{callbackContext.getCallbackId()});
            } else {
                jsWrapper = "(function(d) { var c = d.createElement('link'); c.rel='stylesheet'; c.type='text/css'; c.href = %s; d.head.appendChild(c); })(document)";
            }
            injectDeferredObject(args.getString(0), jsWrapper);
        } else if (!action.equals("show")) {
            return false;
        } else {
            this.cordova.getActivity().runOnUiThread(new C00332());
            PluginResult pluginResult = new PluginResult(Status.OK);
            pluginResult.setKeepCallback(true);
            this.callbackContext.sendPluginResult(pluginResult);
        }
        return true;
    }

    public void onReset() {
        closeDialog();
    }

    public void onPause(boolean multitasking) {
        if (this.shouldPauseInAppBrowser) {
            this.inAppWebView.onPause();
        }
    }

    public void onResume(boolean multitasking) {
        if (this.shouldPauseInAppBrowser) {
            this.inAppWebView.onResume();
        }
    }

    public void onDestroy() {
        closeDialog();
    }

    private void injectDeferredObject(String source, String jsWrapper) {
        String scriptToInject;
        if (jsWrapper != null) {
            JSONArray jsonEsc = new JSONArray();
            jsonEsc.put(source);
            String jsonRepr = jsonEsc.toString();
            String jsonSourceString = jsonRepr.substring(1, jsonRepr.length() - 1);
            scriptToInject = String.format(jsWrapper, new Object[]{jsonSourceString});
        } else {
            scriptToInject = source;
        }
        final String finalScriptToInject = scriptToInject;
        this.cordova.getActivity().runOnUiThread(new Runnable() {
            @SuppressLint({"NewApi"})
            public void run() {
                if (VERSION.SDK_INT < 19) {
                    InAppBrowser.this.inAppWebView.loadUrl("javascript:" + finalScriptToInject);
                } else {
                    InAppBrowser.this.inAppWebView.evaluateJavascript(finalScriptToInject, null);
                }
            }
        });
    }

    private HashMap<String, Boolean> parseFeature(String optString) {
        if (optString.equals(NULL)) {
            return null;
        }
        HashMap<String, Boolean> map = new HashMap();
        StringTokenizer features = new StringTokenizer(optString, ",");
        while (features.hasMoreElements()) {
            StringTokenizer option = new StringTokenizer(features.nextToken(), "=");
            if (option.hasMoreElements()) {
                map.put(option.nextToken(), option.nextToken().equals("no") ? Boolean.FALSE : Boolean.TRUE);
            }
        }
        return map;
    }

    public String openExternal(String url) {
        ActivityNotFoundException e;
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            try {
                Uri uri = Uri.parse(url);
                if ("file".equals(uri.getScheme())) {
                    intent.setDataAndType(uri, this.webView.getResourceApi().getMimeType(uri));
                } else {
                    intent.setData(uri);
                }
                intent.putExtra("com.android.browser.application_id", this.cordova.getActivity().getPackageName());
                this.cordova.getActivity().startActivity(intent);
                return "";
            } catch (ActivityNotFoundException e2) {
                e = e2;
                Intent intent2 = intent;
                LOG.m0d(LOG_TAG, "InAppBrowser: Error loading url " + url + ":" + e.toString());
                return e.toString();
            }
        } catch (ActivityNotFoundException e3) {
            e = e3;
            LOG.m0d(LOG_TAG, "InAppBrowser: Error loading url " + url + ":" + e.toString());
            return e.toString();
        }
    }

    public void closeDialog() {
        this.cordova.getActivity().runOnUiThread(new C00364());
    }

    public void goBack() {
        if (this.inAppWebView.canGoBack()) {
            this.inAppWebView.goBack();
        }
    }

    public boolean canGoBack() {
        return this.inAppWebView.canGoBack();
    }

    public boolean hardwareBack() {
        return this.hadwareBackButton;
    }

    private void goForward() {
        if (this.inAppWebView.canGoForward()) {
            this.inAppWebView.goForward();
        }
    }

    private void navigate(String url) {
        ((InputMethodManager) this.cordova.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.edittext.getWindowToken(), 0);
        if (url.startsWith("http") || url.startsWith("file:")) {
            this.inAppWebView.loadUrl(url);
        } else {
            this.inAppWebView.loadUrl("http://" + url);
        }
        this.inAppWebView.requestFocus();
    }

    private boolean getShowLocationBar() {
        return this.showLocationBar;
    }

    private InAppBrowser getInAppBrowser() {
        return this;
    }

    public String showWebPage(final String url, HashMap<String, Boolean> features) {
        this.showLocationBar = true;
        this.showZoomControls = true;
        this.openWindowHidden = false;
        this.mediaPlaybackRequiresUserGesture = false;
        if (features != null) {
            Boolean show = (Boolean) features.get(LOCATION);
            if (show != null) {
                this.showLocationBar = show.booleanValue();
            }
            Boolean zoom = (Boolean) features.get(ZOOM);
            if (zoom != null) {
                this.showZoomControls = zoom.booleanValue();
            }
            Boolean hidden = (Boolean) features.get(HIDDEN);
            if (hidden != null) {
                this.openWindowHidden = hidden.booleanValue();
            }
            Boolean hardwareBack = (Boolean) features.get(HARDWARE_BACK_BUTTON);
            if (hardwareBack != null) {
                this.hadwareBackButton = hardwareBack.booleanValue();
            }
            Boolean mediaPlayback = (Boolean) features.get(MEDIA_PLAYBACK_REQUIRES_USER_ACTION);
            if (mediaPlayback != null) {
                this.mediaPlaybackRequiresUserGesture = mediaPlayback.booleanValue();
            }
            Boolean cache = (Boolean) features.get(CLEAR_ALL_CACHE);
            if (cache != null) {
                this.clearAllCache = cache.booleanValue();
            } else {
                cache = (Boolean) features.get(CLEAR_SESSION_CACHE);
                if (cache != null) {
                    this.clearSessionCache = cache.booleanValue();
                }
            }
            Boolean shouldPause = (Boolean) features.get(SHOULD_PAUSE);
            if (shouldPause != null) {
                this.shouldPauseInAppBrowser = shouldPause.booleanValue();
            }
        }
        final CordovaWebView thatWebView = this.webView;
        this.cordova.getActivity().runOnUiThread(new Runnable() {

            class C00371 implements OnClickListener {
                C00371() {
                }

                public void onClick(View v) {
                    InAppBrowser.this.goBack();
                }
            }

            class C00382 implements OnClickListener {
                C00382() {
                }

                public void onClick(View v) {
                    InAppBrowser.this.goForward();
                }
            }

            class C00393 implements OnKeyListener {
                C00393() {
                }

                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() != 0 || keyCode != 66) {
                        return false;
                    }
                    InAppBrowser.this.navigate(InAppBrowser.this.edittext.getText().toString());
                    return true;
                }
            }

            class C00404 implements OnClickListener {
                C00404() {
                }

                public void onClick(View v) {
                    InAppBrowser.this.closeDialog();
                }
            }

            private int dpToPixels(int dipValue) {
                return (int) TypedValue.applyDimension(1, (float) dipValue, InAppBrowser.this.cordova.getActivity().getResources().getDisplayMetrics());
            }

            @SuppressLint({"NewApi"})
            public void run() {
                if (InAppBrowser.this.dialog != null) {
                    InAppBrowser.this.dialog.dismiss();
                }
                InAppBrowser.this.dialog = new InAppBrowserDialog(InAppBrowser.this.cordova.getActivity(), 16973830);
                InAppBrowser.this.dialog.getWindow().getAttributes().windowAnimations = 16973826;
                InAppBrowser.this.dialog.requestWindowFeature(1);
                InAppBrowser.this.dialog.setCancelable(true);
                InAppBrowser.this.dialog.setInAppBroswer(InAppBrowser.this.getInAppBrowser());
                View linearLayout = new LinearLayout(InAppBrowser.this.cordova.getActivity());
                linearLayout.setOrientation(1);
                View toolbar = new RelativeLayout(InAppBrowser.this.cordova.getActivity());
                toolbar.setBackgroundColor(-3355444);
                toolbar.setLayoutParams(new LayoutParams(-1, dpToPixels(44)));
                toolbar.setPadding(dpToPixels(2), dpToPixels(2), dpToPixels(2), dpToPixels(2));
                toolbar.setHorizontalGravity(3);
                toolbar.setVerticalGravity(48);
                RelativeLayout actionButtonContainer = new RelativeLayout(InAppBrowser.this.cordova.getActivity());
                actionButtonContainer.setLayoutParams(new LayoutParams(-2, -2));
                actionButtonContainer.setHorizontalGravity(3);
                actionButtonContainer.setVerticalGravity(16);
                actionButtonContainer.setId(Integer.valueOf(1).intValue());
                ImageButton back = new ImageButton(InAppBrowser.this.cordova.getActivity());
                LayoutParams backLayoutParams = new LayoutParams(-2, -1);
                backLayoutParams.addRule(5);
                back.setLayoutParams(backLayoutParams);
                back.setContentDescription("Back Button");
                back.setId(Integer.valueOf(2).intValue());
                Resources activityRes = InAppBrowser.this.cordova.getActivity().getResources();
                Drawable backIcon = activityRes.getDrawable(activityRes.getIdentifier("ic_action_previous_item", "drawable", InAppBrowser.this.cordova.getActivity().getPackageName()));
                if (VERSION.SDK_INT >= 16) {
                    back.setBackground(null);
                } else {
                    back.setBackgroundDrawable(null);
                }
                back.setImageDrawable(backIcon);
                back.setScaleType(ScaleType.FIT_CENTER);
                back.setPadding(0, dpToPixels(10), 0, dpToPixels(10));
                if (VERSION.SDK_INT >= 16) {
                    back.getAdjustViewBounds();
                }
                back.setOnClickListener(new C00371());
                linearLayout = new ImageButton(InAppBrowser.this.cordova.getActivity());
                LayoutParams layoutParams = new LayoutParams(-2, -1);
                layoutParams.addRule(1, 2);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setContentDescription("Forward Button");
                linearLayout.setId(Integer.valueOf(3).intValue());
                Drawable fwdIcon = activityRes.getDrawable(activityRes.getIdentifier("ic_action_next_item", "drawable", InAppBrowser.this.cordova.getActivity().getPackageName()));
                if (VERSION.SDK_INT >= 16) {
                    linearLayout.setBackground(null);
                } else {
                    linearLayout.setBackgroundDrawable(null);
                }
                linearLayout.setImageDrawable(fwdIcon);
                linearLayout.setScaleType(ScaleType.FIT_CENTER);
                linearLayout.setPadding(0, dpToPixels(10), 0, dpToPixels(10));
                if (VERSION.SDK_INT >= 16) {
                    linearLayout.getAdjustViewBounds();
                }
                linearLayout.setOnClickListener(new C00382());
                InAppBrowser.this.edittext = new EditText(InAppBrowser.this.cordova.getActivity());
                ViewGroup.LayoutParams layoutParams2 = new LayoutParams(-1, -1);
                layoutParams2.addRule(1, 1);
                layoutParams2.addRule(0, 5);
                InAppBrowser.this.edittext.setLayoutParams(layoutParams2);
                InAppBrowser.this.edittext.setId(Integer.valueOf(4).intValue());
                InAppBrowser.this.edittext.setSingleLine(true);
                InAppBrowser.this.edittext.setText(url);
                InAppBrowser.this.edittext.setInputType(16);
                InAppBrowser.this.edittext.setImeOptions(2);
                InAppBrowser.this.edittext.setInputType(0);
                InAppBrowser.this.edittext.setOnKeyListener(new C00393());
                ImageButton close = new ImageButton(InAppBrowser.this.cordova.getActivity());
                layoutParams2 = new LayoutParams(-2, -1);
                layoutParams2.addRule(11);
                close.setLayoutParams(layoutParams2);
                linearLayout.setContentDescription("Close Button");
                close.setId(Integer.valueOf(5).intValue());
                Drawable closeIcon = activityRes.getDrawable(activityRes.getIdentifier("ic_action_remove", "drawable", InAppBrowser.this.cordova.getActivity().getPackageName()));
                if (VERSION.SDK_INT >= 16) {
                    close.setBackground(null);
                } else {
                    close.setBackgroundDrawable(null);
                }
                close.setImageDrawable(closeIcon);
                close.setScaleType(ScaleType.FIT_CENTER);
                back.setPadding(0, dpToPixels(10), 0, dpToPixels(10));
                if (VERSION.SDK_INT >= 16) {
                    close.getAdjustViewBounds();
                }
                close.setOnClickListener(new C00404());
                InAppBrowser.this.inAppWebView = new WebView(InAppBrowser.this.cordova.getActivity());
                InAppBrowser.this.inAppWebView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                InAppBrowser.this.inAppWebView.setId(Integer.valueOf(6).intValue());
                InAppBrowser.this.inAppWebView.setWebChromeClient(new InAppChromeClient(thatWebView));
                InAppBrowser.this.inAppWebView.setWebViewClient(new InAppBrowserClient(thatWebView, InAppBrowser.this.edittext));
                WebSettings settings = InAppBrowser.this.inAppWebView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setBuiltInZoomControls(InAppBrowser.this.showZoomControls);
                settings.setPluginState(PluginState.ON);
                if (VERSION.SDK_INT >= 17) {
                    settings.setMediaPlaybackRequiresUserGesture(InAppBrowser.this.mediaPlaybackRequiresUserGesture);
                }
                String overrideUserAgent = InAppBrowser.this.preferences.getString("OverrideUserAgent", null);
                String appendUserAgent = InAppBrowser.this.preferences.getString("AppendUserAgent", null);
                if (overrideUserAgent != null) {
                    settings.setUserAgentString(overrideUserAgent);
                }
                if (appendUserAgent != null) {
                    settings.setUserAgentString(settings.getUserAgentString() + appendUserAgent);
                }
                Bundle appSettings = InAppBrowser.this.cordova.getActivity().getIntent().getExtras();
                if (appSettings == null ? true : appSettings.getBoolean("InAppBrowserStorageEnabled", true)) {
                    settings.setDatabasePath(InAppBrowser.this.cordova.getActivity().getApplicationContext().getDir("inAppBrowserDB", 0).getPath());
                    settings.setDatabaseEnabled(true);
                }
                settings.setDomStorageEnabled(true);
                if (InAppBrowser.this.clearAllCache) {
                    CookieManager.getInstance().removeAllCookie();
                } else if (InAppBrowser.this.clearSessionCache) {
                    CookieManager.getInstance().removeSessionCookie();
                }
                InAppBrowser.this.inAppWebView.loadUrl(url);
                InAppBrowser.this.inAppWebView.setId(Integer.valueOf(6).intValue());
                InAppBrowser.this.inAppWebView.getSettings().setLoadWithOverviewMode(true);
                InAppBrowser.this.inAppWebView.getSettings().setUseWideViewPort(true);
                InAppBrowser.this.inAppWebView.requestFocus();
                InAppBrowser.this.inAppWebView.requestFocusFromTouch();
                actionButtonContainer.addView(back);
                actionButtonContainer.addView(linearLayout);
                toolbar.addView(actionButtonContainer);
                toolbar.addView(InAppBrowser.this.edittext);
                toolbar.addView(close);
                if (InAppBrowser.this.getShowLocationBar()) {
                    linearLayout.addView(toolbar);
                }
                linearLayout.addView(InAppBrowser.this.inAppWebView);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(InAppBrowser.this.dialog.getWindow().getAttributes());
                lp.width = -1;
                lp.height = -1;
                InAppBrowser.this.dialog.setContentView(linearLayout);
                InAppBrowser.this.dialog.show();
                InAppBrowser.this.dialog.getWindow().setAttributes(lp);
                if (InAppBrowser.this.openWindowHidden) {
                    InAppBrowser.this.dialog.hide();
                }
            }
        });
        return "";
    }

    private void sendUpdate(JSONObject obj, boolean keepCallback) {
        sendUpdate(obj, keepCallback, Status.OK);
    }

    private void sendUpdate(JSONObject obj, boolean keepCallback, Status status) {
        if (this.callbackContext != null) {
            PluginResult result = new PluginResult(status, obj);
            result.setKeepCallback(keepCallback);
            this.callbackContext.sendPluginResult(result);
            if (!keepCallback) {
                this.callbackContext = null;
            }
        }
    }
}
