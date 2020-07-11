package com.amos.im.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * DESCRIPTION: IndexController
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/7/11
 */
@ApiIgnore
@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "chat";
    }


}
