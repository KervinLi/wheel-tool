package com.tool.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.tool.mybatis.handler.MyMetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: Mybatis配置
 * @Author KerVinLi
 * @since 2021/10/24
 */
@Slf4j
@Configuration
@AutoConfigureOrder
@EnableTransactionManagement
@MapperScan(basePackages = {"com.tool.module.*.mapper"})
@ComponentScans({
        @ComponentScan("com.tool.module.*.service.*")
})
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
public class MybatisAutoConfig {
    /**
     * 注册乐观锁和分页插件(新版：3.4.0)
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor()); // 乐观锁插件
        // DbType：数据库类型(根据类型获取应使用的分页方言)
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 分页插件
        return interceptor;
    }

    @Bean
    public MyMetaObjectHandler myMetaObjectHandler(){
        return new MyMetaObjectHandler();
    }
}
