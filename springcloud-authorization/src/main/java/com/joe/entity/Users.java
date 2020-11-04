package com.joe.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: distribution-sso
 * @description: 用户实体
 * @author: Joe
 **/
@Data
@TableName("t_user")
public class Users {

    private int id;
    private String userName;
    private String passWord;
    private String fullName;
    private String mobile;
    private int isLocked;

}
