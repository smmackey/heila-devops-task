package com.heilaiq.interview;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpPost
{
    int TIMEOUTMS = 5000;

    /**
     * Sends a POST request to the endpoint specified in url
     * @param url an absolute URL pointing to the API endpoint that receives the Sunny Boy data
     * @param sunnyBoy object containing the values of a Sunny Boy
     * @throws IOException
     */
    public void postData(String url, SunnyBoy sunnyBoy) throws IOException {
        // Request configuration
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(TIMEOUTMS)
                .setConnectionRequestTimeout(TIMEOUTMS)
                .setSocketTimeout(TIMEOUTMS).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        // Init gson to serialize object
        Gson gson = new Gson();

        try {
            org.apache.http.client.methods.HttpPost request = new org.apache.http.client.methods.HttpPost(url);
            StringEntity params = new StringEntity(gson.toJson(sunnyBoy));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            String responseString = new BasicResponseHandler().handleResponse(response);
        } catch (IOException e) {
            httpClient.close();
            throw e;
        } finally {
            httpClient.close();
        }
    }

}
