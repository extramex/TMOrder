package com.olonte.tmorder.modelo;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by jsgravenhorst on 3/8/14.
 */
public class JSONParser {
    // constructor
    public JSONParser() {

    }

    public boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            URLConnection urlConnection = myUrl.openConnection();
            urlConnection.setConnectTimeout(timeout);
            urlConnection.connect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /******
     * *******************************************************************Metodos POST************************************************************************************
     *
     */
    public JSONObject methodPOST( String url, List<NameValuePair> params ){

        DefaultHttpClient defaultHttpClient;
        HttpPost httpPost;
        HttpResponse httpResponse;
        HttpEntity httpEntity;
        InputStream inputStream;
        int timeOut;

        inputStream = null;
        timeOut = 10000;

        if (isConnectedToServer(url,timeOut)) {
            try {

                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                httpResponse = defaultHttpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonParcer( inputStream );
        } else {
            return null;
        }


    }

    public JSONObject methodPOST( String url ){

        DefaultHttpClient defaultHttpClient;
        HttpPost httpPost;
        HttpEntity httpEntity;
        HttpResponse httpResponse;
        InputStream inputStream;
        int timeOut;

        inputStream = null;

        timeOut = 10000;

        if (isConnectedToServer(url,timeOut)) {

            try {

                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(url);
                httpResponse = defaultHttpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonParcer( inputStream );
        }

        return null;


    }

    /******
     * *******************************************************************Metodos GET************************************************************************************
     *
     */

    public JSONObject methodGET(String url, List<NameValuePair> params){

        DefaultHttpClient defaultHttpClient;
        HttpResponse httpResponse;
        HttpGet httpGet;
        HttpEntity httpEntity;
        InputStream inputStream;
        String paramString;
        int timeOut;

        inputStream = null;

        timeOut = 10000;

        if (isConnectedToServer(url,timeOut)) {

            try {

                defaultHttpClient = new DefaultHttpClient();
                paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                httpGet = new HttpGet(url);
                httpResponse = defaultHttpClient.execute(httpGet);
                httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonParcer( inputStream );
        }

        return null;

    }

    public JSONObject methodGET(String url ){

        DefaultHttpClient defaultHttpClient;
        HttpResponse httpResponse;
        HttpGet httpGet;
        HttpEntity httpEntity;
        InputStream inputStream;
        int timeOut;

        inputStream = null;

        timeOut = 10000;

        if (isConnectedToServer(url,timeOut)) {

            try {
                defaultHttpClient = new DefaultHttpClient();
                httpGet = new HttpGet(url);
                httpResponse = defaultHttpClient.execute(httpGet);
                httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonParcer( inputStream );
        }

        return null;

    }

    /**
     **********************************************************************************************************************************************************************
     */
    public JSONObject makeHttpRequest( String url, String method )  {

        JSONObject jSonObject;
        jSonObject = null;

        if (method.equalsIgnoreCase( "POST" ) )
            jSonObject =  methodPOST( url );
        else if (method.equalsIgnoreCase( "GET" ) )
            jSonObject = methodGET( url );

        return jSonObject;
    }

    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params)  {

        JSONObject jSonObject;
        jSonObject = null;

	    if ( method.equalsIgnoreCase( "POST" ) )
		    jSonObject = methodPOST(url,params);
	    else if (method.equalsIgnoreCase( "GET" ) )
		    jSonObject = methodGET(url,params);

        return jSonObject;

    }

    public JSONObject jsonParcer( InputStream inputStream ) {

          BufferedReader bufferedReader;
          StringBuilder stringBuilder;
          String json;
          JSONObject jSonObject;

          json = "";
          jSonObject = null;

          try {
              bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
              stringBuilder = new StringBuilder();
              String line;

              while ((line = bufferedReader.readLine()) != null) {
                  stringBuilder.append(line + "\n");
              }

              inputStream.close();
              json = stringBuilder.toString();

          } catch (Exception e) {
              Log.e("Buffer Error", "Error converting result " + e.toString());
          }

          // try parse the string to a JSON object
          try {

              jSonObject = new JSONObject(json);

          } catch (JSONException e) {
              Log.e("JSON Parser", "Error parsing data " + e.toString());
          }

          return jSonObject;

    }

}
