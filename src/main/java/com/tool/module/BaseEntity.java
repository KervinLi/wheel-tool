package com.tool.module;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @TableField("id")
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "created_date", fill = FieldFill.INSERT)
    private LocalDateTime createdDate;

    /**
     * 创建人
     */
    @TableField(value = "created_user", fill = FieldFill.INSERT)
    private String createdUser;

    /**
     * 最后更新时间
     */
    @TableField(value = "updated_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedDate;

    /**
     * 最后更新人
     */
    @TableField(value = "updated_user", fill = FieldFill.INSERT_UPDATE)
    private String updatedUser;

    /**
     * 逻辑删除标志，1为已删除，0为未删除
     */
    @TableLogic
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    private Integer deleteFlag;

    /**
     * 逻辑删除时间
     */
    @TableField(value = "delete_date",fill = FieldFill.UPDATE)
    private LocalDateTime deleteDate;

    /**
     * 数据版本号，用于乐观锁，insert后为1，update后自增
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

}
