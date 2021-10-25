package indi.jl.sp.core.okhttp;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface Callback {

    /**
     * 请求成功
     * @param responseEntity responseEntity
     */
    void onSuccess(ResponseEntity<byte[]> responseEntity);

    /**
     * 请求失败
     * @param ioe ioe
     */
    void onError(IOException ioe);

}
