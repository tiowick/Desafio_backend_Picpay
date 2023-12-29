package com.picpaydesafio.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig { // configurando a infejeção para as classes que são dependentes do RestTemplate
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
