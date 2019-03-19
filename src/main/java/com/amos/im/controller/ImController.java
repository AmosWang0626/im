package com.amos.im.controller;

import com.amos.im.biz.ImBusiness;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * PROJECT: communication
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2018/9/29
 */
@RestController
public class ImController {

    @Resource
    private ImBusiness imBusiness;


}
