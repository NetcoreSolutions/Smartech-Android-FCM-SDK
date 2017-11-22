package in.netcore.smartechfcmintegration.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.Random;

import in.netcore.smartechfcmintegration.R;

public class InAppActivity extends AppCompatActivity {
    private static final String TAG = "Testing";
    Button btnClosePopup;
    Button btnCreatePopup, btnhead, btnfoot, btnfull, btnhalf, btnjquery, btnc2a, btnanim;
    private boolean mbErrorOccured = false;

    Handler handler;
    Runnable r;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp);
        btnCreatePopup = (Button) findViewById(R.id.InApp);
        btnhead = (Button) findViewById(R.id.btnhead);
        btnfoot = (Button) findViewById(R.id.btnfoot);
        btnfull = (Button) findViewById(R.id.btnfull);
        btnhalf = (Button) findViewById(R.id.btnhalf);
        btnjquery = (Button) findViewById(R.id.btnjquery);
        btnc2a = (Button) findViewById(R.id.btnc2a);
        btnanim = (Button) findViewById(R.id.btnanim);

        btnCreatePopup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopupWindow(1);
            }
        });
        btnfull.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopupWindow(2);
            }
        });
        btnhalf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopupWindow(3);
            }
        });
        btnhead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopupWindow(4);
            }
        });
        btnfoot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopupWindow(5);
            }
        });
        btnjquery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopupWindow(6);
            }
        });
        btnc2a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopupWindow(7);
            }
        });
        btnanim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopupWindow(8);
            }
        });
    }

    private PopupWindow pwindo;

    public void initiatePopupWindow(int act) {

        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) getBaseContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            WebView wv = new WebView(this);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setDomStorageEnabled(true);
            wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

            class JavaScriptExtensions {
                Context context;
                WebView wv1;

                JavaScriptExtensions(Context c, WebView wv) {
                    context = c;
                    wv1 = wv;
                }

                JavaScriptExtensions(Context c) {
                    context = c;
                }

                @JavascriptInterface
                public void intentAction(String url, String textVal) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }

                @JavascriptInterface
                public void closeAction(String textVal) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pwindo.dismiss();
                            //     wv1.setVisibility(View.GONE);
                            //    wv1.destroy();
                        }
                    });
                }
            }
            wv.addJavascriptInterface(new JavaScriptExtensions(this, wv), "jse");
            wv.setBackgroundColor(0x00000000);
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(final WebView view, String url) {
                    super.onPageFinished(view, url);
                    String pageTitle = view.getTitle();

                    if (!pageTitle.equals("Smartech InAppEngage")) {
                        Log.d(TAG, "detect page not found error 404");
                        pwindo.dismiss();
                    } else {
                        Log.d(TAG, "page found");
                        //view.setVisibility(View.GONE);
                    }
                }

            });
            //String summary = "<html><body>You scored <b>192</b> points.</body></html>";
            //wv.loadData(summary, "text/html", null);

            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            if (act == 1) {
                wv.loadUrl("http://smlocal.netcore.co.in/mdownload/inapp1.html?" + new Random().nextInt());
                pwindo = new PopupWindow(wv, -1, -1, true);
                pwindo.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pwindo.showAtLocation(wv, Gravity.CENTER, 0, 0);

            } else if (act == 2) {
                wv.loadUrl("http://smlocal.netcore.co.in/mdownload/inapp2.html?" + new Random().nextInt());
                pwindo = new PopupWindow(wv, width - (width / 10), height - (height / 10), true);
                pwindo.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pwindo.showAtLocation(wv, Gravity.CENTER, 0, 0);

            } else if (act == 3) {
                wv.loadUrl("http://smlocal.netcore.co.in/mdownload/inapp3.html?" + new Random().nextInt());
                pwindo = new PopupWindow(wv, width - (width / 10), height / 2, true);
                pwindo.setBackgroundDrawable(new ColorDrawable(
                        android.graphics.Color.TRANSPARENT));
                pwindo.showAtLocation(wv, Gravity.CENTER, 0, 0);

            } else if (act == 4) {
                wv.loadUrl("http://smlocal.netcore.co.in/mdownload/inapp4.html?" + new Random().nextInt());
                pwindo = new PopupWindow(wv, -1, (height / 5), true);
                pwindo.setBackgroundDrawable(new ColorDrawable(
                        android.graphics.Color.TRANSPARENT));
                pwindo.showAtLocation(wv, Gravity.TOP, 0, 0);

            } else if (act == 5) {
                wv.loadUrl("http://smlocal.netcore.co.in/mdownload/inapp5.html?" + new Random().nextInt());
                pwindo = new PopupWindow(wv, -1, (height / 5), true);
                pwindo.setBackgroundDrawable(new ColorDrawable(
                        android.graphics.Color.TRANSPARENT));
                pwindo.showAtLocation(wv, Gravity.BOTTOM, 0, 0);
            } else if (act == 6) {
                wv.loadUrl("http://smlocal.netcore.co.in/mdownload/inapp6.html?" + new Random().nextInt());
                pwindo = new PopupWindow(wv, -1, -1, true);
                pwindo.setBackgroundDrawable(new ColorDrawable(
                        android.graphics.Color.TRANSPARENT));
                pwindo.showAtLocation(wv, Gravity.CENTER, 0, 0);
            } else if (act == 7) {
                wv.loadUrl("http://smlocal.netcore.co.in/mdownload/inapp7.html?" + new Random().nextInt());
                pwindo = new PopupWindow(wv, -1, -1, true);
                pwindo.setBackgroundDrawable(new ColorDrawable(
                        android.graphics.Color.TRANSPARENT));
                pwindo.showAtLocation(wv, Gravity.CENTER, 0, 0);
            } else if (act == 8) {
                wv.loadUrl("http://smlocal.netcore.co.in/mdownload/inapp8.html?" + new Random().nextInt());
                pwindo = new PopupWindow(wv, width - (width / 10), height / 2, true);
                pwindo.setBackgroundDrawable(new ColorDrawable(
                        android.graphics.Color.TRANSPARENT));
                pwindo.showAtLocation(wv, Gravity.CENTER, 0, 0);
            }

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };

    public boolean onKey(DialogInterface pwindo, int keyCode, KeyEvent event) {
        Log.d(TAG, "Pressed back btn " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            pwindo.dismiss();
        }

        return false;
    }
}