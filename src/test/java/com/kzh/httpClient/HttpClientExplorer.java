package com.kzh.httpClient;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.DefaultMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;
import org.springframework.http.HttpStatus;

/**
 * User: kzh
 * Date: 13-5-13
 * Time: 上午11:07
 */
public class HttpClientExplorer {
    @Test
    public void getMethod() {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod();
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        try {
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.OK.value()) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
