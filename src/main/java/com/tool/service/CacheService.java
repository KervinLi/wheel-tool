package com.tool.service;

/**
 * 缓存服务接口
 */
public interface CacheService {
    /**
     * 根据字典代码获取字典值
     * @param code
     * @return
     */
    String getDictNameByCode(String code);
}
