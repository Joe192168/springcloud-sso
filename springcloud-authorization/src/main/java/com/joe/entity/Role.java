package com.joe.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @program: distribution-sso
 * @description: 角色实体
 * @author: Joe
 **/
@Data
@TableName("t_role")
public class Role {

    private int id;
    private String roleName;
    private String description;
    private Date createTime;
    private Date updateTime;
    private String status;

}
