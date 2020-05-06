package com.application.services;

import com.application.entities.AccountDao;
import com.application.entities.AccountDto;


public class AccountTransformer {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static AccountDto from(AccountDao accountDao) {
        return new AccountDto(accountDao.getIban(), accountDao.getCurrency(), accountDao.getBalance(), accountDao.getLastUpdate());
    }
}
