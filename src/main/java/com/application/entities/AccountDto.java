package com.application.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AccountDto {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private String iban;
    private String currency;
    private double balance;
    private Date lastUpdate;
}
