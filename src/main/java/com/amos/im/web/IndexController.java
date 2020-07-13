package com.amos.im.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * DESCRIPTION: IndexController
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/7/11
 */
@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }

}
