package com.amos.im;

import com.amos.im.common.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(RedisUtil.get("hello"));
    }

}
