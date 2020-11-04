package com.joe.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Joiner;
import com.joe.entity.Users;
import com.joe.mapper.RoleMapper;
import com.joe.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.nio.channels.AcceptPendingException;
import java.util.List;

/**
 * @description: 安全验证用户身份配置
 * @author: Joe
 **/
@Component
public class UserDetailsServiceConfig implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 生产环境使用数据库进行验证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Users user = userMapper.selectOne(wrapper);
        //该账号为空
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        //该账号不正确
        if (!username.equals(user.getUserName())) {
            throw new AcceptPendingException();
        }
        //根据用户id查询用户角色
        List<String> roles = roleMapper.findRolesByUserId(user.getId());
        //将roles转成字符串
        String result = Joiner.on(",").join(roles);
        return new User(username, user.getPassWord(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(result));
    }
}
