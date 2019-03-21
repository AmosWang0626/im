package com.amos.im.common.attribute;

import com.amos.im.controller.dto.LoginVO;
import io.netty.util.AttributeKey;

/**
 * PROJECT: im
 * DESCRIPTION: Channel里存储数据常量
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public interface AttributeConstant {

    AttributeKey<LoginVO> TOKEN = AttributeKey.newInstance("token");

}
