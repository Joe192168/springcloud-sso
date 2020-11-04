package com.joe;
 
import com.joe.entity.Users;
import com.joe.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {
 
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        Users user = new Users();
        user.setUserName("xiao");
        //密码加密
        String jiaMiPw = BCrypt.hashpw("123",BCrypt.gensalt());
        user.setPassWord(jiaMiPw);
        user.setFullName("小米");
        user.setMobile("888");
        int insert = userMapper.insert(user);
        Assert.assertEquals(insert , 1);
 
    }
 
    @Test
    public void testSelect(){
        List<Users> users = userMapper.selectList(null);
        users.forEach(System.out::println);
        //断言
        Assert.assertEquals(3, users.size());
    }
 
}