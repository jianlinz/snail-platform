package indi.jl.sp.test.common;

import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.okhttp.HttpClient;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.test.SpTestApplication;
import indi.jl.sp.test.exception.ResponseErrorExceptionSp;
import okhttp3.FormBody;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * spring真实环境单元测试 非mock数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpTestApplication.class)
public class BaseSpringTest {

    @LocalServerPort
    protected int port;

    protected String getPrefix() {
        return "http://localhost:" + port;
    }


    protected <T> T get(String url, Class<T> type) {
        ResponseEntity<byte[]> responseEntity = HttpClient.get(getPrefix() + url);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ResponseErrorExceptionSp(new String(Objects.requireNonNull(responseEntity.getBody())));
        }
        return (T) JsonUtil.parseGenericities(new String(Objects.requireNonNull(responseEntity.getBody())), DataVO.class, type).getContent();
    }

    protected <T> Collection<T> get(String url, Class<T> type, Class<? extends Collection> c) {
        ResponseEntity<byte[]> responseEntity = HttpClient.get(getPrefix() + url);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ResponseErrorExceptionSp(new String(Objects.requireNonNull(responseEntity.getBody())));
        }
        DataVO dataVO = JsonUtil.parseGenericities(new String(Objects.requireNonNull(responseEntity.getBody())), DataVO.class, c);
        return JsonUtil.parseCollectGenericities(JsonUtil.toJsonString(dataVO.getContent()), type, c);
    }

    protected <T> PageChunk<T> getPage(String url, Class<T> type) {
        ResponseEntity<byte[]> responseEntity = HttpClient.get(getPrefix() + url);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ResponseErrorExceptionSp(new String(Objects.requireNonNull(responseEntity.getBody())));
        }
        DataVO<PageChunk<T>> dataVO = JsonUtil.parseGenericities(new String(Objects.requireNonNull(responseEntity.getBody())), DataVO.class, PageChunk.class);
        return dataVO.getContent();
    }

    protected <T> T post(String url, Class<T> type, String data) {
        ResponseEntity<byte[]> responseEntity = HttpClient.post(getPrefix() + url, MediaType.APPLICATION_JSON, data);
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            throw new ResponseErrorExceptionSp(new String(Objects.requireNonNull(responseEntity.getBody())));
        }
        return (T) JsonUtil.parseGenericities(new String(Objects.requireNonNull(responseEntity.getBody())), DataVO.class, type).getContent();
    }

    protected <T> T put(String url, Class<T> type, String data) {
        ResponseEntity<byte[]> responseEntity = HttpClient.put(getPrefix() + url, MediaType.APPLICATION_JSON, data);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ResponseErrorExceptionSp(new String(Objects.requireNonNull(responseEntity.getBody())));
        }
        return (T) JsonUtil.parseGenericities(new String(Objects.requireNonNull(responseEntity.getBody())), DataVO.class, type).getContent();
    }

    protected void delete(String url) {
        ResponseEntity<byte[]> responseEntity = HttpClient.delete(getPrefix() + url);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ResponseErrorExceptionSp(new String(Objects.requireNonNull(responseEntity.getBody())));
        }
    }

    protected String login(String username, String password) {
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        ResponseEntity<byte[]> responseEntity = HttpClient.post(getPrefix() + "/login",
                MediaType.MULTIPART_FORM_DATA,
                new HashMap<>(), formBody);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ResponseErrorExceptionSp(new String(Objects.requireNonNull(responseEntity.getBody())));
        }
        return (String) JsonUtil.parseGenericities(new String(Objects.requireNonNull(responseEntity.getBody())), DataVO.class, String.class).getContent();
    }

    protected DataVO buildErrorVO(String errorMsg) {
        return JsonUtil.parse(errorMsg, DataVO.class);
    }
}