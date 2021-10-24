package com.tool.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Description:
 * @Author KerVinLi
 * @since 2021/10/24
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /** 创建时间 */
    public static final String CREATED_DATE = "createdDate";
    /** 创建人 */
    public static final String CREATED_USER = "createdUser";
    /** 更新时间 */
    public static final String UPDATED_DATE = "updatedDate";
    /** 更新人 */
    public static final String UPDATED_USER = "updatedUser";
    /** 删除标识 */
    public static final String DELETE_FLAG = "deleteFlag";
    /** 删除时间 */
    public static final String DELETE_DATE = "deleteDate";
    /** 版本号 乐观锁 */
    public static final String VERSION = "version";

    // 删除标志 1表示已删除 0表示未删除
    public static final Integer DELETE_FLAG_TRUE = 1;
    public static final Integer DELETE_FLAG_FALSE = 0;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, CREATED_DATE, () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, UPDATED_DATE, () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictInsertFill(metaObject, CREATED_USER, () -> getCurrentInsertUser(), String.class);
        this.strictInsertFill(metaObject, UPDATED_USER, () -> getCurrentInsertUser(), String.class);
        this.strictInsertFill(metaObject, DELETE_FLAG, () -> getDeleteFlagFalse(), Integer.class);
        this.strictInsertFill(metaObject, VERSION, () -> initVersion(), Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, UPDATED_DATE, () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictUpdateFill(metaObject, UPDATED_USER, () -> getCurrentUpdateUser(), String.class);
        Integer deleteFlag = (Integer) this.getFieldValByName(DELETE_FLAG, metaObject);
        log.info("deleteFlag:"+deleteFlag);
        if(Objects.nonNull(deleteFlag) && DELETE_FLAG_TRUE.compareTo(deleteFlag) == 0 ){//逻辑删除操作
            this.strictUpdateFill(metaObject,DELETE_DATE, () -> LocalDateTime.now(), LocalDateTime.class);
        }
    }

    /**
     * 获取当前登录账户
     * @return
     */
    private String getCurrentInsertUser(){
        return "zSan";
    }

    /**
     * 获取当前登录账户
     * @return
     */
    private String getCurrentUpdateUser(){
        return "lSi";
    }

    private Integer getDeleteFlagTrue(){
        return DELETE_FLAG_TRUE;
    }

    private Integer getDeleteFlagFalse(){
        return DELETE_FLAG_FALSE;
    }
    private Integer initVersion(){
        return 1;
    }
}
