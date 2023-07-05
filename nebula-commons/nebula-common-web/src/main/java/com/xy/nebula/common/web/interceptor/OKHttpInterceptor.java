package com.xy.nebula.common.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * description: RestTemplateConfig
 * date: 2020-09-09 08:06
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class OKHttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request request = chain.request();
        log.debug("sending {} request, url {}  header {}", request.method(),
                request.url(), request.headers());
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        log.debug("received response, url {} in {}ms header {}", response.request().url(),
                (t2 - t1) / 1e6d, response.headers());
        return response;
    }
}
