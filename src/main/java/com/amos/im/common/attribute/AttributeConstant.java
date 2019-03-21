package com.amos.im.common.attribute;

import io.netty.util.AttributeKey;

/**
 * PROJECT: im
 * DESCRIPTION: Channel里存储数据常量
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public interface AttributeConstant {

    AttributeKey<String> TOKEN = AttributeKey.newInstance("toToken");

}
