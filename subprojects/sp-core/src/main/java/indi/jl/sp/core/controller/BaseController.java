package indi.jl.sp.core.controller;

import indi.jl.sp.core.util.RequestUtil;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.core.vo.DataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    protected static final String DEFAULT_SORT = "createTime";

    protected boolean isPageSearch() {
        return !StringUtil.isEmpty(RequestUtil.getCurrentRequest().getParameter("size"));
    }

    /**
     * 返回 GET 请求的响应
     * 查询到结果时返回 200 OK 状态及查询结果
     * 没有查到结果时返回 200 OK 状态
     *
     * @param entity 查询结果
     * @param <T>    结果对象类型
     * @return GET 请求的响应
     */
    protected <T> ResponseEntity<DataVO<T>> responseOfGet(T entity) {
        return responseOfGet(entity, null);
    }


    /**
     * 返回 GET 请求的响应
     * 查询到结果时返回 200 OK 状态及查询结果
     * 没有查到结果时返回 200 OK 状态
     *
     * @param entity 查询结果
     * @param <T>    结果对象类型
     * @return GET 请求的响应
     */
    protected <T extends DataVO> ResponseEntity<T> responseOfGet(T entity) {
        return new ResponseEntity<>(entity, null, HttpStatus.OK);
    }

    /**
     * 返回 GET 请求的响应
     * 查询到结果时返回 200 OK 状态及查询结果
     * 没有查到结果时返回 200 OK 状态
     *
     * @param entity  查询结果
     * @param headers 响应头信息
     * @param <T>     结果对象类型
     * @return GET 请求的响应
     */
    protected <T> ResponseEntity<DataVO<T>> responseOfGet(T entity, MultiValueMap<String, String> headers) {
        return responseOKWithOrWithoutContent(entity, headers, HttpStatus.OK);
    }

    /**
     * 返回 POST 请求的响应
     * 创建实体成功时返回 201 Created 状态及被创建的实体
     *
     * @param entity 被创建的实体
     * @param <T>    实体类型
     * @return POST 请求的响应
     */
    protected <T> ResponseEntity<DataVO<T>> responseOfPost(T entity) {
        return responseOfPost(entity, null);
    }

    /**
     * 返回 POST 请求的响应
     * 创建实体成功时返回 201 Created 状态及被创建的实体
     *
     * @param entity  被创建的实体
     * @param headers 响应头信息
     * @param <T>     实体类型
     * @return POST 请求的响应
     */
    protected <T> ResponseEntity<DataVO<T>> responseOfPost(T entity, MultiValueMap<String, String> headers) {
        return responseOKWithOrWithoutContent(entity, headers, HttpStatus.CREATED);
    }

    /**
     * 返回 PUT 请求的响应
     * 查询到要更新的实体并更新成功时返回 200 OK 状态及更新后的实体
     * 没有查到结果时返回 200 OK 状态
     *
     * @param entity 更新后的实体
     * @param <T>    更新的实体类型
     * @return PUT 请求的响应
     */
    protected <T> ResponseEntity<DataVO<T>> responseOfPut(T entity) {
        return responseOfPut(entity, null);
    }

    /**
     * 返回 PUT 请求的响应
     * 查询到要更新的实体并更新成功时返回 200 OK 状态及更新后的实体
     * 没有查到结果时返回 200 OK 状态
     *
     * @param entity  更新后的实体
     * @param headers 响应头信息
     * @param <T>     更新的实体类型
     * @return PUT 请求的响应
     */
    protected <T> ResponseEntity<DataVO<T>> responseOfPut(T entity, MultiValueMap<String, String> headers) {
        return responseOKWithOrWithoutContent(entity, headers, HttpStatus.OK);
    }


    /**
     * 删除成功
     *
     * @return DELETE 请求的响应
     */
    protected ResponseEntity<DataVO<String>> responseOfDelete() {
        return new ResponseEntity<>(new DataVO<>(), HttpStatus.OK);
    }


    /**
     * 封装ResponseEntity
     *
     * @param entity  查询结果
     * @param headers 响应头信息
     * @param <T>     结果对象类型
     * @return ResponseEntity
     */
    private <T> ResponseEntity<DataVO<T>> responseOKWithOrWithoutContent(T entity, MultiValueMap<String, String> headers, HttpStatus httpStatus) {
        return new ResponseEntity<>(new DataVO<T>(entity), headers, httpStatus);
    }

}
