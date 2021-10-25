package indi.jl.sp.core.okhttp;

import indi.jl.sp.core.exception.SpSystemException;
import indi.jl.sp.core.okhttp.enums.SpHttpMethod;
import indi.jl.sp.core.util.StringUtil;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ClientUtil {


    protected static ResponseEntity<byte[]> perform(OkHttpClient client,
                                                    String url, SpHttpMethod method,
                                                    Map<String, String> headers, MediaType type,
                                                    String data) {
        try {
            Response response = createCall(client, url, method, headers, type, data).execute();
            return converter(response);
        } catch (IOException e) {
            throw new SpSystemException("http perform error", e);
        }
    }

    /**
     * 异步发送请求，需要回调方法
     *
     * @param client   http 客户端
     * @param url      请求 url
     * @param method   请求方法
     * @param headers  请求头
     * @param type     media type
     * @param data     数据
     * @param callback 回调类
     */
    protected static void perform(OkHttpClient client,
                                  String url, SpHttpMethod method,
                                  Map<String, String> headers, MediaType type,
                                  String data, final Callback callback) {
        createCall(client, url, method, headers, type, data).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                callback.onSuccess(converter(response));
            }
        });
    }

    private static Call createCall(OkHttpClient client,
                                   String url, SpHttpMethod method,
                                   Map<String, String> headers, MediaType type,
                                   String data) {
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url);
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                builder = builder.addHeader(header.getKey(), header.getValue());
            }
        }
        RequestBody body = null;
        if (StringUtil.isNotNull(data)) {
            Assert.notNull(type, "Data is not null!");
            body = RequestBody.create(okhttp3.MediaType.parse(type.toString()), data);
        }
        builder = builder.method(method.name(), null == body
                && HttpMethod.requiresRequestBody(method.name())
                ? RequestBody.create(okhttp3.MediaType.parse(""), "") : body);
        Request request = builder.build();
        return client.newCall(request);
    }

    protected static ResponseEntity<byte[]> converter(Response response) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.putAll(response.headers().toMultimap());
        try {
            return new ResponseEntity<>(Objects.requireNonNull(response.body()).bytes(), headers, HttpStatus.valueOf(response.code()));
        } catch (IOException e) {
            throw new SpSystemException("http converter error", e);
        }
    }

}
