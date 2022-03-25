package com.imetre.wiki.config;

import com.imetre.wiki.response.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局处理响应数据：针对返回的状态码设置到HttpResponseServer中
 */
@RestControllerAdvice
public class ResponseBodyControllerAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        //拦截复合条件需要进行处理的对象
        return returnType.getParameterType().equals(CommonResp.class);
    }

    /**
     * @param data               返回值对象
     * @param returnType         返回值类型
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // String类型不能直接包装，所以要进行些特别的处理
        CommonResp error = (CommonResp) data;
        HttpStatus resolve = HttpStatus.resolve(error.code());
        if (resolve != null) {
            serverHttpResponse.setStatusCode(resolve);
        } else {
            LOG.error("unknown error code:{}",error);
            serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return error;
    }


}
