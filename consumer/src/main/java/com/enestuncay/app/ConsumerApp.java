package com.enestuncay.app;

import com.enestuncay.consumer.Consumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(value = "com")
@PropertySource("consumer.properties")
public class ConsumerApp {

    public static void main(String[] args){
        SpringApplication.run(ConsumerApp.class , args);


        Consumer consumer =  new Consumer("buyukveri");

        consumer.run();


    }

}
