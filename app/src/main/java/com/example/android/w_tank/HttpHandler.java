package com.example.android.w_tank;

/**
 * Created by Mohit on 15-02-2017.
 */

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Mohit on 27-01-2017.
 */

public class HttpHandler {

    private static final String Tag=HttpHandler.class
            .getSimpleName();

    public HttpHandler(){}

    public String makeServiceCall(String reqUrl)
    {
        String response= null;
        try{
            URL url=new URL(reqUrl);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            InputStream in =new BufferedInputStream(con.getInputStream());
            response=convertStreamToString(in);
        }catch (MalformedURLException e)
        {
            Log.e(Tag,"MalformedURLException: "+e.getMessage());

        }catch (ProtocolException e)
        {
            Log.e(Tag,"ProtocolException: "+e.getMessage());
        }catch (IOException e)
        {
            Log.e(Tag,"IOException: "+e.getMessage());
        }
        catch (Exception e)
        {
            Log.e(Tag,"Exception: "+e.getMessage());
        }

        Log.e(Tag,"My response "+response);
        return response;
    }

    private String convertStreamToString(InputStream is)
    {
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        StringBuilder sb=new StringBuilder();
        String line;
        try{
            while((line=reader.readLine())!=null)
            {
                sb.append(line).append("\n");
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        finally {
            try{
                is.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
