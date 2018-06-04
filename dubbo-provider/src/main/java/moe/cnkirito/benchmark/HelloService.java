package moe.cnkirito.benchmark;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class HelloService implements IHelloService {

    private static AtomicLong count = new AtomicLong(0);

    private Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Override
    public int hash(String str) throws Exception {
        int hashCode = str.hashCode();
        logger.info(count.incrementAndGet() + "_" + hashCode);
        sleep(50);
        return hashCode;
    }

    private void sleep(long duration) throws Exception {
        Thread.sleep(duration);
    }
}
