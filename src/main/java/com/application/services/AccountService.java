package com.application.services;

import java.util.List;
import java.util.Optional;

import com.application.entities.AccountDto;


public interface AccountService {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    List<AccountDto> findAll();

    Optional<AccountDto> findById(long id);
}
