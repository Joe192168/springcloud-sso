package com.joe.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: distribution-sso
 * @description: 权限实体
 * @author: Joe
 **/
@Data
@TableName("t_permission")
public class Permission {

    private int id;
    private String code;
    private String description;
    private String url;
    private String method;
}
