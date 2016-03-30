package org.houstonmethodist.phonedirectory.Services;



import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.houstonmethodist.phonedirectory.Model.Employees;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by TMHMXY8 on 3/18/2016.
 */
public class HttpClientUtil {
    public final static String baseUrl="http://10.110.39.21/PhoneDirectoryService/Api/";

    public static boolean serviceAvailable(String path) throws IOException {
        URL url = new URL(baseUrl+path);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(5*1000);
        String result = null;
        InputStream instream = null;

        try {

            urlConnection.connect();
            instream = new BufferedInputStream(urlConnection.getInputStream());
            int code=urlConnection.getResponseCode();
            if(code==200) {
                result = readStream(instream);
            }
            int count=Integer.parseInt(result);
            return count>100;

        } catch (Exception e) {
            Log.i("error", e.getMessage());
            // manage exceptions
        } finally {
            urlConnection.disconnect();
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }
        return false;
    }

    public static List<Employees.Employee> getEmployees(String path) throws IOException {

        URL url = new URL(baseUrl+path);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(15*1000);
        String result = null;
        InputStream instream = null;
        List<Employees.Employee> list=null;

        try {

            urlConnection.connect();
            instream = new BufferedInputStream(urlConnection.getInputStream());
            int code=urlConnection.getResponseCode();
            if(code==200) {
                result = readStream(instream);
                list=JsonConvert(result);
            }
        } catch (Exception e) {
            Log.i("error", e.getMessage());
            // manage exceptions
        } finally {
            urlConnection.disconnect();
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return list;
    }

    public static String readStream(InputStream stream) throws IOException {
        String str=IOUtils.toString(stream);
        return str;
    }

    public static List<Employees.Employee> JsonConvert(String input) throws JSONException {
        JSONArray jsonArray = new JSONArray(input);

        List<Employees.Employee> list=new ArrayList<>();

        for(int i=0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String networkId = jsonObject.optString("NetworkId");
            String lastName = jsonObject.optString("LastName");
            String firstName = jsonObject.optString("FirstName");
            String middleInitial=jsonObject.optString("MiddleInitial");
            String email=jsonObject.optString("Email");
            String phone=jsonObject.optString("Phone");
            String business=jsonObject.optString("BusinessUnit");
            String department=jsonObject.optString("Department");

            Employees.Employee e=new Employees.Employee(networkId,lastName, firstName, email, phone, business, department, middleInitial);

            list.add(e);
        }

        return list;
    }

    public static String getWifiName(Context context) {
        String ssid = "none";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ssid = wifiInfo.getSSID();
        ssid=ssid.replace("\"", "");

        return ssid;
    }
}
