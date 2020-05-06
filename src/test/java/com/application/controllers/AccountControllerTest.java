package com.application.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Date;
import java.util.Optional;

import com.application.CacheService;
import com.application.entities.AccountDao;
import com.application.repositories.AccountRepository;
import com.application.services.ExchangeRateService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private RestTemplate mockRestTemplate;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private CacheService cacheService;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Before
    public void init() {
        AccountDao accountDao1 = new AccountDao(1L, "RO00 RZBR 0000 0000 0000 0001", "RON", 10000L, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        AccountDao accountDao2 = new AccountDao(2L, "RO00 RZBR 0000 0000 0000 0002", "USD", 2000L, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(accountDao1));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.of(accountDao2));

        exchangeRateService.setRestTemplate(mockRestTemplate);
    }

    @Test
    public void accountNotFound() throws Exception {
        mockMvc.perform(get("/account/6")).andExpect(status().isNotFound());
    }

    @Test
    public void accountFoundWithoutCachedRates() throws Exception {
        cacheService.evictAllCacheValues();

        mockMvc.perform(get("/account/1")).andExpect(status().is2xxSuccessful());
        cacheService.evictAllCacheValues();
        exchangeRateService.getExchangeRateByCurrency("RON");

        Mockito.verify(mockRestTemplate, Mockito.times(2)).getForObject(Mockito.any(String.class), any());
    }

    @Test
    public void accountFoundWithCachedRates() throws Exception {
        cacheService.evictAllCacheValues();

        mockMvc.perform(get("/account/2")).andExpect(status().is2xxSuccessful());
        exchangeRateService.getExchangeRateByCurrency("USD");

        Mockito.verify(mockRestTemplate, Mockito.times(1)).getForObject(Mockito.any(String.class), any());
    }
}
