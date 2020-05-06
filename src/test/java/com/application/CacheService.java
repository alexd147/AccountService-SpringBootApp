package com.application;

import org.springframework.cache.annotation.CacheEvict;


public class CacheService {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @CacheEvict(value = "exchangeRate", allEntries = true)
    public void evictAllCacheValues() {
    }
}
