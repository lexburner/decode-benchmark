package moe.cnkirito.benchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class ProviderApp {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ProviderApp.class,args);

        new CountDownLatch(1).await();
    }
}
