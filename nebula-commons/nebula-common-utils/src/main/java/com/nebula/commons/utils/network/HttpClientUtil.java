package com.nebula.commons.utils.network;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description: HttpClientUtils
 * date: 2019-11-12 21:55
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class HttpClientUtil {

    //编码格式
    private static final String CHAR_SET = "UTF-8";
    //连接超时时间，单位：毫秒
    private static final int connectTimeout = 30 * 1000;
    //响应时间，单位：毫秒
    private static final int socketTimeout = 30 * 1000;
    //从connect Manager(连接池)获取Connection 超时时间，单位：毫秒
    private static final int connectionRequestTimeout = 3 * 1000;
    //连接池最大连接数
    private static final int maxPoolConnections = 800;
    //每个路由最大连接数
    private static final int maxPoolConnectionsPerRoute = 400;

    private static final CloseableHttpClient httpClient;

    /**
     * httplient初始化
     */
    static {
        // 加入3次重试机制，请求失败后进行重试
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {
                    // Do not retry if over max retry count
                    return false;
                }
                return exception instanceof NoHttpResponseException;
            }

        };
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxPoolConnections);
        cm.setDefaultMaxPerRoute(maxPoolConnectionsPerRoute);
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build();
        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config).setRetryHandler(myRetryHandler).build();
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, Object> params) {
        return get(url, null, params);
    }

    public static String getWithHeader(String url, Map<String, Object> headers) {
        return get(url, headers, null);
    }

    /**
     * get请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求参数
     * @return
     */
    public static String get(String url, Map<String, Object> headers, Map<String, Object> params) {
        try {
            // 创建访问的地址
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                params.forEach((key, value) -> {
                    uriBuilder.setParameter(key, value.toString());
                });
            }
            // 创建http对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 设置请求头
            buildHeader(headers, httpGet);
            // 执行请求并获取结果
            return getResponse(httpClient, httpGet);
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        }
        return null;
    }

    public static String post(String url) {
        return post(url, null, null);
    }

    public static String post(String url, Map<String, String> params) {
        return post(url, null, params);
    }

    public static String postWithHeader(String url, Map<String, Object> headers) {
        return post(url, headers, null);
    }

    /**
     * post请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求参数
     * @return
     */
    public static String post(String url, Map<String, Object> headers, Map<String, String> params) {
        try {
            // 创建http对象
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头
            buildHeader(headers, httpPost);
            // 设置请求参数
            buildParam(params, httpPost);
            // 执行请求并获取结果
            return getResponse(httpClient, httpPost);
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        }
        return null;
    }

    public static String postJson(String url) {
        return postJson(url, null, null);
    }

    public static String postJson(String url, Object params) {
        return postJson(url, null, params);
    }

    public static String postJsonWithHeader(String url, Map<String, Object> headers) {
        return postJson(url, headers, null);
    }

    /**
     * post提交json请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求参数
     * @return
     */
    public static String postJson(String url, Map<String, Object> headers, Object params) {
        try {
            // 创建http对象
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头
            buildHeader(headers, httpPost);
            // 设置请求参数
            buildJsonParams(params, httpPost);
            // 执行请求并获取结果
            return getResponse(httpClient, httpPost);
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        }
        return null;
    }

    public static String put(String url) {
        return get(url, null, null);
    }

    public static String put(String url, Map<String, Object> params) {
        return get(url, null, params);
    }

    public static String putWithHeader(String url, Map<String, Object> headers) {
        return get(url, headers, null);
    }

    /**
     * put请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求参数
     * @return
     */
    public static String put(String url, Map<String, Object> headers, Map<String, String> params) {
        try {
            // 创建http对象
            HttpPut httpPut = new HttpPut(url);
            // 设置请求头
            buildHeader(headers, httpPut);
            // 设置请求参数
            buildParam(params, httpPut);
            // 执行请求并获取结果
            return getResponse(httpClient, httpPut);
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        }
        return null;
    }

    public static String putJson(String url, Map<String, Object> headers, Object params) {
        try {
            // 创建http对象
            HttpPut httpPut = new HttpPut(url);
            // 设置请求头
            buildHeader(headers, httpPut);
            // 设置请求参数
            buildJsonParams(params, httpPut);
            // 执行请求并获取结果
            return getResponse(httpClient, httpPut);
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        }
        return null;
    }

    public static String delete(String url) {
        return delete(url, null, null);
    }

    public static String delete(String url, Map<String, Object> params) {
        return delete(url, null, params);
    }

    public static String deleteWithHeader(String url, Map<String, Object> headers) {
        return delete(url, headers, null);
    }

    /**
     * delete请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求参数
     * @return
     */
    public static String delete(String url, Map<String, Object> headers, Map<String, Object> params) {
        try {
            // 创建访问的地址
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                params.forEach((key, value) -> {
                    uriBuilder.setParameter(key, value.toString());
                });
            }
            // 创建http对象
            HttpDelete httpDelete = new HttpDelete(uriBuilder.build());
            // 设置请求头
            buildHeader(headers, httpDelete);
            // 执行请求并获取结果
            return getResponse(httpClient, httpDelete);
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        }
        return null;
    }

    /**
     *
     * @param url 请求地址
     * @param headers 请求头
     * @param params  请求参数
     * @param fileParamName 服务端接收文件参数名
     * @param bytes 二进制数组
     * @param suffixFilename 文件后缀名（例：.jpg、.txt）
     * @return
     */
    public static String uploadFileByte(String url, Map<String, Object> headers, Map<String, String> params, String fileParamName, byte[] bytes, String suffixFilename) {
        try {
            // 创建http对象
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头
            buildHeader(headers, httpPost);
            // 设置文件流
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName(CHAR_SET));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
            builder.addBinaryBody(fileParamName, bytes, ContentType.DEFAULT_BINARY, suffixFilename);
            if (params != null) {
                params.forEach((key, value) -> {
                    builder.addTextBody(key, value);
                });
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            return getResponse(httpClient, httpPost);
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        }
        return null;
    }

    /**
     * 文件上传
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求参数
     * @param fileParamName 服务端接收文件参数名
     * @param localFilePath 本地文件地址
     * @return
     */
    public static String uploadFile(String url, Map<String, Object> headers, Map<String, String> params, String fileParamName, String localFilePath) {
        try {
            File file = new File(localFilePath);
            String fileName = file.getName();
            // 创建http对象
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头
            buildHeader(headers, httpPost);
            // 设置文件流
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName(CHAR_SET));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
            builder.addBinaryBody(fileParamName, file, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            if (params != null) {
                params.forEach((key, value) -> {
                    builder.addTextBody(key, value);
                });
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            return getResponse(httpClient, httpPost);
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        }
        return null;
    }

    public static boolean downloadFile(String url, String localFilePath) {
        return downloadFile(url, null, null, localFilePath);
    }

    public static boolean downloadFile(String url, Map<String, String> params, String localFilePath) {
        return downloadFile(url, null, params, localFilePath);
    }

    public static boolean downloadFileWithHeaders(String url, Map<String, Object> headers, String localFilePath) {
        return downloadFile(url, headers, null, localFilePath);
    }

    /**
     * 文件下载
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求参数
     * @param localFilePath 本地文件路径
     * @return
     */
    public static boolean downloadFile(String url, Map<String, Object> headers, Map<String, String> params, String localFilePath) {
        CloseableHttpResponse httpResponse = null;
        InputStream responseStream = null;
        FileOutputStream fout = null;
        try {
            // 创建访问的地址
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                params.forEach((key, value) -> {
                    uriBuilder.setParameter(key, value);
                });
            }
            // 创建http对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 设置请求头
            buildHeader(headers, httpGet);
            // 执行请求并获取结果
            httpResponse = httpClient.execute(httpGet);
            // 获取返回结果
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (httpResponse.getEntity() != null) {
                    responseStream = httpResponse.getEntity().getContent();
                    if (responseStream != null) {
                        File file = new File(localFilePath);
                        if(!file.exists()){
                            file.createNewFile();
                        }
                        fout = new FileOutputStream(file);
                        int l;
                        byte[] tmp = new byte[4096];
                        while ((l = responseStream.read(tmp)) != -1) {
                            fout.write(tmp, 0, l);
                        }
                        return true;
                    }
                }
            } else {
                log.error("httpclient request failed, httpCode:{}, reason:{}", httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        } finally {
            release(httpResponse);
            if (responseStream != null) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    log.error("responseStream close failed", e);
                }
            }
            if (fout != null) {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    log.error("FileOutputStream flush or close failed", e);
                }
            }
        }
        return false;
    }


    /**
     * 设置请求头
     * @param headers
     * @param httpMethod
     */
    public static void buildHeader(Map<String, Object> headers, HttpRequestBase httpMethod) {
        // 封装请求头
        if (headers != null) {
            headers.forEach((key, value) -> {
                httpMethod.setHeader(key, value == null ? null : String.valueOf(value));
            });
        }
    }

    /**
     * 封装请求参数
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void buildParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        //封装请求参数
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<>();
            params.forEach((key, value) -> {
                nvps.add(new BasicNameValuePair(key, value));
            });
            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, CHAR_SET));
        }
    }

    /**
     * 设置json格式请求参数
     * @param params
     * @param httpMethod
     */
    public static void buildJsonParams(Object params, HttpEntityEnclosingRequestBase httpMethod) {
        if (params != null) {
            HttpEntity httpEntity = new StringEntity(JSON.toJSONString(params), ContentType.APPLICATION_JSON);
            httpMethod.setEntity(httpEntity);
        }
    }

    /**
     * 获取响应数据
     * @param httpClient
     * @param httpMethod
     * @return
     */
    public static String getResponse(CloseableHttpClient httpClient, HttpRequestBase httpMethod) {
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            // 执行请求
            httpResponse = httpClient.execute(httpMethod);
            // 获取返回结果
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (httpResponse.getEntity() != null) {
                    return EntityUtils.toString(httpResponse.getEntity(), CHAR_SET);
                }
            } else {
                log.error("httpclient request failed, httpCode:{}, reason:{}", httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            log.error("httpclient request failed", e);
        } finally {
            release(httpResponse);
        }
        return null;
    }

    /**
     * 释放资源
     * @param httpResponse
     * @throws IOException
     */
    public static void release(CloseableHttpResponse httpResponse) {
        if (httpResponse != null) {
            try {
                EntityUtils.consume(httpResponse.getEntity());
                httpResponse.close();
            } catch (IOException e) {
                log.error("release resource failed", e);
            }
        }
    }

}
