package com.tool.service.impl;

import com.tool.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Description: 缓存服务实现类 先从缓存中获取数据，没有数据再调用sql查询，并把结果加到缓存中
 * 开启基于注解@Cacheable的缓存，需使用 @EnableCaching 标识在 SpringBoot 的主启动类上。
 * @Author KerVinLi
 * @Version 1.0
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Override
    @Cacheable(value = "dictNameByCode",key = "#code",cacheManager = "defaultCacheManager")
    public String getDictNameByCode(String code) {
        log.info("模拟第一次请求打印日志，后期未过期前直接返回缓存中数据。");
        return "name:"+code;
    }
}
