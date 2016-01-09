package com.fredericlavigne.somusic.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

  private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
      Locale.ENGLISH);
  {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  public static Date parse(String yyyyMMdd) {
    try {
      dateFormat.setCalendar(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
      return dateFormat.parse(yyyyMMdd);
    } catch (ParseException e) {
      return null;
    }
  }

  public static String format(Date date) {
    return dateFormat.format(date);
  }
  
  public static String getBody(String host, String query) throws IOException {
    return getBody("http://" + host + query);
  }

  public static String getBody(String location) throws IOException {
    URL url = new URL(location);
    URLConnection connection = url.openConnection();
    ((HttpURLConnection) connection).setRequestProperty("Connection", "Close");
    return getBody(connection);
  }

  public static String getBodyWithAuthentication(String location,
      String basicAuthHeader) throws IOException {
    URL url = new URL(location);
    URLConnection connection = url.openConnection();
    ((HttpURLConnection) connection).setRequestProperty("Connection", "Close");
    ((HttpURLConnection) connection).setRequestProperty("Authorization",
        basicAuthHeader);
    return getBody(connection);
  }

  public static String getBody(URLConnection connection) throws IOException {
    InputStream input = connection.getInputStream();
    try {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      int read;
      byte[] buffer = new byte[1024];
      while ((read = input.read(buffer)) != -1) {
        output.write(buffer, 0, read);
      }
      String result = output.toString("UTF-8");
      Log.fine(result);

      return result;
    } finally {
      input.close();
    }
  }
  
  public static Date dayAfter(Date date) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  public static Date now(int addHours) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.add(Calendar.HOUR_OF_DAY, addHours);
    return calendar.getTime();
  }

  public static Date dayBefore(Date date) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    return calendar.getTime();
  }

  public static Date yesterday() {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  public static Date today() {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }
  
  public static Date tomorrow() {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

}
