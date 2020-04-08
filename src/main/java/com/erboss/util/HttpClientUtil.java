package com.erboss.util;

import com.erboss.exception.HttpClientFailException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final RequestConfig CONFIG = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000)
            .build();

    /**
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream inputStream = conn.getInputStream();
            byte[] getData = readInputStream(inputStream);
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            logger.error("下载文件失败", e);
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 发送get请求
     *
     * @param url 请求路径
     * @return
     * @throws HttpClientFailException
     */
    public static String doGet(String url) throws HttpClientFailException {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求
     *
     * @param url    请求路径
     * @param params 请求参数
     * @return
     * @throws HttpClientFailException
     */
    public static String doGet(String url, Map<String, String> params) throws HttpClientFailException {
        return doGet(url, params, null);
    }

    /**
     * Client发送HTTP GET请求到指定URL，传入的参数会以URL参数对的方式追加在url之后
     *
     * @param url       请求的URL
     * @param params    参数信息
     * @param headerMap 头部参数
     * @return
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headerMap) throws HttpClientFailException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(CONFIG).build();
        HttpGet httpGet = null;
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                String separator = (url.indexOf("?") == -1) ? "?" : "&";
                url += separator + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "utf-8"));
            }

            httpGet = new HttpGet(url);

            if (headerMap != null && !headerMap.isEmpty()) {
                for (String key : headerMap.keySet()) {
                    httpGet.addHeader(key, headerMap.get(key));
                }
            }

            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null && entity.getContentLength() != 0) {
                result = EntityUtils.toString(entity, "UTF-8");
            }

            if (statusCode != HttpStatus.SC_OK) {
                logger.error("httpclient do get response code is:{},the url is:{}, response result:{}.", statusCode, url, result);
                httpGet.abort();
                throw new HttpClientFailException("httpclient do get error status code :" + statusCode);
            }
            return result;
        } catch (Exception e) {
            logger.error("httpclient do get exception,the url is:" + url, e);
            throw new HttpClientFailException(e.getMessage(), e);
        } finally {
            try {
                if (httpGet != null) {
                    httpGet.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("httpclient do get close exception", e);
            }
        }
    }

    /**
     * 发送post请求
     *
     * @param url 请求路径
     * @return
     * @throws HttpClientFailException
     */
    public static String doPost(String url) throws HttpClientFailException {
        return doPost(url, null, null);
    }

    /**
     * 发送post请求
     *
     * @param url    请求路径
     * @param params 请求参数
     * @return
     * @throws HttpClientFailException
     */
    public static String doPost(String url, Map<String, String> params) throws HttpClientFailException {
        return doPost(url, params, null);
    }

    /**
     * 发送post请求
     *
     * @param url     请求路径
     * @param params  请求参数
     * @param headers 请求头
     * @return
     * @throws HttpClientFailException
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) throws HttpClientFailException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(CONFIG).build();
        HttpPost httpPost = new HttpPost(url);

        try {
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    httpPost.addHeader(key, headers.get(key));
                }
            }

            if (params != null && !params.isEmpty()) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Iterator<Map.Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, String> entry = it.next();
                    paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null && entity.getContentLength() != 0) {
                result = EntityUtils.toString(entity, "UTF-8");
            }

            if (statusCode != HttpStatus.SC_OK) {
                logger.error("httpclient do post response code is:{},the url is:{}, response result:{}.", statusCode, url, result);
                httpPost.abort();
                throw new HttpClientFailException("httpclient do post error status code :" + statusCode);
            }
            return result;
        } catch (Exception e) {
            logger.error("httpclient do post exception,url:{}." + url, e);
            throw new HttpClientFailException(e.getMessage(), e);
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("httpclient do post close exception", e);
            }
        }
    }

}
