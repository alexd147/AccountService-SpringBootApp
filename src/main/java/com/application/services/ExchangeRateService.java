package com.application.services;

import java.util.Objects;

import com.application.entities.ExchangeRate;
import com.application.entities.ExchangeRatesResponse;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.extern.log4j.Log4j2;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Data
@AllArgsConstructor
@Log4j2
@Service
public class ExchangeRateService {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private RestTemplate restTemplate;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @HystrixCommand(fallbackMethod = "fallback", commandProperties = { @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
    @Cacheable("exchangeRate")
    public ExchangeRate getExchangeRateByCurrency(String currency) {
        log.info("Going to the rates-ws for exchange rates for currency {}", currency);
        String url = "https://api.exchangeratesapi.io/latest";
        ExchangeRatesResponse response = restTemplate.getForObject(url, ExchangeRatesResponse.class);
        log.info("Response {}", response);

        return new ExchangeRate(currency, Objects.requireNonNull(response).getRates().get(currency));
    }

    public ExchangeRate fallback(String currency, Throwable hystrixCommand) {
        log.info("Enter fallback for currency {}", currency);
        return new ExchangeRate(currency, 1);
    }
}
