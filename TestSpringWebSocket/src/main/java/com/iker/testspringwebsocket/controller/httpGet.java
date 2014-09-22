package com.iker.testspringwebsocket.controller;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class httpGet
{
    private URL url;
    private HttpURLConnection httpURLConn;
    public void myDoGet()
    {
        try
        {
            String temp = new String();
            url = new  URL("http://localhost:8080/myServlet/welcome");
            url = new  URL("http://192.168.0.242:8086/oldPeople/repTel.do");            
            httpURLConn= (HttpURLConnection)url.openConnection();
            httpURLConn.setDoOutput(true);
            httpURLConn.setRequestMethod("GET");
            httpURLConn.setIfModifiedSince(999999999);
            httpURLConn.setRequestProperty("Referer", "http://localhost:80");
            httpURLConn.setRequestProperty("User-Agent", "test");
            httpURLConn.getOutputStream().write("nihao".getBytes());
            httpURLConn.connect();
            InputStream in =httpURLConn.getInputStream();
            BufferedReader bd = new BufferedReader(new InputStreamReader(in));
            while((temp=bd.readLine())!=null)
            {
                System.out.println(temp);
            }            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } 
        finally
        {
            if(httpURLConn!=null)
            {
                httpURLConn.disconnect();
            }
        }
    }
    public static void main(String[] args)
    {
        httpGet hg = new httpGet();
        hg.myDoGet();
    }
}