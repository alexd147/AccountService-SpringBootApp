package com.application.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Date;

import com.application.entities.AccountDao;
import com.application.entities.AccountDto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class AccountTransformerTest {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void transformerFromDaoToDto() {
        Date commonDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        AccountDao accountDao = new AccountDao(1L, "RO00 RZBR 0000 0000 0000 0001", "RON", 10000L, commonDate);
        AccountDto accountDtoReceived = AccountTransformer.from(accountDao);
        AccountDto accountDtoExpected = new AccountDto("RO00 RZBR 0000 0000 0000 0001", "RON", 10000L, commonDate);

        Assert.assertEquals(accountDtoReceived, accountDtoExpected);
    }
}
