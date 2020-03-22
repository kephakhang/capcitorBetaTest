package com.getcapacitor;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Wraps a result for web from calling a native plugin.
 */
public class PluginResult {
  private final JSObject json;

  //ToDo MGK_ADD
  public JSObject getData() { return this.json; }

  public PluginResult() {
    this(new JSObject());
  }

  public PluginResult(JSObject json) {
    this.json = json;
  }

  public PluginResult put(String name, boolean value) {
    return this.jsonPut(name, value);
  }

  public PluginResult put(String name, double value) {
    return this.jsonPut(name, value);
  }

  public PluginResult put(String name, int value) {
    return this.jsonPut(name, value);
  }

  public PluginResult put(String name, long value) {
    return this.jsonPut(name, value);
  }

  /**
   * Format a date as an ISO string
   */
  public PluginResult put(String name, Date value) {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    df.setTimeZone(tz);
    return this.jsonPut(name, df.format(value));
  }

  public PluginResult put(String name, Object value) {
    return this.jsonPut(name, value);
  }

  public PluginResult put(String name, PluginResult value) {
    return this.jsonPut(name, value.json);
  }

  PluginResult jsonPut(String name, Object value) {
    try {
      this.json.put(name, value);
    } catch (Exception ex) {
      Log.e(LogUtils.getPluginTag(), "", ex);
    }
    return this;
  }

  public String toString() {
    return this.json.toString();
  }

  /**
   * Return plugin metadata and information about the result, if it succeeded the data, or error information if it didn't.
   * This is used for appRestoredResult, as it's technically a raw data response from a plugin.
   * @return the raw data response from the plugin.
   * @param call
   */
  public PluginResult getWrappedResult(PluginCall call) {
    JSObject ret = new JSObject();
    ret.put("pluginId", this.json.getString("pluginId"));
    ret.put("methodName", this.json.getString("methodName"));
    ret.put("success", this.json.getBoolean("success", false));
    ret.put("data", this.json.getJSObject("data"));
    ret.put("error", this.json.getJSObject("error"));
    //ToDo MGK_CHG json -> PluginResult
    return new PluginResult(ret);
  }
}
