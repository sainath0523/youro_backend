package com.youro.web.utils;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils {

    @CacheEvict(value = "profilePictures", key = "#key")
    public void evictCache(int key) {
    }

}