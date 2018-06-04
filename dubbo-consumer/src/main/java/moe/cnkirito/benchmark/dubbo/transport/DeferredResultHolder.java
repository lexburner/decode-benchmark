package moe.cnkirito.benchmark.dubbo.transport;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 徐靖峰
 * Date 2018-06-04
 */
public class DeferredResultHolder {
    public static Map<Long,DeferredResult<ResponseEntity>> responseHolder = new ConcurrentHashMap<>();
    public static Map<Long,String> requestParamHolder = new ConcurrentHashMap<>();
}
