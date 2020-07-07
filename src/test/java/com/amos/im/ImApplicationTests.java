package com.amos.im;

import com.amos.im.common.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(RedisUtil.get("hello"));
    }

}
