package com.getcapacitor;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import androidx.webkit.SafeBrowsingResponseCompat;
import androidx.webkit.WebResourceErrorCompat;
import androidx.webkit.WebViewClientCompat;
import androidx.webkit.WebViewFeature;
import androidx.webkit.WebViewCompat;

public class CapacitorWebView extends WebView {
  private BaseInputConnection capInputConnection;

  public CapacitorWebView(Context context, AttributeSet attrs) {
    super(context, attrs);

    //ToDo MGK_ADD 추가 코드 [
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

      this.setWebViewClient(new WebViewClientCompat(){

        @Override
        public void onPageCommitVisible(@NonNull WebView view, @NonNull String url) {
          super.onPageCommitVisible(view, url);
        }

        @Override
        public void onReceivedError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceErrorCompat error) {
          super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceResponse errorResponse) {
          super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onSafeBrowsingHit(@NonNull WebView view, @NonNull WebResourceRequest request, int threatType, @NonNull SafeBrowsingResponseCompat callback) {
          super.onSafeBrowsingHit(view, request, threatType, callback);
        }

        @Override
        public boolean shouldOverrideUrlLoading(@NonNull WebView view, @NonNull WebResourceRequest request) {
          return super.shouldOverrideUrlLoading(view, request);
        }
      });
    }
    //ToDo ]
  }

  @Override
  public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
    boolean captureInput = Config.getBoolean("android.captureInput", false);
    if (captureInput) {
      if (capInputConnection == null) {
        capInputConnection = new BaseInputConnection(this, false);
      }
      return capInputConnection;
    }
    return super.onCreateInputConnection(outAttrs);
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_MULTIPLE) {
      evaluateJavascript("document.activeElement.value = document.activeElement.value + '" + event.getCharacters() + "';", null);
      return false;
    }
    return super.dispatchKeyEvent(event);
  }
}
