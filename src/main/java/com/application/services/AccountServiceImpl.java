package com.application.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.application.entities.AccountDao;
import com.application.entities.AccountDto;
import com.application.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Autowired
    private AccountRepository accountRepository;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(AccountTransformer::from).collect(Collectors.toList());
    }

    @Override
    public Optional<AccountDto> findById(long id) {
        Optional<AccountDao> accountDao = accountRepository.findById(id);
        return accountDao.map(AccountTransformer::from);
    }
}
