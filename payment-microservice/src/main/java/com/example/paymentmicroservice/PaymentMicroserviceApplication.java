package com.example.paymentmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.example.clients"

)
public class PaymentMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMicroserviceApplication.class, args);
    }


}
