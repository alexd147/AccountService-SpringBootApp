package com.application.controllers;

import java.util.List;

import com.application.entities.AccountDto;
import com.application.exceptions.AccountNotFoundException;
import com.application.services.AccountService;
import com.application.services.ExchangeRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Autowired
    private AccountService accountService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @GetMapping("/accounts")
    List<AccountDto> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/account/{id}")
    String findOne(@PathVariable Long id) {
        AccountDto accountDto = accountService.findById(id).orElseThrow(AccountNotFoundException::new);

        double rate = exchangeRateService.getExchangeRateByCurrency(accountDto.getCurrency()).getRate();

        return accountDto.toString() + " --> balance amount in EUR: " + (accountDto.getBalance() / rate);
    }
}
