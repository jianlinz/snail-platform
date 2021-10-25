package indi.jl.sp.core.okhttp;

import indi.jl.sp.core.exception.SpSystemException;
import indi.jl.sp.core.okhttp.enums.SpHttpMethod;
import indi.jl.sp.core.util.StringUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.Map;

/**
 * HTTP 客户端工具类
 */
public class HttpClient extends ClientUtil {


    private static final OkHttpClient CLIENT = initClient();

    /**
     * 私有化工具类的构造函数，避免对工具类的实例化
     */
    protected HttpClient() {
    }

    /*
     * 静态方法调用私有构造函数，以覆盖对构造函数的测试
     */
    static {
        new HttpClient();
    }

    public static ResponseEntity<byte[]> get(String url) {
        return perform(CLIENT, url, SpHttpMethod.GET, null, null, null);
    }

    public static void get(String url, Callback callback) {
        perform(CLIENT, url, SpHttpMethod.GET, null, null, null, callback);
    }

    public static ResponseEntity<byte[]> get(String url, Map<String, String> headers) {
        return perform(CLIENT, url, SpHttpMethod.GET, headers, null, null);
    }

    public static ResponseEntity<byte[]> post(String url, MediaType type, String data) {
        return perform(CLIENT, url, SpHttpMethod.POST, null, type, data);
    }

    public static void post(String url, MediaType type, String data, Callback callback) {
        perform(CLIENT, url, SpHttpMethod.POST, null, type, data, callback);
    }

    public static ResponseEntity<byte[]> post(String url, MediaType type, Map<String, String> headers, FormBody formBody) {
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url);
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                builder = builder.addHeader(header.getKey(), header.getValue());
            }
        }
        builder = builder.method(SpHttpMethod.POST.name(), formBody);
        try {
            Request request = builder.build();
            return converter(CLIENT.newCall(request).execute());
        } catch (IOException e) {
            throw new SpSystemException("http perform error", e);
        }

    }


    public static ResponseEntity<byte[]> post(String url, Map<String, String> headers, MediaType type, String data) {
        return perform(CLIENT, url, SpHttpMethod.POST, headers, type, data);
    }

    public static ResponseEntity<byte[]> put(String url, MediaType type, String data) {
        return perform(CLIENT, url, SpHttpMethod.PUT, null, type, data);
    }

    public static ResponseEntity<byte[]> put(String url, Map<String, String> headers, MediaType type, String data) {
        return perform(CLIENT, url, SpHttpMethod.PUT, headers, type, data);
    }

    public static ResponseEntity<byte[]> put(String url, Map<String, String> headers, MediaType type, String data,
                                             int executionCount) {
        return perform(CLIENT, url, SpHttpMethod.PUT, headers, type, data);
    }

    public static ResponseEntity<byte[]> delete(String url) {
        return perform(CLIENT, url, SpHttpMethod.DELETE, null, null, null);
    }

    public static ResponseEntity<byte[]> delete(String url, Map<String, String> headers) {
        return perform(CLIENT, url, SpHttpMethod.DELETE, headers, null, null);
    }

    public static ResponseEntity<byte[]> delete(String url, MediaType type, String data) {
        return perform(CLIENT, url, SpHttpMethod.DELETE, null, type, data);
    }

    public static ResponseEntity<byte[]> delete(String url, Map<String, String> headers, MediaType type, String data) {
        return perform(CLIENT, url, SpHttpMethod.DELETE, headers, type, data);
    }

    /**
     * 将请求参数map转换成form表单APPLICATION_FORM_URLENCODED字符串
     *
     * @param params 请求参数map
     * @param encode 字符串编码格式
     * @return 存储APPLICATION_FORM_URLENCODED的字符串
     * @throws UnsupportedEncodingException 不支持的字符集
     */
    public static String getFormUrlEncodedData(Map<String, Object> params, String encode) throws UnsupportedEncodingException {
        // 存储封装好的请求体信息
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.append(entry.getKey()).append("=")
                    .append(URLEncoder.encode(entry.getValue().toString(), encode)).append("&");
        }
        if (builder.length() > 0) {
            // 删除最后的一个"&"
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    private static OkHttpClient initClient() {
        HttpClientInterceptor httpClientInterceptor = new HttpClientInterceptor();
        httpClientInterceptor.setExecutionCount(3);
        return new OkHttpClient.Builder()
                .addInterceptor(httpClientInterceptor)
                .connectTimeout(Duration.ofSeconds(1))
                .readTimeout(Duration.ofSeconds(3))
                .build();
    }

}
