package org.apache.cordova.splashscreen;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

public class SplashScreen extends CordovaPlugin {
    private static final int DEFAULT_FADE_DURATION = 500;
    private static final int DEFAULT_SPLASHSCREEN_DURATION = 3000;
    private static final boolean HAS_BUILT_IN_SPLASH_SCREEN;
    private static final String LOG_TAG = "SplashScreen";
    private static boolean firstShow = true;
    private static boolean lastHideAfterDelay;
    private static ProgressDialog spinnerDialog;
    private static Dialog splashDialog;
    private int orientation;
    private ImageView splashImageView;

    class C00421 implements Runnable {
        C00421() {
        }

        public void run() {
            SplashScreen.this.getView().setVisibility(4);
        }
    }

    class C00432 implements Runnable {
        C00432() {
        }

        public void run() {
            SplashScreen.this.webView.postMessage("splashscreen", "hide");
        }
    }

    class C00443 implements Runnable {
        C00443() {
        }

        public void run() {
            SplashScreen.this.webView.postMessage("splashscreen", "show");
        }
    }

    class C00506 implements Runnable {

        class C00491 implements OnCancelListener {
            C00491() {
            }

            public void onCancel(DialogInterface dialog) {
                SplashScreen.spinnerDialog = null;
            }
        }

        C00506() {
        }

        public void run() {
            SplashScreen.this.spinnerStop();
            SplashScreen.spinnerDialog = new ProgressDialog(SplashScreen.this.webView.getContext());
            SplashScreen.spinnerDialog.setOnCancelListener(new C00491());
            SplashScreen.spinnerDialog.setCancelable(false);
            SplashScreen.spinnerDialog.setIndeterminate(true);
            RelativeLayout centeredLayout = new RelativeLayout(SplashScreen.this.cordova.getActivity());
            centeredLayout.setGravity(17);
            centeredLayout.setLayoutParams(new LayoutParams(-2, -2));
            ProgressBar progressBar = new ProgressBar(SplashScreen.this.webView.getContext());
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.addRule(13, -1);
            progressBar.setLayoutParams(layoutParams);
            centeredLayout.addView(progressBar);
            SplashScreen.spinnerDialog.getWindow().clearFlags(2);
            SplashScreen.spinnerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            SplashScreen.spinnerDialog.show();
            SplashScreen.spinnerDialog.setContentView(centeredLayout);
        }
    }

    class C00517 implements Runnable {
        C00517() {
        }

        public void run() {
            if (SplashScreen.spinnerDialog != null && SplashScreen.spinnerDialog.isShowing()) {
                SplashScreen.spinnerDialog.dismiss();
                SplashScreen.spinnerDialog = null;
            }
        }
    }

    static {
        boolean z = false;
        if (Integer.valueOf(CordovaWebView.CORDOVA_VERSION.split("\\.")[0]).intValue() < 4) {
            z = true;
        }
        HAS_BUILT_IN_SPLASH_SCREEN = z;
    }

    private View getView() {
        try {
            return (View) this.webView.getClass().getMethod("getView", new Class[0]).invoke(this.webView, new Object[0]);
        } catch (Exception e) {
            return (View) this.webView;
        }
    }

    protected void pluginInitialize() {
        if (!HAS_BUILT_IN_SPLASH_SCREEN) {
            this.cordova.getActivity().runOnUiThread(new C00421());
            if (this.preferences.getInteger("SplashDrawableId", 0) == 0) {
                String splashResource = this.preferences.getString(LOG_TAG, "screen");
                if (splashResource != null) {
                    int drawableId = this.cordova.getActivity().getResources().getIdentifier(splashResource, "drawable", this.cordova.getActivity().getClass().getPackage().getName());
                    if (drawableId == 0) {
                        drawableId = this.cordova.getActivity().getResources().getIdentifier(splashResource, "drawable", this.cordova.getActivity().getPackageName());
                    }
                    this.preferences.set("SplashDrawableId", drawableId);
                }
            }
            this.orientation = this.cordova.getActivity().getResources().getConfiguration().orientation;
            if (firstShow) {
                showSplashScreen(this.preferences.getBoolean("AutoHideSplashScreen", true));
            }
            if (this.preferences.getBoolean("SplashShowOnlyFirstTime", true)) {
                firstShow = false;
            }
        }
    }

    private boolean isMaintainAspectRatio() {
        return this.preferences.getBoolean("SplashMaintainAspectRatio", false);
    }

    private int getFadeDuration() {
        int fadeSplashScreenDuration = this.preferences.getBoolean("FadeSplashScreen", true) ? this.preferences.getInteger("FadeSplashScreenDuration", DEFAULT_FADE_DURATION) : 0;
        if (fadeSplashScreenDuration < 30) {
            return fadeSplashScreenDuration * 1000;
        }
        return fadeSplashScreenDuration;
    }

    public void onPause(boolean multitasking) {
        if (!HAS_BUILT_IN_SPLASH_SCREEN) {
            removeSplashScreen(true);
        }
    }

    public void onDestroy() {
        if (!HAS_BUILT_IN_SPLASH_SCREEN) {
            removeSplashScreen(true);
        }
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("hide")) {
            this.cordova.getActivity().runOnUiThread(new C00432());
        } else if (!action.equals("show")) {
            return false;
        } else {
            this.cordova.getActivity().runOnUiThread(new C00443());
        }
        callbackContext.success();
        return true;
    }

    public Object onMessage(String id, Object data) {
        if (!HAS_BUILT_IN_SPLASH_SCREEN) {
            if ("splashscreen".equals(id)) {
                if ("hide".equals(data.toString())) {
                    removeSplashScreen(false);
                } else {
                    showSplashScreen(false);
                }
            } else if ("spinner".equals(id)) {
                if ("stop".equals(data.toString())) {
                    getView().setVisibility(0);
                }
            } else if ("onReceivedError".equals(id)) {
                spinnerStop();
            }
        }
        return null;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation != this.orientation) {
            this.orientation = newConfig.orientation;
            if (this.splashImageView != null) {
                int drawableId = this.preferences.getInteger("SplashDrawableId", 0);
                if (drawableId != 0) {
                    this.splashImageView.setImageDrawable(this.cordova.getActivity().getResources().getDrawable(drawableId));
                }
            }
        }
    }

    private void removeSplashScreen(final boolean forceHideImmediately) {
        this.cordova.getActivity().runOnUiThread(new Runnable() {

            class C00451 implements AnimationListener {
                C00451() {
                }

                public void onAnimationStart(Animation animation) {
                    SplashScreen.this.spinnerStop();
                }

                public void onAnimationEnd(Animation animation) {
                    if (SplashScreen.splashDialog != null && SplashScreen.splashDialog.isShowing()) {
                        SplashScreen.splashDialog.dismiss();
                        SplashScreen.splashDialog = null;
                        SplashScreen.this.splashImageView = null;
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }
            }

            public void run() {
                if (SplashScreen.splashDialog != null && SplashScreen.splashDialog.isShowing()) {
                    int fadeSplashScreenDuration = SplashScreen.this.getFadeDuration();
                    if (fadeSplashScreenDuration <= 0 || forceHideImmediately) {
                        SplashScreen.this.spinnerStop();
                        SplashScreen.splashDialog.dismiss();
                        SplashScreen.splashDialog = null;
                        SplashScreen.this.splashImageView = null;
                        return;
                    }
                    AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                    fadeOut.setInterpolator(new DecelerateInterpolator());
                    fadeOut.setDuration((long) fadeSplashScreenDuration);
                    SplashScreen.this.splashImageView.setAnimation(fadeOut);
                    SplashScreen.this.splashImageView.startAnimation(fadeOut);
                    fadeOut.setAnimationListener(new C00451());
                }
            }
        });
    }

    private void showSplashScreen(final boolean hideAfterDelay) {
        int splashscreenTime = this.preferences.getInteger("SplashScreenDelay", DEFAULT_SPLASHSCREEN_DURATION);
        final int drawableId = this.preferences.getInteger("SplashDrawableId", 0);
        final int effectiveSplashDuration = Math.max(0, splashscreenTime - getFadeDuration());
        lastHideAfterDelay = hideAfterDelay;
        if ((splashDialog != null && splashDialog.isShowing()) || drawableId == 0) {
            return;
        }
        if (splashscreenTime > 0 || !hideAfterDelay) {
            this.cordova.getActivity().runOnUiThread(new Runnable() {

                class C00471 implements Runnable {
                    C00471() {
                    }

                    public void run() {
                        if (SplashScreen.lastHideAfterDelay) {
                            SplashScreen.this.removeSplashScreen(false);
                        }
                    }
                }

                public void run() {
                    Display display = SplashScreen.this.cordova.getActivity().getWindowManager().getDefaultDisplay();
                    Context context = SplashScreen.this.webView.getContext();
                    SplashScreen.this.splashImageView = new ImageView(context);
                    SplashScreen.this.splashImageView.setImageResource(drawableId);
                    SplashScreen.this.splashImageView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                    SplashScreen.this.splashImageView.setMinimumHeight(display.getHeight());
                    SplashScreen.this.splashImageView.setMinimumWidth(display.getWidth());
                    SplashScreen.this.splashImageView.setBackgroundColor(SplashScreen.this.preferences.getInteger("backgroundColor", -16777216));
                    if (SplashScreen.this.isMaintainAspectRatio()) {
                        SplashScreen.this.splashImageView.setScaleType(ScaleType.CENTER_CROP);
                    } else {
                        SplashScreen.this.splashImageView.setScaleType(ScaleType.FIT_XY);
                    }
                    SplashScreen.splashDialog = new Dialog(context, 16973840);
                    if ((SplashScreen.this.cordova.getActivity().getWindow().getAttributes().flags & 1024) == 1024) {
                        SplashScreen.splashDialog.getWindow().setFlags(1024, 1024);
                    }
                    SplashScreen.splashDialog.setContentView(SplashScreen.this.splashImageView);
                    SplashScreen.splashDialog.setCancelable(false);
                    SplashScreen.splashDialog.show();
                    if (SplashScreen.this.preferences.getBoolean("ShowSplashScreenSpinner", true)) {
                        SplashScreen.this.spinnerStart();
                    }
                    if (hideAfterDelay) {
                        new Handler().postDelayed(new C00471(), (long) effectiveSplashDuration);
                    }
                }
            });
        }
    }

    private void spinnerStart() {
        this.cordova.getActivity().runOnUiThread(new C00506());
    }

    private void spinnerStop() {
        this.cordova.getActivity().runOnUiThread(new C00517());
    }
}
