package com.joe;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @description:
 * @author: Joe
 **/
@SpringBootTest
public class TestBCrypt {

    @Test
    public void testPw(){
        //加密
        String jiaMiPw = BCrypt.hashpw("123",BCrypt.gensalt());
        System.out.println("加密："+jiaMiPw);

        //校验密码
        boolean checkPW = BCrypt.checkpw("123",jiaMiPw);
        System.out.println("校验结果："+checkPW);
    }

}
