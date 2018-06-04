package moe.cnkirito.benchmark;

import moe.cnkirito.benchmark.dubbo.transport.NormalClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Random;

/**
 * @author 徐靖峰
 * Date 2018-06-04
 */
@RestController
public class BenchmarkEntry {

    private NormalClient normalClient;

    public BenchmarkEntry(){
        System.out.println("init");
        NormalClient normalClient = new NormalClient();
        normalClient.connect("127.0.0.1",20881 );
        this.normalClient = normalClient;
    }

    Random r = new Random(1);

    @RequestMapping(path = "/benchmark")
    public DeferredResult<ResponseEntity> benchmark(){
        String param = RandomStringUtils.random(r.nextInt(1024), true, true);
        return normalClient.call(param);
    }

}
